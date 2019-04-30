package be.valuya.accountingtroll.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class ATAccountBalance {

    private ATAccount account;
    private ATBookPeriod period;
    private LocalDate date;
    private BigDecimal balance;
    private Optional<ATAccountingEntry> atAccountingEntryOptional = Optional.empty();

    public ATAccount getAccount() {
        return account;
    }

    public void setAccount(ATAccount account) {
        this.account = account;
    }

    public ATBookPeriod getPeriod() {
        return period;
    }

    public void setPeriod(ATBookPeriod period) {
        this.period = period;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Optional<ATAccountingEntry> getAtAccountingEntryOptional() {
        return atAccountingEntryOptional;
    }

    public void setAtAccountingEntryOptional(Optional<ATAccountingEntry> atAccountingEntryOptional) {
        this.atAccountingEntryOptional = atAccountingEntryOptional;
    }

    @Override
    public String toString() {
        return "ATAccountBalance{" +
                "account=" + account +
                ", period=" + period +
                ", date=" + date +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATAccountBalance that = (ATAccountBalance) o;
        return Objects.equals(account, that.account) &&
                Objects.equals(period, that.period) &&
                Objects.equals(date, that.date) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, period, date, balance);
    }
}
