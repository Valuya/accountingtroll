package be.valuya.accountingtroll.domain;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;

@NotNull
public class ATAccount {

    private String name;
    private String code;
    private boolean analytics;
    private boolean yearlyBalanceReset;
    private boolean title;
    private ATAccountImputationType imputationType;
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

    public ATAccountImputationType getImputationType() {
        return imputationType;
    }

    public void setImputationType(ATAccountImputationType imputationType) {
        this.imputationType = imputationType;
    }

    public boolean isTitle() {
        return title;
    }

    public void setTitle(boolean title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ATAccount{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", analytics=" + analytics +
                ", yearlyBalanceReset=" + yearlyBalanceReset +
                ", currencyOptional=" + currencyOptional +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATAccount atAccount = (ATAccount) o;
        return analytics == atAccount.analytics &&
                yearlyBalanceReset == atAccount.yearlyBalanceReset &&
                title == atAccount.title &&
                Objects.equals(code, atAccount.code) &&
                imputationType == atAccount.imputationType &&
                Objects.equals(currencyOptional, atAccount.currencyOptional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, analytics, yearlyBalanceReset, title, imputationType, currencyOptional);
    }
}
