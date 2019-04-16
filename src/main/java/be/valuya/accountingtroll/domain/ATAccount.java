package be.valuya.accountingtroll.domain;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@NotNull
public class ATAccount {

    private String name;
    private String code;
    private boolean analytics;
    private boolean yearlyBalanceReset;
    private Optional<String> currencyOptional = Optional.empty();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Optional<String> getCurrencyOptional() {
        return currencyOptional;
    }

    public void setCurrencyOptional(Optional<String> currencyOptional) {
        this.currencyOptional = currencyOptional;
    }

    public boolean isAnalytics() {
        return analytics;
    }

    public void setAnalytics(boolean analytics) {
        this.analytics = analytics;
    }

    public boolean isYearlyBalanceReset() {
        return yearlyBalanceReset;
    }

    public void setYearlyBalanceReset(boolean yearlyBalanceReset) {
        this.yearlyBalanceReset = yearlyBalanceReset;
    }

    @Override
    public String toString() {
        return "ATAccount{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", analytics=" + analytics +
                ", currencyOptional=" + currencyOptional +
                '}';
    }
}
