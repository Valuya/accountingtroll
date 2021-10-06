package be.valuya.accountingtroll.domain;

import java.math.BigDecimal;
import java.util.Optional;

public class ATCurrencyAmount {

    private BigDecimal amount;
    private String currencyCode;
    private BigDecimal euroBase;
    private BigDecimal rate;

    public Optional<BigDecimal> getAmountOptional() {
        return Optional.ofNullable(amount);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Optional<String> getCurrencyCodeOptional() {
        return Optional.ofNullable(currencyCode);
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Optional<BigDecimal> getEuroBaseOptional() {
        return Optional.ofNullable(euroBase);
    }

    public void setEuroBase(BigDecimal euroBase) {
        this.euroBase = euroBase;
    }

    public Optional<BigDecimal> getRateOptional() {
        return Optional.ofNullable(rate);
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
