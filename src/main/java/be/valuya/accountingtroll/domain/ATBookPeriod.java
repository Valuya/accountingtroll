package be.valuya.accountingtroll.domain;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NotNull
public class ATBookPeriod {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private ATBookYear bookYear;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ATBookYear getBookYear() {
        return bookYear;
    }

    public void setBookYear(ATBookYear bookYear) {
        this.bookYear = bookYear;
    }

    @Override
    public String toString() {
        return "ATBookPeriod{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", bookYear=" + bookYear +
                '}';
    }
}
