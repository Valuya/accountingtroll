package be.valuya.accountingtroll.cache;

import be.valuya.accountingtroll.AccountingRuntimeException;
import be.valuya.accountingtroll.domain.ATBookPeriod;
import be.valuya.accountingtroll.domain.ATThirdParty;
import be.valuya.accountingtroll.domain.ATThirdPartyBalance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

class ThirdPartyBalanceCache {

    private SortedMap<ATBookPeriod, BookPeriodThirdPartyBalanceCache> periodBalances = new ConcurrentSkipListMap<>();
    private boolean resetEveryYear;

    ThirdPartyBalanceCache(List<ATBookPeriod> allPeriods) {
        allPeriods.forEach(this::createPeriodCache);
    }

    void setResetEveryYear(boolean resetEveryYear) {
        this.resetEveryYear = resetEveryYear;
    }

    List<ATThirdPartyBalance> getPeriodBalances(ATBookPeriod bookPeriod) {
        BookPeriodThirdPartyBalanceCache balanceCache = getBalanceCache(bookPeriod);
        return balanceCache.getAllBalances();
    }

    void addAmountToBalance(ATBookPeriod bookPeriod, LocalDate operationDate, ATThirdParty account, BigDecimal amount) {
        BookPeriodThirdPartyBalanceCache balanceCache = getBalanceCache(bookPeriod);
        ATThirdPartyBalance updatedBalance = balanceCache.appendToBalance(account, amount, operationDate);

        propagateUpdatedBalance(updatedBalance);
    }

    void resetBalanceAmount(ATBookPeriod bookPeriod, LocalDate operationDate, ATThirdParty account, BigDecimal amount) {
        BookPeriodThirdPartyBalanceCache balanceCache = getBalanceCache(bookPeriod);
        ATThirdPartyBalance updatedBalance = balanceCache.resetBalance(account, amount, operationDate);

        propagateUpdatedBalance(updatedBalance);
    }

    private void propagateUpdatedBalance(ATThirdPartyBalance updatedBalance) {
        ATBookPeriod period = updatedBalance.getPeriod();

        periodBalances.tailMap(period).keySet().stream()
                .skip(1) // skip updated balance
                .forEach(p -> this.setPeriodBalance(p, updatedBalance));
    }

    private void setPeriodBalance(ATBookPeriod period, ATThirdPartyBalance updatedBalance) {
        BigDecimal periodEndBalance = updatedBalance.getPeriodEndBalance();
        ATThirdParty thirdParty = updatedBalance.getThirdParty();
        LocalDate lastOperationDate = updatedBalance.getLastOperationDate();

        BookPeriodThirdPartyBalanceCache balanceCache = getBalanceCache(period);
        balanceCache.setBalance(thirdParty, periodEndBalance, lastOperationDate);
    }

    private void createPeriodCache(ATBookPeriod bookPeriod) {
        BookPeriodThirdPartyBalanceCache periodAccountBalanceCache = new BookPeriodThirdPartyBalanceCache(bookPeriod);
        periodBalances.put(bookPeriod, periodAccountBalanceCache);
    }

    private BookPeriodThirdPartyBalanceCache getBalanceCache(ATBookPeriod bookPeriod) {
        BookPeriodThirdPartyBalanceCache balanceCacheNullable = periodBalances.get(bookPeriod);
        return Optional.ofNullable(balanceCacheNullable)
                .orElseThrow(() -> new AccountingRuntimeException("No balance cache found for period " + bookPeriod));
    }

}
