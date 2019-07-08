package be.valuya.accountingtroll.domain;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

@NotNull
public class ATAccountingEntry implements Comparable<ATAccountingEntry> {

    private final static Comparator<ATAccountingEntry> COMPARATOR = Comparator
            .comparing(ATAccountingEntry::getBookPeriod)
            .thenComparing(ATAccountingEntry::getDate)
            .thenComparing(ATAccountingEntry::getDocNumber);

    private ATBookPeriod bookPeriod;
    private LocalDate date;
    /**
     * Entry amount. Positive values for a 'credit' entry, negative values for a 'debit' entry, regardless
     * of the account type.
     */
    private BigDecimal amount;
    /**
     * The journal code.
     */
    private String dbkCode;
    /**
     * Type of journal
     */
    private AccountingEntryType accountingEntryType;

    /**
     * The document number. This represent the id of the document within a journal/period.
     * Optionally, the doc number may be typed.
     */
    private String docNumber;
    /**
     * An optional type for the docNumber
     */
    private Optional<AccountingEntryDocumentNumberType> docNumberTypeOptional = Optional.empty();
    /**
     * An index to order entries
     */
    private int orderingNumber;
    // TODO: remove
    private AccountingEntryDocumentType accountingEntryDocumentType;
    private ATAccount account;
    /**
     * Each entry should be matched by another, so that the overall balance is zero.
     * Sometimes, the related entry is not (yet) present.
     */
    private boolean matched;

    /**
     * The document from which this entry was derived.
     */
    private Optional<ATDocument> documentOptional = Optional.empty();
    private Optional<ATTax> taxOptional = Optional.empty();
    private Optional<ATThirdParty> thirdPartyOptional = Optional.empty();
    private Optional<LocalDate> documentDateOptional = Optional.empty();
    private Optional<LocalDate> dueDateOptional = Optional.empty();
    private Optional<String> commentOptional = Optional.empty();
    private Optional<ATAccountingEntry> matchedEntry = Optional.empty();

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

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public ATAccount getAccount() {
        return account;
    }

    public void setAccount(ATAccount account) {
        this.account = account;
    }

    public Optional<ATAccountingEntry> getMatchedEntry() {
        return matchedEntry;
    }

    public void setMatchedEntry(Optional<ATAccountingEntry> matchedEntry) {
        this.matchedEntry = matchedEntry;
    }

    public int getOrderingNumber() {
        return orderingNumber;
    }

    public void setOrderingNumber(int orderingNumber) {
        this.orderingNumber = orderingNumber;
    }

    public Optional<AccountingEntryDocumentNumberType> getDocNumberTypeOptional() {
        return docNumberTypeOptional;
    }

    public void setDocNumberTypeOptional(Optional<AccountingEntryDocumentNumberType> docNumberTypeOptional) {
        this.docNumberTypeOptional = docNumberTypeOptional;
    }

    @Override
    public String toString() {
        return "ATAccountingEntry{" +
                "bookPeriod=" + bookPeriod +
                ", date=" + date +
                ", amount=" + amount +
                ", dbkCode='" + dbkCode + '\'' +
                ", accountingEntryType=" + accountingEntryType +
                ", account=" + account +
                ", docNumber=" + docNumber +
                '}';
    }

    @Override
    public int compareTo(ATAccountingEntry o) {
        return COMPARATOR.compare(this, o);
    }
}
