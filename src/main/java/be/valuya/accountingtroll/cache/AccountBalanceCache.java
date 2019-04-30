package be.valuya.accountingtroll.cache;

import be.valuya.accountingtroll.domain.ATAccount;
import be.valuya.accountingtroll.domain.ATAccountBalance;
import be.valuya.accountingtroll.domain.ATAccountingEntry;
import be.valuya.accountingtroll.domain.ATBookPeriod;
import be.valuya.accountingtroll.domain.ATBookYear;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

public class AccountBalanceCache {

    private Map<ATBookYear, BookYearAccountBalanceCache> bookYearBalances = new ConcurrentSkipListMap<>();
    private List<ATBookPeriod> openingPeriods;

    public AccountBalanceCache(List<ATBookPeriod> openingPeriods) {
        this.openingPeriods = openingPeriods;
    }

    public ATAccountBalance resetAccountBalance(ATAccountingEntry accountingEntry) {
        ATAccount account = accountingEntry.getAccount();
        ATBookPeriod bookPeriod = accountingEntry.getBookPeriod();
        ATBookYear bookYear = bookPeriod.getBookYear();
        BookYearAccountBalanceCache bookYearBalanceCache = getBookYearBalanceCache(bookYear);
        return bookYearBalanceCache.resetAccountBalance(account, accountingEntry);
    }

    public ATAccountBalance resetAccountBalance(ATAccount account, ATBookPeriod bookPeriod, BigDecimal balance) {
        ATBookYear bookYear = bookPeriod.getBookYear();
        BookYearAccountBalanceCache bookYearBalanceCache = getBookYearBalanceCache(bookYear);
        return bookYearBalanceCache.resetAccountBalance(account, bookPeriod, balance);
    }

    public ATAccountBalance appendToAccountBalance(ATAccountingEntry accountingEntry) {
        ATAccount account = accountingEntry.getAccount();
        ATBookPeriod bookPeriod = accountingEntry.getBookPeriod();
        ATBookYear bookYear = bookPeriod.getBookYear();
        BookYearAccountBalanceCache bookYearBalanceCache = getBookYearBalanceCache(bookYear);
        return bookYearBalanceCache.appendAccountBalance(account, accountingEntry);
    }

    public List<ATAccount> findAccountWithoutBalanceInBookYear(ATBookYear bookYear) {
        Optional<BookYearAccountBalanceCache> accountBalanceCacheOptional = Optional.ofNullable(bookYearBalances.get(bookYear));
        return this.findAccountsWithoutBalanceInCache(bookYear, accountBalanceCacheOptional);
    }

    public Optional<ATBookYear> getLastBookYear() {
        return findLastBookYearCache()
                .map(BookYearAccountBalanceCache::getBookYear);
    }

    public ATAccountBalance getAccountBalance(ATAccount account, ATBookYear bookYear) {
        BookYearAccountBalanceCache bookYearBalanceCache = getBookYearBalanceCache(bookYear);
        return bookYearBalanceCache.getBalance(account);
    }

    private BookYearAccountBalanceCache getBookYearBalanceCache(ATBookYear bookYear) {
        BookYearAccountBalanceCache bookYearAccountBalanceCache = bookYearBalances.computeIfAbsent(bookYear, this::createBookYearBalance);
        return bookYearAccountBalanceCache;
    }

    private BookYearAccountBalanceCache createBookYearBalance(ATBookYear bookYear) {
        Optional<BookYearAccountBalanceCache> lastCacheOptional = findLastBookYearCache(bookYear);
        ATBookPeriod openingPeriod = this.findOpeningPeriod(bookYear);

        BookYearAccountBalanceCache newCache = new BookYearAccountBalanceCache(bookYear, lastCacheOptional, openingPeriod);
        bookYearBalances.put(bookYear, newCache);
        return newCache;
    }

    private ATBookPeriod findOpeningPeriod(ATBookYear bookYear) {
        return openingPeriods.stream()
                .filter(p -> p.getBookYear().equals(bookYear))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("No opening period found for book year " + bookYear));
    }

    private Optional<BookYearAccountBalanceCache> findLastBookYearCache() {
        return bookYearBalances.keySet()
                .stream()
                .sorted(Comparator.reverseOrder())
                .map(bookYearBalances::get)
                .findFirst();
    }


    private Optional<BookYearAccountBalanceCache> findLastBookYearCache(ATBookYear maxBookyearEclusive) {
        return bookYearBalances.keySet()
                .stream()
                .sorted(Comparator.reverseOrder())
                .filter(year -> year.compareTo(maxBookyearEclusive) < 0)
                .map(bookYearBalances::get)
                .findFirst();
    }

    private List<ATAccount> findAccountsWithoutBalanceInCache(ATBookYear bookYear, Optional<BookYearAccountBalanceCache> bookYearcacheOptional) {
        List<ATAccount> accountsWithBalanceForThatBookYear = bookYearcacheOptional
                .map(BookYearAccountBalanceCache::getAccountsWithBalance)
                .orElseGet(ArrayList::new);

        return bookYearBalances.keySet().stream()
                .filter(year -> !year.equals(bookYear))
                .map(bookYearBalances::get)
                .map(BookYearAccountBalanceCache::getAccountsWithBalance)
                .map(accounts -> this.listDifference(accounts, accountsWithBalanceForThatBookYear))
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<ATAccount> listDifference(List<ATAccount> accounts, List<ATAccount> accountsWithBalanceForLastBookyear) {
        return accounts.stream()
                .filter(account -> !accountsWithBalanceForLastBookyear.contains(account))
                .collect(Collectors.toList());
    }

}
