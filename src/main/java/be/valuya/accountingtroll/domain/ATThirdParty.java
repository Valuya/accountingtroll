package be.valuya.accountingtroll.domain;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class ATThirdParty {

    @NotNull
    private String id;
    @Nullable
    private ATThirdPartyType type;
    @Nullable
    private String fullName;
    @Nullable
    private String address;
    @Nullable
    private String zipCode;
    @Nullable
    private String city;
    @Nullable
    private String countryCode;
    @Nullable
    private String vatNumber;

    @Nullable
    private String phoneNumber;
    @Nullable
    private String bankAccountNumber;
    @Nullable
    private String language;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Optional<ATThirdPartyType> getTypeOptional() {
        return Optional.ofNullable(type);
    }

    public void setType(@Nullable ATThirdPartyType type) {
        this.type = type;
    }

    public Optional<String> getFullNameOptional() {
        return Optional.ofNullable(fullName);
    }

    public void setFullName(@Nullable String fullName) {
        this.fullName = fullName;
    }

    public Optional<String> getAddressOptional() {
        return Optional.ofNullable(address);
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    public Optional<String> getZipCodeOptional() {
        return Optional.ofNullable(zipCode);
    }

    public void setZipCode(@Nullable String zipCode) {
        this.zipCode = zipCode;
    }

    public Optional<String> getCityOptional() {
        return Optional.ofNullable(city);
    }

    public void setCity(@Nullable String city) {
        this.city = city;
    }

    public Optional<String> getCountryCodeOptional() {
        return Optional.ofNullable(countryCode);
    }

    public void setCountryCode(@Nullable String countryCode) {
        this.countryCode = countryCode;
    }

    public Optional<String> getVatNumberOptional() {
        return Optional.ofNullable(vatNumber);
    }

    public void setVatNumber(@Nullable String vatNumber) {
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
        return "ATThirdParty{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", fullName='" + fullName + '\'' +
                '}';
    }

}
