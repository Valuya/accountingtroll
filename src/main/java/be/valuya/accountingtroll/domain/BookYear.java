package be.valuya.accountingtroll.domain;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@NotNull
public class BookYear {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookYear bookYear = (BookYear) o;
        return Objects.equals(name, bookYear.name) &&
                Objects.equals(startDate, bookYear.startDate) &&
                Objects.equals(endDate, bookYear.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDate, endDate);
    }

    @Override
    public String toString() {
        return "BookYear{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
