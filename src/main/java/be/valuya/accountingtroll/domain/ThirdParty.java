package be.valuya.accountingtroll.domain;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class ThirdParty {

    @NotNull
    private String number;
    @NotNull
    private ThirdPartyType type;
    @NotNull
    private String fullName;
    @NotNull
    private String address;
    @NotNull
    private String zipCode;
    @NotNull
    private String city;
    @NotNull
    private String countryCode;
    @NotNull
    private String vatCode;
    @NotNull
    private String vatNumber;

    @Nullable
    private String phoneNumber;
    @Nullable
    private String bankAccountNumber;
    @Nullable
    private String language;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ThirdPartyType getType() {
        return type;
    }

    public void setType(ThirdPartyType type) {
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public Optional<String> getPhoneNumberOptional() {
        return Optional.ofNullable(phoneNumber);
    }

    public void setPhoneNumber(@Nullable String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Optional<String> getBankAccountNumberOptional() {
        return Optional.ofNullable(bankAccountNumber);
    }

    public void setBankAccountNumber(@Nullable String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public Optional<String> getLanguageOptional() {
        return Optional.ofNullable(language);
    }

    public void setLanguage(@Nullable String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "ThirdParty{" +
                "number='" + number + '\'' +
                ", type=" + type +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
