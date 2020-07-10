package be.valuya.accountingtroll.domain;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

@NotNull
public class ATAccount implements Comparable<ATAccount> {

    private final static Comparator<ATAccount> COMPARATOR = Comparator.nullsLast(
            Comparator.comparing(a -> a.getCodeOptional().orElse(null))
    );

    @NotNull
    private String name;
    @NotNull
    private String code;
    private boolean analytics;
    private boolean yearlyBalanceReset;
    private boolean title;
    @NotNull
    private ATAccountImputationType imputationType;
    private String currency;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public ATAccountImputationType getImputationType() {
        return imputationType;
    }

    public Optional<String> getNameOptional() {
        return Optional.ofNullable(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getCodeOptional() {
        return Optional.ofNullable(code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAnalytics(boolean analytics) {
        this.analytics = analytics;
    }

    public void setYearlyBalanceReset(boolean yearlyBalanceReset) {
        this.yearlyBalanceReset = yearlyBalanceReset;
    }

    public void setTitle(boolean title) {
        this.title = title;
    }

    public Optional<ATAccountImputationType> getImputationTypeOptional() {
        return Optional.ofNullable(imputationType);
    }

    public void setImputationType(ATAccountImputationType imputationType) {
        this.imputationType = imputationType;
    }

    public Optional<String> getCurrencyOptional() {
        return Optional.ofNullable(currency);
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isAnalytics() {
        return analytics;
    }

    public boolean isYearlyBalanceReset() {
        return yearlyBalanceReset;
    }

    public boolean isTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATAccount atAccount = (ATAccount) o;
        return Objects.equals(code, atAccount.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public int compareTo(ATAccount o) {
        return COMPARATOR.compare(this, o);
    }

    @Override
    public String toString() {
        return "ATAccount{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", yearlyBalanceReset=" + yearlyBalanceReset +
                ", imputationType=" + imputationType +
                '}';
    }
}
