package be.valuya.accountingtroll.cache;

import be.valuya.accountingtroll.domain.Account;
import be.valuya.accountingtroll.AccountingService;
import be.valuya.accountingtroll.domain.BookYear;
import be.valuya.accountingtroll.AccountingSession;
import be.valuya.accountingtroll.domain.BookPeriod;
import be.valuya.accountingtroll.domain.ThirdParty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MemorySessionCache implements AccountingCache {

    private final AccountingSession accountingSession;
    private Map<String, BookYear> bookYears;
    private Map<BookYear, List<BookPeriod>> bookPeriods;
    private Map<String, Account> accounts;
    private Map<String, ThirdParty> thirdParties;

    public MemorySessionCache(AccountingSession accountingSession) {
        this.accountingSession = accountingSession;
    }


    @Override
    public List<BookYear> listBookYears(AccountingService accountingService) {
        return new ArrayList<>(getBookYears(accountingService).values());
    }

    @Override
    public List<BookPeriod> listPeriods(AccountingService accountingService) {
        return getBookPeriods(accountingService).values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookPeriod> listYearPeriods(AccountingService accountingService, BookYear bookYear) {
        List<BookPeriod> periodsNullable = getBookPeriods(accountingService).get(bookYear);
        return Optional.ofNullable(periodsNullable).orElseGet(ArrayList::new);
    }

    @Override
    public Optional<BookYear> findBookYearByName(AccountingService accountingService, String name) {
        BookYear bookYearNullable = getBookYears(accountingService).get(name);
        return Optional.ofNullable(bookYearNullable);
    }

    @Override
    public Optional<BookPeriod> findPeriodByName(AccountingService accountingService, BookYear bookYear, String name) {
        List<BookPeriod> bookYearPeriods = getBookPeriods(accountingService).get(bookYear);
        return bookYearPeriods.stream()
                .filter(p -> p.getName().equals(name))
                .findAny();
    }

    @Override
    public Optional<Account> findAccountByCode(AccountingService accountingService, String code) {
        Account accountNullable = getAccounts(accountingService).get(code);
        return Optional.ofNullable(accountNullable);
    }

    @Override
    public Optional<ThirdParty> findThirdPartyById(AccountingService accountingService, String id) {
        ThirdParty thirdPartyNullable = getThirdParties(accountingService).get(id);
        return Optional.ofNullable(thirdPartyNullable);
    }


    private Map<String, BookYear> getBookYears(AccountingService accountingService) {
        if (bookYears == null) {
            readBookYears(accountingService);
        }
        return bookYears;
    }

    private Map<BookYear, List<BookPeriod>> getBookPeriods(AccountingService accountingService) {
        if (bookPeriods == null) {
            readPeriods(accountingService);
        }
        return this.bookPeriods;
    }


    private Map<String, Account> getAccounts(AccountingService accountingService) {
        if (accounts == null) {
            readAccounts(accountingService);
        }
        return accounts;
    }


    private Map<String, ThirdParty> getThirdParties(AccountingService accountingService) {
        if (thirdParties == null) {
            readThirdParties(accountingService);
        }
        return thirdParties;
    }

    private void readPeriods(AccountingService accountingService) {
        this.bookPeriods = accountingService.streamPeriods(accountingSession)
                .collect(Collectors.groupingBy(BookPeriod::getBookYear));
    }

    private void readBookYears(AccountingService accountingService) {
        this.bookYears = accountingService.streamBookYears(accountingSession)
                .collect(Collectors.toMap(
                        BookYear::getName,
                        Function.identity()
                ));
    }


    private void readAccounts(AccountingService accountingService) {
        this.accounts = accountingService.streamAccounts(accountingSession)
                .collect(Collectors.toMap(
                        Account::getCode,
                        Function.identity()
                ));
    }


    private void readThirdParties(AccountingService accountingService) {
        this.thirdParties = accountingService.streamThirdParties(accountingSession)
                .collect(Collectors.toMap(
                        ThirdParty::getId,
                        Function.identity()
                ));
    }
}
