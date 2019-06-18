package be.valuya.accountingtroll.cache;

import be.valuya.accountingtroll.AccountingRuntimeException;
import be.valuya.accountingtroll.domain.ATAccount;
import be.valuya.accountingtroll.domain.ATAccountBalance;
import be.valuya.accountingtroll.domain.ATAccountingEntry;
import be.valuya.accountingtroll.domain.ATBookPeriod;
import be.valuya.accountingtroll.domain.ATPeriodType;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class AccountBalanceSpliterator implements Spliterator<ATAccountBalance> {

    private final AccountBalancesCache accountBalancesCache;
    private final Iterator<ATAccountingEntry> accountingEntriesIterator;
    private final List<ATBookPeriod> allPeriods;
    private int curPeriodIndex;

    private boolean ignoreIntermediatePeriodOpeningEntry = false;
    private boolean resetOnBookYearOpening = true;

    public AccountBalanceSpliterator(Stream<ATAccountingEntry> accountingEntriesStream, List<ATBookPeriod> allPeriods) {
        this.accountingEntriesIterator = accountingEntriesStream.iterator();
        this.allPeriods = allPeriods;
        this.accountBalancesCache = new AccountBalancesCache(allPeriods);
        this.curPeriodIndex = 0;

        Collections.sort(allPeriods);
    }

    public void setIgnoreIntermediatePeriodOpeningEntry(boolean ignoreIntermediatePeriodOpeningEntry) {
        this.ignoreIntermediatePeriodOpeningEntry = ignoreIntermediatePeriodOpeningEntry;
    }

    public void setResetOnBookYearOpening(boolean resetOnBookYearOpening) {
        this.resetOnBookYearOpening = resetOnBookYearOpening;
    }

    public void setResetEveryYear(boolean resetEveryYear) {
        this.accountBalancesCache.setResetEveryYear(resetEveryYear);
    }

    @Override
    public boolean tryAdvance(Consumer<? super ATAccountBalance> action) {
        boolean hasNextEntry = accountingEntriesIterator.hasNext();
        if (hasNextEntry) {
            ATAccountingEntry nextEntry = accountingEntriesIterator.next();
            ATBookPeriod nextPeriod = nextEntry.getBookPeriod();
            BigDecimal amount = nextEntry.getAmount();
            ATAccount account = nextEntry.getAccount();

            this.emitPeriodBalancesUntil(nextPeriod, action);

            handleNextEntry(nextPeriod, amount, account);
            return true;
        } else {
            if (curPeriodIndex < allPeriods.size()) {
                this.emitRemainingPeriodBalances(action);
                return true;
            } else {
                return false;
            }
        }
    }

    private void handleNextEntry(ATBookPeriod nextPeriod, BigDecimal amount, ATAccount account) {
        ATPeriodType periodType = nextPeriod.getPeriodType();
        switch (periodType) {
            case OPENING:
                handlePeriodOpeningEntry(nextPeriod, amount, account);
                break;
            case GENERAL:
                accountBalancesCache.addAmountToBalance(nextPeriod, account, amount);
                break;
            case CLOSING:
                handlePeriodClosingEntry(nextPeriod, amount, account);
                break;
            default:
                throw new IllegalArgumentException("Invalid period type: " + periodType);
        }
    }

    private void handlePeriodOpeningEntry(ATBookPeriod nextPeriod, BigDecimal amount, ATAccount account) {
        if (this.ignoreIntermediatePeriodOpeningEntry) {
            // Only emit balance for the first opening
            int periodIndex = allPeriods.indexOf(nextPeriod);
            if (periodIndex != 0) {
                return;
            }
        }

        if (this.resetOnBookYearOpening) {
            accountBalancesCache.resetBalanceAmount(nextPeriod, account, amount);
        } else {
            accountBalancesCache.addAmountToBalance(nextPeriod, account, amount);
        }
    }

    private void handlePeriodClosingEntry(ATBookPeriod nextPeriod, BigDecimal amount, ATAccount account) {
        accountBalancesCache.addAmountToBalance(nextPeriod, account, amount);
    }


    @Override
    public Spliterator<ATAccountBalance> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE;
    }

    private void emitPeriodBalancesUntil(ATBookPeriod maxPeriodExclusive, Consumer<? super ATAccountBalance> action) {
        int maxPeriodIndex = allPeriods.indexOf(maxPeriodExclusive);
        if (maxPeriodIndex < 0) {
            throw new AccountingRuntimeException("Period not found: " + maxPeriodExclusive);
        }

        if (curPeriodIndex >= maxPeriodIndex) {
            return;
        }
        emitPeriodBalances(action, maxPeriodIndex);
    }

    private void emitRemainingPeriodBalances(Consumer<? super ATAccountBalance> action) {
        int allPeriodSize = allPeriods.size();
        emitPeriodBalances(action, allPeriodSize);
    }

    private void emitPeriodBalances(Consumer<? super ATAccountBalance> action, int maxIndex) {
        for (int periodIndex = curPeriodIndex; periodIndex < maxIndex; periodIndex++) {
            ATBookPeriod bookPeriod = allPeriods.get(periodIndex);
            accountBalancesCache.getPeriodBalances(bookPeriod).forEach(action);
        }
        curPeriodIndex = maxIndex;
    }

}
