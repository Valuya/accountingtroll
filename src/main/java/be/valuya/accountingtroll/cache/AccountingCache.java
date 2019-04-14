package be.valuya.accountingtroll.cache;

import be.valuya.accountingtroll.AccountingService;
import be.valuya.accountingtroll.domain.Account;
import be.valuya.accountingtroll.domain.BookPeriod;
import be.valuya.accountingtroll.domain.BookYear;
import be.valuya.accountingtroll.domain.ThirdParty;

import java.util.List;
import java.util.Optional;

public interface AccountingCache {

    List<BookYear> listBookYears(AccountingService accountingService);

    List<BookPeriod> listPeriods(AccountingService accountingService);

    List<BookPeriod> listYearPeriods(AccountingService accountingService, BookYear bookYear);

    Optional<BookYear> findBookYearByName(AccountingService accountingService, String name);

    Optional<BookPeriod> findPeriodByName(AccountingService accountingService, BookYear bookYear, String name);

    Optional<Account> findAccountByCode(AccountingService accountingService, String code);

    Optional<ThirdParty> findThirdPartyById(AccountingService accountingService, String id);
}
