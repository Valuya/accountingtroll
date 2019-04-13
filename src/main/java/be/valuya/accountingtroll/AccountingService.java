package be.valuya.accountingtroll;


import be.valuya.accountingtroll.domain.Account;
import be.valuya.accountingtroll.domain.AccountingEntry;
import be.valuya.accountingtroll.domain.BookYear;
import be.valuya.accountingtroll.domain.BookPeriod;
import be.valuya.accountingtroll.domain.ThirdParty;

import javax.validation.constraints.NotNull;
import java.util.stream.Stream;

public interface AccountingService {

    @NotNull
    Stream<Account> streamAccounts(@NotNull Session session);

    @NotNull
    Stream<BookYear> streamBookYears(@NotNull Session session);

    @NotNull
    Stream<BookPeriod> streamPeriods(@NotNull Session session);

    @NotNull
    Stream<ThirdParty> streamThirdParties(@NotNull Session session);

    @NotNull
    Stream<AccountingEntry> streamAccountingEntries(@NotNull Session session);

}
