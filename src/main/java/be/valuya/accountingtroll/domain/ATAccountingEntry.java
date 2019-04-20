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
    private AccountingEntryDocumentType accountingEntryDocumentType;
    private AccountingEntryType accountingEntryType;
    private ATAccount account;
    private boolean matched;
    private int docNumber;

    private Optional<ATDocument> documentOptional = Optional.empty();
    private Optional<ATDocument> matchedDocumentOptional = Optional.empty();
    private Optional<ATTax> taxOptional = Optional.empty();
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

    public AccountingEntryDocumentType getAccountingEntryDocumentType() {
        return accountingEntryDocumentType;
    }

    public void setAccountingEntryDocumentType(AccountingEntryDocumentType accountingEntryDocumentType) {
        this.accountingEntryDocumentType = accountingEntryDocumentType;
    }

    public AccountingEntryType getAccountingEntryType() {
        return accountingEntryType;
    }

    public void setAccountingEntryType(AccountingEntryType accountingEntryType) {
        this.accountingEntryType = accountingEntryType;
    }

    public Optional<ATDocument> getDocumentOptional() {
        return documentOptional;
    }

    public void setDocumentOptional(Optional<ATDocument> documentOptional) {
        this.documentOptional = documentOptional;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public Optional<ATDocument> getMatchedDocumentOptional() {
        return matchedDocumentOptional;
    }

    public void setMatchedDocumentOptional(Optional<ATDocument> matchedDocumentOptional) {
        this.matchedDocumentOptional = matchedDocumentOptional;
    }

    public int getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(int docNumber) {
        this.docNumber = docNumber;
    }

    public ATAccount getAccount() {
        return account;
    }

    public void setAccount(ATAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "ATAccountingEntry{" +
                "bookPeriod=" + bookPeriod +
                ", date=" + date +
                ", amount=" + amount +
                ", dbkCode='" + dbkCode + '\'' +
                ", account=" + account +
                '}';
    }
}
