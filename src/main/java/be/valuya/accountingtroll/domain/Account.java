package be.valuya.accountingtroll.domain;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class Account {

    @NotNull
    private String name;
    @NotNull
    private String code;
    @Nullable
    private String currency;

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
        return Optional.ofNullable(currency);
    }

    public void setCurrency(@Nullable String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
