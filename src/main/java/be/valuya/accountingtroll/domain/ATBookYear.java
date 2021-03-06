package be.valuya.accountingtroll.domain;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

@NotNull
public class ATBookYear implements Comparable<ATBookYear> {

    private final static Comparator<ATBookYear> COMPARATOR = Comparator
            .comparing(ATBookYear::getStartDate)
            .thenComparing(ATBookYear::getEndDate)
            .thenComparing(ATBookYear::getName);

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean archived;

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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ATBookYear bookYear = (ATBookYear) o;
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
        return "ATBookYear{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    @Override
    public int compareTo(ATBookYear o) {
        return COMPARATOR.compare(this, o);
    }
}
