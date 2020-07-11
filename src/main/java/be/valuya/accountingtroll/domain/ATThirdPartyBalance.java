package be.valuya.accountingtroll.domain;

import be.valuya.accountingtroll.AccountingConstants;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ATThirdPartyBalance {

    private final ATThirdParty thirdParty;
    private final ATBookPeriod period;
    private BigDecimal periodStartBalance = AccountingConstants.AMOUNT_ZERO;
    private BigDecimal periodEndBalance = AccountingConstants.AMOUNT_ZERO;
    private LocalDate lastOperationDate;

    public ATThirdPartyBalance(ATThirdParty thirdParty, ATBookPeriod period) {
        this.thirdParty = thirdParty;
        this.period = period;
    }

    public ATThirdParty getThirdParty() {
        return thirdParty;
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
        return "ATThirdPartyBalance{" +
                "thirdParty=" + thirdParty +
                ", period=" + period +
                ", periodStartBalance=" + periodStartBalance +
                ", periodEndBalance=" + periodEndBalance +
                ", lastOperationDate=" + lastOperationDate +
                '}';
    }
}
