package be.valuya.accountingtroll;

import java.time.LocalDate;

public interface TrollPeriod {

    String getName();

    LocalDate getStartDate();

    LocalDate getEndDate();

    TrollBookYear getBookYear();
}
