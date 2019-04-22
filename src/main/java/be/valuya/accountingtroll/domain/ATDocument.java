package be.valuya.accountingtroll.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class ATDocument {

    private String id;
    private ATBookPeriod bookPeriod;
    private String dbkCode;
    private String documentNumnber;

    private Optional<LocalDate> dateOptional = Optional.empty();
    private Optional<Integer> pageCountOptional = Optional.empty();
    private Optional<LocalDateTime> creationTimeOptional = Optional.empty();
    private Optional<LocalDateTime> updateTimeOptional = Optional.empty();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ATBookPeriod getBookPeriod() {
        return bookPeriod;
    }

    public void setBookPeriod(ATBookPeriod bookPeriod) {
        this.bookPeriod = bookPeriod;
    }

    public String getDbkCode() {
        return dbkCode;
    }

    public void setDbkCode(String dbkCode) {
        this.dbkCode = dbkCode;
    }

    public String getDocumentNumnber() {
        return documentNumnber;
    }

    public void setDocumentNumnber(String documentNumnber) {
        this.documentNumnber = documentNumnber;
    }

    public Optional<LocalDate> getDateOptional() {
        return dateOptional;
    }

    public void setDateOptional(Optional<LocalDate> dateOptional) {
        this.dateOptional = dateOptional;
    }

    public Optional<Integer> getPageCountOptional() {
        return pageCountOptional;
    }

    public void setPageCountOptional(Optional<Integer> pageCountOptional) {
        this.pageCountOptional = pageCountOptional;
    }

    public Optional<LocalDateTime> getCreationTimeOptional() {
        return creationTimeOptional;
    }

    public void setCreationTimeOptional(Optional<LocalDateTime> creationTimeOptional) {
        this.creationTimeOptional = creationTimeOptional;
    }

    public Optional<LocalDateTime> getUpdateTimeOptional() {
        return updateTimeOptional;
    }

    public void setUpdateTimeOptional(Optional<LocalDateTime> updateTimeOptional) {
        this.updateTimeOptional = updateTimeOptional;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATDocument that = (ATDocument) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(bookPeriod, that.bookPeriod) &&
                Objects.equals(dbkCode, that.dbkCode) &&
                Objects.equals(documentNumnber, that.documentNumnber) &&
                Objects.equals(dateOptional, that.dateOptional) &&
                Objects.equals(pageCountOptional, that.pageCountOptional) &&
                Objects.equals(creationTimeOptional, that.creationTimeOptional) &&
                Objects.equals(updateTimeOptional, that.updateTimeOptional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookPeriod, dbkCode, documentNumnber, dateOptional, pageCountOptional, creationTimeOptional, updateTimeOptional);
    }
}
