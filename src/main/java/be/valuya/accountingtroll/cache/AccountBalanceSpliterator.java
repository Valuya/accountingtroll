package be.valuya.accountingtroll.cache;

import be.valuya.accountingtroll.domain.ATAccount;
import be.valuya.accountingtroll.domain.ATAccountBalance;
import be.valuya.accountingtroll.domain.ATAccountingEntry;
import be.valuya.accountingtroll.domain.ATBookPeriod;
import be.valuya.accountingtroll.domain.ATBookYear;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountBalanceSpliterator implements Spliterator<ATAccountBalance> {
    private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(3, RoundingMode.UNNECESSARY);

    private final AccountBalanceCache accountBalanceCache;
    private final Iterator<ATAccountingEntry> accountingEntriesIterator;
    private final List<ATBookPeriod> openingPeriods;

    public AccountBalanceSpliterator(Stream<ATAccountingEntry> accountingEntriesStream, List<ATBookPeriod> openingPeriods) {
        this.accountingEntriesIterator = accountingEntriesStream.iterator();
        this.openingPeriods = openingPeriods;
        this.accountBalanceCache = new AccountBalanceCache(openingPeriods);
    }

    @Override
    public boolean tryAdvance(Consumer<? super ATAccountBalance> action) {
        boolean hasNextEntry = accountingEntriesIterator.hasNext();
        if (hasNextEntry) {
            ATAccountingEntry nextEntry = accountingEntriesIterator.next();
            ATBookPeriod bookPeriod = nextEntry.getBookPeriod();
            ATBookYear bookYear = bookPeriod.getBookYear();

            Optional<ATBookYear> lastBookYearOptional = accountBalanceCache.getLastBookYear();
            boolean isNewBookYear = lastBookYearOptional
                    .filter(lasstBookyear -> !lasstBookyear.equals(bookYear))
                    .isPresent();
            // When switching to a new year, first emit resetted balances
            if (isNewBookYear) {
                ATBookYear lastBookyear = lastBookYearOptional.orElseThrow(IllegalStateException::new);
                accountBalanceCache.findAccountWithoutBalanceInBookYear(lastBookyear).stream()
                        .filter(ATAccount::isYearlyBalanceReset)
                        .map(account -> this.getResettedBalance(account, bookYear))
                        .map(this::cloneBalance)
                        .forEach(action);
            }
            // Update the balance with the current entry and emit it
            ATAccountBalance updatedBalance = this.accountBalanceCache.appendToAccountBalance(nextEntry);
            ATAccountBalance updatedBalanceClone = this.cloneBalance(updatedBalance);
            updatedBalanceClone.setAtAccountingEntryOptional(Optional.of(nextEntry));
            action.accept(updatedBalanceClone);
            return true;
        }
        // No more entries, check if balance reset event must be emitted
        List<ATAccountBalance> resettedBalances = accountBalanceCache.getLastBookYear()
                .map(this::getYearlyResettedBalances)
                .orElseGet(ArrayList::new);
        if (resettedBalances.isEmpty()) {
            return false;
        } else {
            resettedBalances.stream()
                    .map(this::cloneBalance)
                    .forEach(action);
            return true;
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

    private List<ATAccountBalance> getYearlyResettedBalances(ATBookYear bookYear) {
        return accountBalanceCache.findAccountWithoutBalanceInBookYear(bookYear)
                .stream()
                .filter(ATAccount::isYearlyBalanceReset)
                .map(account -> this.getResettedBalance(account, bookYear))
                .collect(Collectors.toList());
    }

    private ATAccountBalance getResettedBalance(ATAccount account, ATBookYear bookYear) {
        ATBookPeriod openingPeriod = openingPeriods.stream()
                .filter(p -> p.getBookYear().equals(bookYear))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No opening period for book year " + bookYear));
        return accountBalanceCache.resetAccountBalance(account, openingPeriod, ZERO);
    }

    private ATAccountBalance cloneBalance(ATAccountBalance accountBalance) {
        ATAccountBalance clone = new ATAccountBalance();
        clone.setPeriod(accountBalance.getPeriod());
        clone.setDate(accountBalance.getDate());
        clone.setBalance(accountBalance.getBalance());
        clone.setAccount(accountBalance.getAccount());
        return clone;
    }

}
