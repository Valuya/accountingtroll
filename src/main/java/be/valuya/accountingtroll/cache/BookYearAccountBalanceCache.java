package be.valuya.accountingtroll.cache;

import be.valuya.accountingtroll.domain.ATAccount;
import be.valuya.accountingtroll.domain.ATAccountBalance;
import be.valuya.accountingtroll.domain.ATAccountingEntry;
import be.valuya.accountingtroll.domain.ATBookPeriod;
import be.valuya.accountingtroll.domain.ATBookYear;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

public class BookYearAccountBalanceCache {

    private final static BigDecimal ZERO = BigDecimal.ZERO.setScale(3, RoundingMode.UNNECESSARY);

    private ATBookYear bookYear;
    private ATBookPeriod openingPeriod;
    private Map<String, ATAccountBalance> accountBalanceCache = new ConcurrentSkipListMap<>();

    public BookYearAccountBalanceCache(ATBookYear bookYear, Optional<BookYearAccountBalanceCache> cacheToCopyBalancesFrom, ATBookPeriod openingPeriod) {
        this.bookYear = bookYear;
        this.openingPeriod = openingPeriod;

        cacheToCopyBalancesFrom.ifPresent(this::copyBalances);
    }


    ATAccountBalance resetAccountBalance(ATAccount account, ATAccountingEntry accountingEntry) {
        String accountCode = account.getCode();
        BigDecimal entryAmount = accountingEntry.getAmount();
        ATBookPeriod bookPeriod = accountingEntry.getBookPeriod();
        ATAccountBalance curBalance = accountBalanceCache.computeIfAbsent(accountCode, code -> this.createEmptyBalance(account, bookPeriod));
        if (isSamePeriod(curBalance, accountingEntry)) {
            this.appendBalanceAmount(curBalance, entryAmount);
        } else {
            this.setBalanceAmount(curBalance, entryAmount);
        }
        return curBalance;
    }

    ATAccountBalance resetAccountBalance(ATAccount account, ATBookPeriod bookPeriod, BigDecimal balance) {
        String accountCode = account.getCode();
        ATAccountBalance curBalance = accountBalanceCache.computeIfAbsent(accountCode, code -> this.createEmptyBalance(account, bookPeriod));
        if (isSamePeriod(curBalance, bookPeriod)) {
            this.appendBalanceAmount(curBalance, balance);
        } else {
            this.setBalanceAmount(curBalance, balance);
        }
        return curBalance;
    }

    ATAccountBalance appendAccountBalance(ATAccount account, ATAccountingEntry accountingEntry) {
        String accountCode = account.getCode();
        BigDecimal entryAmount = accountingEntry.getAmount();
        LocalDate date = accountingEntry.getDate();
        ATBookPeriod bookPeriod = accountingEntry.getBookPeriod();
        ATAccountBalance curBalance = accountBalanceCache.computeIfAbsent(accountCode, code -> this.createEmptyBalance(account, bookPeriod));

        curBalance.setDate(date);
        curBalance.setPeriod(bookPeriod);
        appendBalanceAmount(curBalance, entryAmount);
        return curBalance;
    }

    ATAccountBalance getBalance(ATAccount account) {
        String accountCode = account.getCode();

        ATAccountBalance balance = accountBalanceCache.computeIfAbsent(accountCode, code -> this.createEmptyBalance(account, openingPeriod));
        return balance;
    }

    List<ATAccount> getAccountsWithBalance() {
        return accountBalanceCache.values()
                .stream()
                .map(ATAccountBalance::getAccount)
                .collect(Collectors.toList());
    }

    ATBookYear getBookYear() {
        return this.bookYear;
    }

    private void setBalanceAmount(ATAccountBalance balance, BigDecimal entryAmount) {
        balance.setBalance(entryAmount);
    }

    private void appendBalanceAmount(ATAccountBalance balance, BigDecimal amountToAdd) {
        BigDecimal newAmount = balance.getBalance().add(amountToAdd);
        balance.setBalance(newAmount);
    }

    private boolean isSamePeriod(ATAccountBalance curBalance, ATAccountingEntry accountingEntry) {
        ATBookPeriod periodA = curBalance.getPeriod();
        ATBookPeriod periodB = accountingEntry.getBookPeriod();
        return periodA.equals(periodB);
    }


    private boolean isSamePeriod(ATAccountBalance curBalance, ATBookPeriod bookPeriod) {
        ATBookPeriod periodA = curBalance.getPeriod();
        return periodA.equals(bookPeriod);
    }

    private ATAccountBalance createEmptyBalance(ATAccount account, ATBookPeriod bookPeriod) {
        ATAccountBalance accountBalance = new ATAccountBalance();
        accountBalance.setDate(bookPeriod.getStartDate());
        accountBalance.setPeriod(bookPeriod);
        accountBalance.setAccount(account);
        accountBalance.setBalance(ZERO);
        return accountBalance;
    }

    private Map<String, ATAccountBalance> getAccountBalanceCache() {
        return accountBalanceCache;
    }

    private void copyBalances(BookYearAccountBalanceCache previousCache) {
        previousCache.getAccountBalanceCache().values()
                .stream()
                .filter(this::isBalanceCopyRequired)
                .forEach(lastBalance -> this.copyPreviousBalance(lastBalance, openingPeriod));
    }

    private void copyPreviousBalance(ATAccountBalance previousBalance, ATBookPeriod newPeriod) {
        ATAccount account = previousBalance.getAccount();
        BigDecimal balance = previousBalance.getBalance();
        String code = account.getCode();
        LocalDate startDate = newPeriod.getStartDate();

        ATAccountBalance newBalance = new ATAccountBalance();
        newBalance.setBalance(balance);
        newBalance.setAccount(account);
        newBalance.setDate(startDate);
        newBalance.setPeriod(newPeriod);
        accountBalanceCache.put(code, newBalance);
    }

    private boolean isBalanceCopyRequired(ATAccountBalance accountBalance) {
        ATAccount account = accountBalance.getAccount();
        boolean yearlyBalanceReset = account.isYearlyBalanceReset();
        return !yearlyBalanceReset;
    }

}
