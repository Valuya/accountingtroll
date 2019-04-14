package be.valuya.accountingtroll.domain;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@NotNull
public class AccountingEntry {

    private BookPeriod bookPeriod;
    private LocalDate date;
    private BigDecimal amount;
    private BigDecimal vatRate;
    private BigDecimal balance;
    private Optional<Account> accountOptional = Optional.empty();
    private Optional<ThirdParty> thirdPartyOptional = Optional.empty();
    private Optional<LocalDate> documentDateOptional = Optional.empty();
    private Optional<LocalDate> dueDateOptional = Optional.empty();
    private Optional<String> commentOptional = Optional.empty();

    public BookPeriod getBookPeriod() {
        return bookPeriod;
    }

    public void setBookPeriod(BookPeriod bookPeriod) {
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

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Optional<Account> getAccountOptional() {
        return accountOptional;
    }

    public void setAccountOptional(Optional<Account> accountOptional) {
        this.accountOptional = accountOptional;
    }

    public Optional<ThirdParty> getThirdPartyOptional() {
        return thirdPartyOptional;
    }

    public void setThirdPartyOptional(Optional<ThirdParty> thirdPartyOptional) {
        this.thirdPartyOptional = thirdPartyOptional;
    }

    public Optional<LocalDate> getDocumentDateOptional() {
        return documentDateOptional;
    }

    public void setDocumentDateOptional(Optional<LocalDate> documentDateOptional) {
        this.documentDateOptional = documentDateOptional;
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
        return "AccountingEntry{" +
                "accountOptional=" + accountOptional +
                ", thirdPartyOptional=" + thirdPartyOptional +
                ", bookPeriod=" + bookPeriod +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
