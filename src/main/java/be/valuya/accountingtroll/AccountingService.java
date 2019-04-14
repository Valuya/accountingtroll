package be.valuya.accountingtroll;


import be.valuya.accountingtroll.domain.Account;
import be.valuya.accountingtroll.domain.AccountingEntry;
import be.valuya.accountingtroll.domain.BookYear;
import be.valuya.accountingtroll.domain.BookPeriod;
import be.valuya.accountingtroll.domain.ThirdParty;

import javax.validation.constraints.NotNull;
import java.util.stream.Stream;

public interface AccountingService<S extends AccountingSession> {

    @NotNull
    Stream<Account> streamAccounts(@NotNull S accountingSession);

    @NotNull
    Stream<BookYear> streamBookYears(@NotNull S accountingSession);

    @NotNull
    Stream<BookPeriod> streamPeriods(@NotNull S accountingSession);

    @NotNull
    Stream<ThirdParty> streamThirdParties(@NotNull S accountingSession);

    @NotNull
    Stream<AccountingEntry> streamAccountingEntries(@NotNull AccountingSession accountingSession);

}
