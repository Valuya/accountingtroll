package be.valuya.accountingtroll;


import be.valuya.accountingtroll.domain.Account;
import be.valuya.accountingtroll.domain.AccountingEntry;
import be.valuya.accountingtroll.domain.BookPeriod;
import be.valuya.accountingtroll.domain.BookYear;
import be.valuya.accountingtroll.domain.ThirdParty;

import java.util.stream.Stream;

public interface AccountingManager {

    Stream<Account> streamAccounts();

    Stream<BookYear> streamBookYears();

    Stream<BookPeriod> streamPeriods();

    Stream<ThirdParty> streamThirdParties();

    Stream<AccountingEntry> streamAccountingEntries(AccountingEventListener accountingEventListener);

}
