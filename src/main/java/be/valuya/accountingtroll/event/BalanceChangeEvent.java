package be.valuya.accountingtroll.event;

import be.valuya.accountingtroll.domain.ATAccount;
import be.valuya.accountingtroll.domain.ATAccountingEntry;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class BalanceChangeEvent {

    private ATAccount account;
    private BigDecimal newBalance;
    private LocalDate date;

    private Optional<ATAccountingEntry> accountingEntryOptional = Optional.empty(); //

    public ATAccount getAccount() {
        return account;
    }

    public void setAccount(ATAccount account) {
        this.account = account;
    }

    public Optional<ATAccountingEntry> getAccountingEntryOptional() {
        return accountingEntryOptional;
    }

    public void setAccountingEntryOptional(Optional<ATAccountingEntry> accountingEntryOptional) {
        this.accountingEntryOptional = accountingEntryOptional;
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

    @Override
    public String toString() {
        return "BalanceChangeEvent{" +
                "account=" + account +
                ", newBalance=" + newBalance +
                ", date=" + date +
                ", accountingEntryOptional=" + accountingEntryOptional +
                '}';
    }
}
