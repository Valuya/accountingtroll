package be.valuya.accountingtroll.domain;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

@NotNull
public class ATAccountingEntry implements Comparable<ATAccountingEntry> {

    private final static Comparator<ATAccountingEntry> COMPARATOR = Comparator.nullsLast(
            Comparator.<ATAccountingEntry, ATBookPeriod>comparing(e -> e.getBookPeriodOptional().orElse(null))
                    .thenComparing(e -> e.getDateOptional().orElse(null))
                    .thenComparing(e -> e.getDocNumberOptional().orElse(null))
    );

    @NotNull
    private ATBookPeriod bookPeriod;
    @NotNull
    private LocalDate date;
    /**
     * Entry amount. Positive values for a 'credit' entry, negative values for a 'debit' entry, regardless
     * of the account type.
     */
    @NotNull
    private BigDecimal amount;
    /**
     * The journal code.
     */
    @NotNull
    private String dbkCode;
    /**
     * Type of journal
     */
    @NotNull
    private AccountingEntryType accountingEntryType;

    /**
     * The document number. This represent the id of the document within a journal/period.
     * Optionally, the doc number may be typed.
     */
    @NotNull
    private String docNumber;
    /**
     * An optional type for the docNumber
     */
    private AccountingEntryDocumentNumberType docNumberType;
    /**
     * An index to order entries
     */
    private int orderingNumber;
    private AccountingEntryDocumentType accountingEntryDocumentType;
    @NotNull
    private ATAccount account;
    /**
     * Each entry should be matched by another, so that the overall balance is zero.
     * Sometimes, the related entry is not (yet) present.
     */
    private boolean matched;

    /**
     * Once matched, each entry is assigned an unique number (the same for both matched entries).
     * This is known as 'lettrage' in the business vocabulary.
     */
    private String matchNumber;

    /**
     * The document from which this entry was derived.
     */
    private ATDocument document;

    /**
     * Vat details
     */
    private ATTax tax;

    /**
     * Currency details
     */
    private ATCurrencyAmount currencyAmount;
    private ATThirdParty thirdParty;
    private LocalDate documentDate;
    private LocalDate dueDate;
    private String comment;
    private ATAccountingEntry matchedEntry;

    public ATBookPeriod getBookPeriod() {
        return bookPeriod;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDbkCode() {
        return dbkCode;
    }

    public AccountingEntryType getAccountingEntryType() {
        return accountingEntryType;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public ATAccount getAccount() {
        return account;
    }

    public Optional<ATBookPeriod> getBookPeriodOptional() {
        return Optional.ofNullable(bookPeriod);
    }

    public void setBookPeriod(ATBookPeriod bookPeriod) {
        this.bookPeriod = bookPeriod;
    }

    public Optional<LocalDate> getDateOptional() {
        return Optional.ofNullable(date);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Optional<BigDecimal> getAmountOptional() {
        return Optional.ofNullable(amount);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Optional<String> getDbkCodeOptional() {
        return Optional.ofNullable(dbkCode);
    }

    public void setDbkCode(String dbkCode) {
        this.dbkCode = dbkCode;
    }

    public Optional<AccountingEntryType> getAccountingEntryTypeOptional() {
        return Optional.ofNullable(accountingEntryType);
    }

    public void setAccountingEntryType(AccountingEntryType accountingEntryType) {
        this.accountingEntryType = accountingEntryType;
    }

    public Optional<String> getDocNumberOptional() {
        return Optional.ofNullable(docNumber);
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Optional<AccountingEntryDocumentNumberType> getDocNumberTypeOptional() {
        return Optional.ofNullable(docNumberType);
    }

    public void setDocNumberType(AccountingEntryDocumentNumberType docNumberType) {
        this.docNumberType = docNumberType;
    }

    public void setOrderingNumber(int orderingNumber) {
        this.orderingNumber = orderingNumber;
    }

    public Optional<AccountingEntryDocumentType> getAccountingEntryDocumentTypeOptional() {
        return Optional.ofNullable(accountingEntryDocumentType);
    }

    public void setAccountingEntryDocumentType(AccountingEntryDocumentType accountingEntryDocumentType) {
        this.accountingEntryDocumentType = accountingEntryDocumentType;
    }

    public Optional<ATAccount> getAccountOptional() {
        return Optional.ofNullable(account);
    }

    public void setAccount(ATAccount account) {
        this.account = account;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public Optional<ATDocument> getDocumentOptional() {
        return Optional.ofNullable(document);
    }

    public void setDocument(ATDocument document) {
        this.document = document;
    }

    public Optional<ATTax> getTaxOptional() {
        return Optional.ofNullable(tax);
    }

    public void setTax(ATTax tax) {
        this.tax = tax;
    }

    public Optional<ATThirdParty> getThirdPartyOptional() {
        return Optional.ofNullable(thirdParty);
    }

    public void setThirdParty(ATThirdParty thirdParty) {
        this.thirdParty = thirdParty;
    }

    public Optional<LocalDate> getDocumentDateOptional() {
        return Optional.ofNullable(documentDate);
    }

    public void setDocumentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
    }

    public Optional<LocalDate> getDueDateOptional() {
        return Optional.ofNullable(dueDate);
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Optional<String> getCommentOptional() {
        return Optional.ofNullable(comment);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Optional<ATAccountingEntry> getMatchedEntryOptional() {
        return Optional.ofNullable(matchedEntry);
    }

    public void setMatchedEntry(ATAccountingEntry matchedEntry) {
        this.matchedEntry = matchedEntry;
    }

    public int getOrderingNumber() {
        return orderingNumber;
    }

    public boolean isMatched() {
        return matched;
    }

    public Optional<ATCurrencyAmount> getCurrencyAmountOptional() {
        return Optional.ofNullable(currencyAmount);
    }

    public void setCurrencyAmount(ATCurrencyAmount currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    @Override
    public String toString() {
        return "ATAccountingEntry{" +
                "bookPeriod=" + bookPeriod +
                ", date=" + date +
                ", amount=" + amount +
                ", dbkCode='" + dbkCode + '\'' +
                ", accountingEntryType=" + accountingEntryType +
                ", docNumber='" + docNumber + '\'' +
                ", docNumberType=" + docNumberType +
                ", accountingEntryDocumentType=" + accountingEntryDocumentType +
                ", account=" + account +
                ", matched=" + matched +
                ", thirdParty=" + thirdParty +
                '}';
    }

    @Override
    public int compareTo(ATAccountingEntry o) {
        return COMPARATOR.compare(this, o);
    }
}
