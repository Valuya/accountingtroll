package be.valuya.accountingtroll.event;

import be.valuya.accountingtroll.domain.ATAccount;
import be.valuya.accountingtroll.domain.ATAccountingEntry;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class AccountBalanceChangeEvent {

    @NotNull
    private ATAccount account;
    @NotNull
    private BigDecimal newBalance;
    @NotNull
    private LocalDate date;

    private ATAccountingEntry accountingEntry;

    public ATAccount getAccount() {
        return account;
    }

    public void setAccount(ATAccount account) {
        this.account = account;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ATAccountingEntry getAccountingEntry() {
        return accountingEntry;
    }

    public Optional<ATAccount> getAccountOptional() {
        return Optional.ofNullable(account);
    }

    public Optional<BigDecimal> getNewBalanceOptional() {
        return Optional.ofNullable(newBalance);
    }

    public Optional<LocalDate> getDateOptional() {
        return Optional.ofNullable(date);
    }

    public Optional<ATAccountingEntry> getAccountingEntryOptional() {
        return Optional.ofNullable(accountingEntry);
    }

    @Override
    public String toString() {
        return "AccountBalanceChangeEvent{" +
                "account=" + account +
                ", newBalance=" + newBalance +
                ", date=" + date +
                ", accountingEntry=" + accountingEntry +
                '}';
    }
}
