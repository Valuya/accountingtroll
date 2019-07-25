package be.valuya.accountingtroll.domain;

import be.valuya.accountingtroll.AccountingConstants;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ATAccountBalance {

    private final ATAccount account;
    private final ATBookPeriod period;
    private BigDecimal periodStartBalance = AccountingConstants.AMOUNT_ZERO;
    private BigDecimal periodEndBalance = AccountingConstants.AMOUNT_ZERO;
    private LocalDate lastOperationDate;

    public ATAccountBalance(ATAccount account, ATBookPeriod period) {
        this.account = account;
        this.period = period;
    }

    public ATAccount getAccount() {
        return account;
    }

    public ATBookPeriod getPeriod() {
        return period;
    }

    public BigDecimal getPeriodStartBalance() {
        return periodStartBalance;
    }

    public void setPeriodStartBalance(BigDecimal periodStartBalance) {
        this.periodStartBalance = periodStartBalance;
    }

    public BigDecimal getPeriodEndBalance() {
        return periodEndBalance;
    }

    public void setPeriodEndBalance(BigDecimal periodEndBalance) {
        this.periodEndBalance = periodEndBalance;
    }

    public LocalDate getLastOperationDate() {
        return lastOperationDate;
    }

    public void setLastOperationDate(LocalDate lastOperationDate) {
        this.lastOperationDate = lastOperationDate;
    }

    @Override
    public String toString() {
        return "ATAccountBalance{" +
                "account=" + account +
                ", period=" + period +
                ", periodStartBalance=" + periodStartBalance +
                ", periodEndBalance=" + periodEndBalance +
                '}';
    }
}
