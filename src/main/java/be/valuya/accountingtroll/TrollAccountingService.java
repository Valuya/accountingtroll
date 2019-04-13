package be.valuya.accountingtroll;


import javax.validation.constraints.NotNull;
import java.util.stream.Stream;

public interface TrollAccountingService {

    @NotNull
    Stream<TrollAccount> streamAccounts(@NotNull TrollSession session);

    @NotNull
    Stream<TrollBookYear> streamBookYears(@NotNull TrollSession session);

    @NotNull
    Stream<TrollPeriod> streamPeriods(@NotNull TrollSession session);

    @NotNull
    Stream<TrollThirdParty> streamThirdParties(@NotNull TrollSession session);

    @NotNull
    Stream<TrollAccountingEntry> streamAccountingEntries(@NotNull TrollSession session);

}
