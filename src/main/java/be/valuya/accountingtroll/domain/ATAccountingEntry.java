package be.valuya.accountingtroll.domain;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@NotNull
public class ATAccountingEntry {

    private ATBookPeriod bookPeriod;
    private LocalDate date;
    private BigDecimal amount;
    private String dbkCode;
    private Optional<ATTax> taxOptional = Optional.empty();
    private Optional<ATAccount> accountOptional = Optional.empty();
    private Optional<ATThirdParty> thirdPartyOptional = Optional.empty();
    private Optional<LocalDate> documentDateOptional = Optional.empty();
    private Optional<LocalDate> dueDateOptional = Optional.empty();
    private Optional<String> commentOptional = Optional.empty();

    public ATBookPeriod getBookPeriod() {
        return bookPeriod;
    }

    public void setBookPeriod(ATBookPeriod bookPeriod) {
        this.bookPeriod = bookPeriod;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDbkCode() {
        return dbkCode;
    }

    public void setDbkCode(String dbkCode) {
        this.dbkCode = dbkCode;
    }

    public Optional<ATAccount> getAccountOptional() {
        return accountOptional;
    }

    public void setAccountOptional(Optional<ATAccount> accountOptional) {
        this.accountOptional = accountOptional;
    }

    public Optional<ATThirdParty> getThirdPartyOptional() {
        return thirdPartyOptional;
    }

    public void setThirdPartyOptional(Optional<ATThirdParty> thirdPartyOptional) {
        this.thirdPartyOptional = thirdPartyOptional;
    }

    public Optional<LocalDate> getDocumentDateOptional() {
        return documentDateOptional;
    }

    public void setDocumentDateOptional(Optional<LocalDate> documentDateOptional) {
        this.documentDateOptional = documentDateOptional;
    }

    public Optional<ATTax> getTaxOptional() {
        return taxOptional;
    }

    public void setTaxOptional(Optional<ATTax> taxOptional) {
        this.taxOptional = taxOptional;
    }

    public Optional<LocalDate> getDueDateOptional() {
        return dueDateOptional;
    }

    public void setDueDateOptional(Optional<LocalDate> dueDateOptional) {
        this.dueDateOptional = dueDateOptional;
    }

    public Optional<String> getCommentOptional() {
        return commentOptional;
    }

    public void setCommentOptional(Optional<String> commentOptional) {
        this.commentOptional = commentOptional;
    }


    @Override
    public String toString() {
        return "ATAccountingEntry{" +
                "accountOptional=" + accountOptional +
                ", thirdPartyOptional=" + thirdPartyOptional +
                ", bookPeriod=" + bookPeriod +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
