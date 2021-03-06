package be.valuya.accountingtroll.cache;

import be.valuya.accountingtroll.domain.ATAccount;
import be.valuya.accountingtroll.domain.ATAccountBalance;
import be.valuya.accountingtroll.domain.ATBookPeriod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

class BookPeriodAccountBalanceCache {

    private final ATBookPeriod bookPeriod;
    private final Map<ATAccount, ATAccountBalance> accountBalanceMap = new ConcurrentSkipListMap<>();
    // A flag indicating if an account balance has been resetted.
    // for opening periods entries, we may want to reset the balance to the entries value.
    // since more than a single entry may exist for an account-period tuple, we have to keep track of this.
    // TODO: implement cache for account-period-thirdparty
    private final Map<ATAccount, Boolean> resettedMap = new ConcurrentSkipListMap<>();

    BookPeriodAccountBalanceCache(ATBookPeriod bookPeriod) {
        this.bookPeriod = bookPeriod;
    }

    List<ATAccountBalance> getAllBalances() {
        return accountBalanceMap.values().stream()
                .sorted(Comparator.comparing(ATAccountBalance::getAccount))
                .collect(Collectors.toList());
    }

    ATAccountBalance appendToBalance(ATAccount account, BigDecimal amount, LocalDate operationDate) {
        ATAccountBalance accountBalance = accountBalanceMap.computeIfAbsent(account, this::createEmptyBalance);
        BigDecimal periodEndBalance = accountBalance.getPeriodEndBalance();
        BigDecimal updatedBalance = periodEndBalance.add(amount);
        accountBalance.setPeriodEndBalance(updatedBalance);
        LocalDate newOperationDate = computeMostRecentOperationDate(operationDate, accountBalance);
        accountBalance.setLastOperationDate(newOperationDate);
        return accountBalance;
    }


    void setBalance(ATAccount account, BigDecimal amount, LocalDate operationDate) {
        ATAccountBalance accountBalance = accountBalanceMap.computeIfAbsent(account, this::createEmptyBalance);
        accountBalance.setPeriodStartBalance(amount);
        accountBalance.setPeriodEndBalance(amount);
        LocalDate newOperationDate = computeMostRecentOperationDate(operationDate, accountBalance);
        accountBalance.setLastOperationDate(newOperationDate);
    }


    ATAccountBalance resetBalance(ATAccount account, BigDecimal amount, LocalDate operationDate) {
        ATAccountBalance accountBalance = accountBalanceMap.computeIfAbsent(account, this::createEmptyBalance);
        boolean resetted = resettedMap.computeIfAbsent(account, a -> false);
        if (resetted) {
            appendToBalance(account, amount, operationDate);
        } else {
            setBalance(account, amount, operationDate);
            resettedMap.put(account, true);
        }
        return accountBalance;
    }

    private ATAccountBalance createEmptyBalance(ATAccount account) {
        return new ATAccountBalance(account, bookPeriod);
    }

    private LocalDate computeMostRecentOperationDate(LocalDate operationDate, ATAccountBalance accountBalance) {
        LocalDate curOperationDateNullable = accountBalance.getLastOperationDate();
        return Optional.ofNullable(curOperationDateNullable)
                .filter(prevDate -> prevDate.isAfter(operationDate))
                .orElse(operationDate);
    }
}
