package be.valuya.accountingtroll.cache;

import be.valuya.accountingtroll.AccountingRuntimeException;
import be.valuya.accountingtroll.domain.ATAccount;
import be.valuya.accountingtroll.domain.ATAccountBalance;
import be.valuya.accountingtroll.domain.ATAccountingEntry;
import be.valuya.accountingtroll.domain.ATBookPeriod;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class AccountBalanceSpliterator implements Spliterator<ATAccountBalance> {

    private final AccountBalanceCache accountBalanceCache;
    private final Iterator<ATAccountingEntry> accountingEntriesIterator;
    private final List<ATBookPeriod> allPeriods;
    private int curPeriodIndex;

    public AccountBalanceSpliterator(Stream<ATAccountingEntry> accountingEntriesStream, List<ATBookPeriod> allPeriods) {
        this.accountingEntriesIterator = accountingEntriesStream.iterator();
        this.allPeriods = allPeriods;
        this.accountBalanceCache = new AccountBalanceCache(allPeriods);
        this.curPeriodIndex = 0;
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

            accountBalanceCache.addAmountToBalance(nextPeriod, account, amount);
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
            accountBalanceCache.getPeriodBalances(bookPeriod).forEach(action);
        }
        curPeriodIndex = maxIndex;
    }
}
