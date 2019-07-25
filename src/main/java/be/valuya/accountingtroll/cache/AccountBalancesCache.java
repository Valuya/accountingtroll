package be.valuya.accountingtroll.cache;

import be.valuya.accountingtroll.AccountingRuntimeException;
import be.valuya.accountingtroll.AccountingConstants;
import be.valuya.accountingtroll.domain.ATAccount;
import be.valuya.accountingtroll.domain.ATAccountBalance;
import be.valuya.accountingtroll.domain.ATBookPeriod;
import be.valuya.accountingtroll.domain.ATBookYear;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

class AccountBalancesCache {

    private SortedMap<ATBookPeriod, BookPeriodAccountBalanceCache> periodBalances = new ConcurrentSkipListMap<>();
    private boolean resetEveryYear;

    AccountBalancesCache(List<ATBookPeriod> allPeriods) {
        allPeriods.forEach(this::createPeriodCache);
    }

    void setResetEveryYear(boolean resetEveryYear) {
        this.resetEveryYear = resetEveryYear;
    }

    List<ATAccountBalance> getPeriodBalances(ATBookPeriod bookPeriod) {
        BookPeriodAccountBalanceCache balanceCache = getBalanceCache(bookPeriod);
        return balanceCache.getAllBalances();
    }

    void addAmountToBalance(ATBookPeriod bookPeriod, LocalDate operationDate, ATAccount account, BigDecimal amount) {
        BookPeriodAccountBalanceCache balanceCache = getBalanceCache(bookPeriod);
        ATAccountBalance updatedBalance = balanceCache.appendToBalance(account, amount, operationDate);

        propagateUpdatedBalance(updatedBalance);
    }

    void resetBalanceAmount(ATBookPeriod bookPeriod, LocalDate operationDate, ATAccount account, BigDecimal amount) {
        BookPeriodAccountBalanceCache balanceCache = getBalanceCache(bookPeriod);
        ATAccountBalance updatedBalance = balanceCache.resetBalance(account, amount, operationDate);

        propagateUpdatedBalance(updatedBalance);
    }

    private void propagateUpdatedBalance(ATAccountBalance updatedBalance) {
        ATBookPeriod period = updatedBalance.getPeriod();

        periodBalances.tailMap(period).keySet().stream()
                .skip(1) // skip updated balance
                .forEach(p -> this.setPeriodBalance(p, updatedBalance));
    }

    private void setPeriodBalance(ATBookPeriod period, ATAccountBalance updatedBalance) {
        ATBookPeriod balancePeriod = updatedBalance.getPeriod();
        BigDecimal periodEndBalance = updatedBalance.getPeriodEndBalance();
        ATAccount account = updatedBalance.getAccount();
        LocalDate lastOperationDate = updatedBalance.getLastOperationDate();
        boolean yearlyBalanceReset = account.isYearlyBalanceReset() || resetEveryYear;

        ATBookYear periodBookYear = period.getBookYear();
        ATBookYear balanceBookyear = balancePeriod.getBookYear();
        boolean sameBookYear = periodBookYear.equals(balanceBookyear);

        BigDecimal updatedAmount;
        if (yearlyBalanceReset && !sameBookYear) {
            updatedAmount = AccountingConstants.AMOUNT_ZERO;
        } else {
            updatedAmount = periodEndBalance;
        }

        BookPeriodAccountBalanceCache balanceCache = getBalanceCache(period);
        balanceCache.setBalance(account, updatedAmount, lastOperationDate);
    }

    private void createPeriodCache(ATBookPeriod bookPeriod) {
        BookPeriodAccountBalanceCache periodAccountBalanceCache = new BookPeriodAccountBalanceCache(bookPeriod);
        periodBalances.put(bookPeriod, periodAccountBalanceCache);
    }

    private BookPeriodAccountBalanceCache getBalanceCache(ATBookPeriod bookPeriod) {
        BookPeriodAccountBalanceCache balanceCacheNullable = periodBalances.get(bookPeriod);
        return Optional.ofNullable(balanceCacheNullable)
                .orElseThrow(() -> new AccountingRuntimeException("No balance cache found for period " + bookPeriod));
    }

}
