package be.valuya.accountingtroll;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface TrollAccountingEntry {

    String getAccountCode();

    String getRemotePartyName();

    String getPeriodName();

    LocalDate getDate();

    BigDecimal getAmountEur();

    BigDecimal getVatRate();

    BigDecimal getAccountBalance();

    Optional<LocalDate> getDocDate();

    Optional<LocalDate> getDueDate();

    Optional<String> getComment();

}
