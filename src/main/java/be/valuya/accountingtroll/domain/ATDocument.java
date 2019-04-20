package be.valuya.accountingtroll.domain;

import java.time.LocalDate;
import java.util.Objects;

public class ATDocument {

    private String id;
    private ATBookPeriod bookPeriod;
    private String dbkCode;
    private int docNumber;

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

    public int getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(int docNumber) {
        this.docNumber = docNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATDocument that = (ATDocument) o;
        return docNumber == that.docNumber &&
                Objects.equals(id, that.id) &&
                Objects.equals(bookPeriod, that.bookPeriod) &&
                Objects.equals(dbkCode, that.dbkCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookPeriod, dbkCode, docNumber);
    }

    @Override
    public String toString() {
        return "ATDocument{" +
                "id='" + id + '\'' +
                ", bookPeriod=" + bookPeriod +
                ", dbkCode='" + dbkCode + '\'' +
                ", docNumber=" + docNumber +
                '}';
    }
}
