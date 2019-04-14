package be.valuya.accountingtroll.domain;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class AccountingEntry {

    @NotNull
    private BookPeriod bookPeriod;
    @NotNull
    private LocalDate date;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private BigDecimal vatRate;
    @NotNull
    private BigDecimal balance;

    @Nullable
    private Account account;
    @Nullable
    private ThirdParty thirdParty;
    @Nullable
    private LocalDate documentDate;
    @Nullable
    private LocalDate dueDate;
    @Nullable
    private String comment;

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
        return Optional.ofNullable(account);
    }

    public void setAccount(@Nullable Account account) {
        this.account = account;
    }

    public Optional<ThirdParty> getThirdPartyOptional() {
        return Optional.ofNullable(thirdParty);
    }

    public void setThirdParty(@Nullable ThirdParty thirdParty) {
        this.thirdParty = thirdParty;
    }

    public Optional<LocalDate> getDocumentDateOptional() {
        return Optional.ofNullable(documentDate);
    }

    public void setDocumentDate(@Nullable LocalDate documentDate) {
        this.documentDate = documentDate;
    }

    public Optional<LocalDate> getDueDateOptional() {
        return Optional.ofNullable(dueDate);
    }

    public void setDueDate(@Nullable LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Optional<String> getCommentOptional() {
        return Optional.ofNullable(comment);
    }

    public void setComment(@Nullable String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "AccountingEntry{" +
                "account=" + account +
                ", thirdParty=" + thirdParty +
                ", bookPeriod=" + bookPeriod +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
