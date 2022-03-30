package be.valuya.accountingtroll.cache;

import be.valuya.accountingtroll.AccountingRuntimeException;
import be.valuya.accountingtroll.domain.ATAccountingEntry;
import be.valuya.accountingtroll.domain.ATBookPeriod;
import be.valuya.accountingtroll.domain.ATPeriodType;
import be.valuya.accountingtroll.domain.ATThirdParty;
import be.valuya.accountingtroll.domain.ATThirdPartyBalance;
import be.valuya.accountingtroll.domain.AccountingEntryDocumentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ThirdPartyBalanceSpliterator implements Spliterator<ATThirdPartyBalance> {

    private final ThirdPartyBalanceCache thirdPartyBalanceCache;
    private final Iterator<ATAccountingEntry> accountingEntriesIterator;
    private final List<ATBookPeriod> allPeriods;
    private int curPeriodIndex;

    private boolean ignoreIntermediatePeriodOpeningEntry = false;
    private boolean resetOnBookYearOpening = true;

    public ThirdPartyBalanceSpliterator(Stream<ATAccountingEntry> accountingEntriesStream, List<ATBookPeriod> allPeriods) {
        this.accountingEntriesIterator = accountingEntriesStream.iterator();
        this.allPeriods = allPeriods;
        this.thirdPartyBalanceCache = new ThirdPartyBalanceCache(allPeriods);
        this.curPeriodIndex = 0;

        Collections.sort(allPeriods);
    }

    public void setIgnoreIntermediatePeriodOpeningEntry(boolean ignoreIntermediatePeriodOpeningEntry) {
        this.ignoreIntermediatePeriodOpeningEntry = ignoreIntermediatePeriodOpeningEntry;
    }

    public void setResetOnBookYearOpening(boolean resetOnBookYearOpening) {
        this.resetOnBookYearOpening = resetOnBookYearOpening;
    }

    @Override
    public boolean tryAdvance(Consumer<? super ATThirdPartyBalance> action) {
        boolean hasNextEntry = accountingEntriesIterator.hasNext();
        if (hasNextEntry) {
            ATAccountingEntry nextEntry = accountingEntriesIterator.next();
            ATBookPeriod nextPeriod = nextEntry.getBookPeriod();
            BigDecimal amount = nextEntry.getAmount();
            Optional<ATThirdParty> thirdPartyOptional = Optional.ofNullable(nextEntry.getThirdParty());
            LocalDate operationDate = nextEntry.getDate();

            boolean relevantDocumentType = Optional.ofNullable(nextEntry.getAccountingEntryDocumentType())
                    .map(this::isDocumentTypeImputedOnThirdParty)
                    .orElse(false);
            boolean hasThirdParty = thirdPartyOptional.isPresent();

            this.emitPeriodBalancesUntil(nextPeriod, action);
            if (hasThirdParty && relevantDocumentType) {
                ATThirdParty atThirdParty = thirdPartyOptional.get();
                handleNextEntry(nextPeriod, operationDate, amount, atThirdParty);
            }

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

    private boolean isDocumentTypeImputedOnThirdParty(AccountingEntryDocumentType documentType) {
        switch (documentType) {
            case CUSTOMER_ACCOUNT_ALLOCATION:
            case SUPPLIER_ACCOUNT_ALLOCATION:
                return true;
            default:
                return false;
        }
    }

    private void handleNextEntry(ATBookPeriod nextPeriod, LocalDate operationDate, BigDecimal amount, ATThirdParty thirdParty) {
        ATPeriodType periodType = nextPeriod.getPeriodType();
        switch (periodType) {
            case OPENING:
                handlePeriodOpeningEntry(nextPeriod, operationDate, amount, thirdParty);
                break;
            case GENERAL:
                thirdPartyBalanceCache.addAmountToBalance(nextPeriod, operationDate, thirdParty, amount);
                break;
            case CLOSING:
                handlePeriodClosingEntry(nextPeriod, operationDate, amount, thirdParty);
                break;
            default:
                throw new IllegalArgumentException("Invalid period type: " + periodType);
        }
    }

    private void handlePeriodOpeningEntry(ATBookPeriod nextPeriod, LocalDate operationDate, BigDecimal amount, ATThirdParty account) {
        if (this.ignoreIntermediatePeriodOpeningEntry) {
            // Only emit balance for the first opening
            int periodIndex = allPeriods.indexOf(nextPeriod);
            if (periodIndex != 0) {
                return;
            }
        }

        if (this.resetOnBookYearOpening) {
            thirdPartyBalanceCache.resetBalanceAmount(nextPeriod, operationDate, account, amount);
        } else {
            thirdPartyBalanceCache.addAmountToBalance(nextPeriod, operationDate, account, amount);
        }
    }

    private void handlePeriodClosingEntry(ATBookPeriod nextPeriod, LocalDate operationDate, BigDecimal amount, ATThirdParty account) {
        thirdPartyBalanceCache.addAmountToBalance(nextPeriod, operationDate, account, amount);
    }


    @Override
    public Spliterator<ATThirdPartyBalance> trySplit() {
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

    private void emitPeriodBalancesUntil(ATBookPeriod maxPeriodExclusive, Consumer<? super ATThirdPartyBalance> action) {
        int maxPeriodIndex = allPeriods.indexOf(maxPeriodExclusive);
        if (maxPeriodIndex < 0) {
            throw new AccountingRuntimeException("Period not found: " + maxPeriodExclusive);
        }

        if (curPeriodIndex >= maxPeriodIndex) {
            return;
        }
        emitPeriodBalances(action, maxPeriodIndex);
    }

    private void emitRemainingPeriodBalances(Consumer<? super ATThirdPartyBalance> action) {
        int allPeriodSize = allPeriods.size();
        emitPeriodBalances(action, allPeriodSize);
    }

    private void emitPeriodBalances(Consumer<? super ATThirdPartyBalance> action, int maxIndex) {
        for (int periodIndex = curPeriodIndex; periodIndex < maxIndex; periodIndex++) {
            ATBookPeriod bookPeriod = allPeriods.get(periodIndex);
            thirdPartyBalanceCache.getPeriodBalances(bookPeriod).forEach(action);
        }
        curPeriodIndex = maxIndex;
    }

}
