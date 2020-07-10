package be.valuya.accountingtroll.cache;

import be.valuya.accountingtroll.domain.ATBookPeriod;
import be.valuya.accountingtroll.domain.ATThirdParty;
import be.valuya.accountingtroll.domain.ATThirdPartyBalance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

class BookPeriodThirdPartyBalanceCache {

    private final ATBookPeriod bookPeriod;
    private final Map<ATThirdParty, ATThirdPartyBalance> thirdPartyBalanceMap = new ConcurrentSkipListMap<>();
    // A flag indicating if an account balance has been resetted.
    // for opening periods entries, we may want to reset the balance to the entries value.
    // since more than a single entry may exist for an account-period tuple, we have to keep track of this.
    // TODO: implement cache for account-period-thirdparty
    private final Map<ATThirdParty, Boolean> resettedMap = new ConcurrentSkipListMap<>();

    BookPeriodThirdPartyBalanceCache(ATBookPeriod bookPeriod) {
        this.bookPeriod = bookPeriod;
    }

    List<ATThirdPartyBalance> getAllBalances() {
        return thirdPartyBalanceMap.values().stream()
                .sorted(Comparator.comparing(ATThirdPartyBalance::getThirdParty))
                .collect(Collectors.toList());
    }

    ATThirdPartyBalance appendToBalance(ATThirdParty thirdParty, BigDecimal amount, LocalDate operationDate) {
        ATThirdPartyBalance thirdPartyBalance = thirdPartyBalanceMap.computeIfAbsent(thirdParty, this::createEmptyBalance);
        BigDecimal periodEndBalance = thirdPartyBalance.getPeriodEndBalance();
        BigDecimal updatedBalance = periodEndBalance.add(amount);
        thirdPartyBalance.setPeriodEndBalance(updatedBalance);
        LocalDate newOperationDate = computeMostRecentOperationDate(operationDate, thirdPartyBalance);
        thirdPartyBalance.setLastOperationDate(newOperationDate);
        return thirdPartyBalance;
    }


    void setBalance(ATThirdParty thirdParty, BigDecimal amount, LocalDate operationDate) {
        ATThirdPartyBalance thirdPartyBalance = thirdPartyBalanceMap.computeIfAbsent(thirdParty, this::createEmptyBalance);
        thirdPartyBalance.setPeriodStartBalance(amount);
        thirdPartyBalance.setPeriodEndBalance(amount);
        LocalDate newOperationDate = computeMostRecentOperationDate(operationDate, thirdPartyBalance);
        thirdPartyBalance.setLastOperationDate(newOperationDate);
    }


    ATThirdPartyBalance resetBalance(ATThirdParty thirdParty, BigDecimal amount, LocalDate operationDate) {
        ATThirdPartyBalance thirdPartyBalance = thirdPartyBalanceMap.computeIfAbsent(thirdParty, this::createEmptyBalance);
        boolean resetted = resettedMap.computeIfAbsent(thirdParty, a -> false);
        if (resetted) {
            appendToBalance(thirdParty, amount, operationDate);
        } else {
            setBalance(thirdParty, amount, operationDate);
            resettedMap.put(thirdParty, true);
        }
        return thirdPartyBalance;
    }

    private ATThirdPartyBalance createEmptyBalance(ATThirdParty thirdParty) {
        return new ATThirdPartyBalance(thirdParty, bookPeriod);
    }

    private LocalDate computeMostRecentOperationDate(LocalDate operationDate, ATThirdPartyBalance thirdPartyBalance) {
        LocalDate curOperationDateNullable = thirdPartyBalance.getLastOperationDate();
        return Optional.ofNullable(curOperationDateNullable)
                .filter(prevDate -> prevDate.isAfter(operationDate))
                .orElse(operationDate);
    }
}
