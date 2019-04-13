package be.valuya.accountingtroll;

import java.util.Optional;

public interface TrollThirdParty {

    String getNumber();

    ThirdPartyType getType();

    String getFullName();

    String getAddress();

    String getZip();

    String getCity();

    String getVatCode();

    String getCountryCode();

    String getVatNumber();

    Optional<String> getPhoneNumber();

    Optional<String> getBankAccountNumber();

    Optional<String> getLanguage();

}
