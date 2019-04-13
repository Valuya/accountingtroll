package be.valuya.accountingtroll.cache;

import be.valuya.accountingtroll.domain.Account;
import be.valuya.accountingtroll.AccountingService;
import be.valuya.accountingtroll.domain.BookYear;
import be.valuya.accountingtroll.Session;
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

    private final Session session;
    private Map<String, BookYear> bookYears;
    private Map<BookYear, List<BookPeriod>> bookPeriods;
    private Map<String, Account> accounts;
    private Map<String, ThirdParty> thirdParties;

    public MemorySessionCache(Session session) {
        this.session = session;
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
    public Optional<ThirdParty> findThirdPartyByNumber(AccountingService accountingService, String number) {
        ThirdParty thirdPartyNullable = getThirdParties(accountingService).get(number);
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
        this.bookPeriods = accountingService.streamPeriods(session)
                .collect(Collectors.groupingBy(BookPeriod::getBookYear));
    }

    private void readBookYears(AccountingService accountingService) {
        this.bookYears = accountingService.streamBookYears(session)
                .collect(Collectors.toMap(
                        BookYear::getName,
                        Function.identity()
                ));
    }


    private void readAccounts(AccountingService accountingService) {
        this.accounts = accountingService.streamAccounts(session)
                .collect(Collectors.toMap(
                        Account::getCode,
                        Function.identity()
                ));
    }


    private void readThirdParties(AccountingService accountingService) {
        this.thirdParties = accountingService.streamThirdParties(session)
                .collect(Collectors.toMap(
                        ThirdParty::getNumber,
                        Function.identity()
                ));
    }
}
