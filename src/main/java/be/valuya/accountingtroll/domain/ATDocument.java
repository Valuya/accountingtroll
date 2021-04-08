package be.valuya.accountingtroll.domain;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class ATDocument {

    @NotNull
    private String id;
    @NotNull
    private ATBookPeriod bookPeriod;
    @NotNull
    private String documentNumber;
    @NotNull
    private String dbkCode;

    private String providerReference;
    private String absolutePath;
    private LocalDate date;
    private Integer pageCount;
    private Integer partCount;
    private LocalDateTime creationTime;
    private LocalDateTime updateTime;

    public String getId() {
        return id;
    }

    public ATBookPeriod getBookPeriod() {
        return bookPeriod;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getDbkCode() {
        return dbkCode;
    }

    public Optional<String> getIdOptional() {
        return Optional.ofNullable(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public Optional<ATBookPeriod> getBookPeriodOptional() {
        return Optional.ofNullable(bookPeriod);
    }

    public void setBookPeriod(ATBookPeriod bookPeriod) {
        this.bookPeriod = bookPeriod;
    }

    public Optional<String> getDocumentNumberOptional() {
        return Optional.ofNullable(documentNumber);
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Optional<String> getDbkCodeOptional() {
        return Optional.ofNullable(dbkCode);
    }

    public void setDbkCode(String dbkCode) {
        this.dbkCode = dbkCode;
    }

    public Optional<String> getProviderReferenceOptional() {
        return Optional.ofNullable(providerReference);
    }

    public void setProviderReference(String providerReference) {
        this.providerReference = providerReference;
    }

    public Optional<LocalDate> getDateOptional() {
        return Optional.ofNullable(date);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Optional<Integer> getPageCountOptional() {
        return Optional.ofNullable(pageCount);
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Optional<Integer> getPartCountOptional() {
        return Optional.ofNullable(partCount);
    }

    public void setPartCount(Integer partCount) {
        this.partCount = partCount;
    }

    public Optional<LocalDateTime> getCreationTimeOptional() {
        return Optional.ofNullable(creationTime);
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Optional<LocalDateTime> getUpdateTimeOptional() {
        return Optional.ofNullable(updateTime);
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
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
