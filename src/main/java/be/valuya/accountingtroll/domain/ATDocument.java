package be.valuya.accountingtroll.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class ATDocument {

    private String id;
    private ATBookPeriod bookPeriod;
    private String dbkCode;
    private String documentNumber;

    private Optional<String> providerReference;
    private Optional<LocalDate> dateOptional = Optional.empty();
    private Optional<Integer> pageCountOptional = Optional.empty();
    private Optional<Integer> partCountOptional = Optional.empty();
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

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
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

    public Optional<String> getProviderReference() {
        return providerReference;
    }

    public void setProviderReference(Optional<String> providerReference) {
        this.providerReference = providerReference;
    }

    public Optional<Integer> getPartCountOptional() {
        return partCountOptional;
    }

    public void setPartCountOptional(Optional<Integer> partCountOptional) {
        this.partCountOptional = partCountOptional;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATDocument that = (ATDocument) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(bookPeriod, that.bookPeriod) &&
                Objects.equals(dbkCode, that.dbkCode) &&
                Objects.equals(documentNumber, that.documentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookPeriod, dbkCode, documentNumber);
    }
}
