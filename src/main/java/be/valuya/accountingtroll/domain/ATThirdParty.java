package be.valuya.accountingtroll.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Comparator;

@Data
public class ATThirdParty implements Comparable<ATThirdParty> {

    private final static Comparator<ATThirdParty> COMPARATOR = Comparator.nullsLast(Comparator.comparing(ATThirdParty::getId));

    @NotNull
    private String id;
    private ATThirdPartyType type;
    private String fullName;
    private String address;
    private String zipCode;
    private String city;
    private String countryCode;
    private String vatNumber;

    private String phoneNumber;
    private String bankAccountNumber;
    private String language;
    private VatLiability vatLiability;

    private String defaultGlAccount;
    private ATVatCode vatCode;
    private String central;

    @Override
    public int compareTo(ATThirdParty atThirdParty) {
        return COMPARATOR.compare(this, atThirdParty);
    }


}
