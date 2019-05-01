package be.valuya.accountingtroll.cache;

import be.valuya.accountingtroll.domain.ATAccount;
import be.valuya.accountingtroll.domain.ATAccountBalance;
import be.valuya.accountingtroll.domain.ATBookPeriod;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

class BookPeriodAccountBalanceCache {

    private final ATBookPeriod bookPeriod;
    private final Map<ATAccount, ATAccountBalance> accountBalanceMap = new ConcurrentSkipListMap<>();

    BookPeriodAccountBalanceCache(ATBookPeriod bookPeriod) {
        this.bookPeriod = bookPeriod;
    }

    List<ATAccountBalance> getAllBalances() {
        return accountBalanceMap.values().stream()
                .sorted(Comparator.comparing(ATAccountBalance::getAccount))
                .collect(Collectors.toList());
    }

    ATAccountBalance appendToBalance(ATAccount account, BigDecimal amount) {
        ATAccountBalance accountBalance = accountBalanceMap.computeIfAbsent(account, this::createEmptyBalance);
        BigDecimal periodEndBalance = accountBalance.getPeriodEndBalance();
        BigDecimal updatedBalance = periodEndBalance.add(amount);
        accountBalance.setPeriodEndBalance(updatedBalance);
        return accountBalance;
    }

    void setBalance(ATAccount account, BigDecimal amount) {
        ATAccountBalance accountBalance = accountBalanceMap.computeIfAbsent(account, this::createEmptyBalance);
        accountBalance.setPeriodStartBalance(amount);
        accountBalance.setPeriodEndBalance(amount);
    }

    private ATAccountBalance createEmptyBalance(ATAccount account) {
        ATAccountBalance accountBalance = new ATAccountBalance(account, bookPeriod);
        return accountBalance;
    }
}
