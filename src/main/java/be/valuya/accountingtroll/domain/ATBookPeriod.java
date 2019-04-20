package be.valuya.accountingtroll.domain;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@NotNull
public class ATBookPeriod {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private ATBookYear bookYear;
    private ATPeriodType periodType;

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

    public ATPeriodType getPeriodType() {
        return periodType;
    }

    public void setPeriodType(ATPeriodType periodType) {
        this.periodType = periodType;
    }

    @Override
    public String toString() {
        return "ATBookPeriod{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", periodType=" + periodType +
                ", bookYear=" + bookYear.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATBookPeriod that = (ATBookPeriod) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(bookYear, that.bookYear) &&
                periodType == that.periodType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDate, endDate, bookYear, periodType);
    }
}
