package be.valuya.accountingtroll.event;

import be.valuya.accountingtroll.domain.Account;
import be.valuya.accountingtroll.domain.AccountingEntry;

import java.math.BigDecimal;
import java.util.Optional;

public class BalanceChangeEvent {

    private Account account;
    private Optional<AccountingEntry> accountingEntryOptional = Optional.empty(); //
    private BigDecimal newBalance;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Optional<AccountingEntry> getAccountingEntryOptional() {
        return accountingEntryOptional;
    }

    public void setAccountingEntryOptional(Optional<AccountingEntry> accountingEntryOptional) {
        this.accountingEntryOptional = accountingEntryOptional;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    @Override
    public String toString() {
        return "BalanceChangeEvent{" +
                "account=" + account +
                ", accountingEntryOptional=" + accountingEntryOptional +
                ", newBalance=" + newBalance +
                '}';
    }
}
