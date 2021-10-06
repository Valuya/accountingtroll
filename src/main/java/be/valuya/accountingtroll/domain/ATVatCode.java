package be.valuya.accountingtroll.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class ATVatCode {

    private String id;
    private String code;
    private BigDecimal rate;

    private String header;
    private String label;
    private String description;

    private VatLiability liability;
    private VatDeducibility deducibility;

    private boolean financial;
    private boolean intraCom;

    public Optional<String> getIdOptional() {
        return Optional.ofNullable(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public Optional<String> getCodeOptional() {
        return Optional.ofNullable(code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Optional<BigDecimal> getRateOptional() {
        return Optional.ofNullable(rate);
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Optional<String> getHeaderOptional() {
        return Optional.ofNullable(header);
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Optional<String> getLabelOptional() {
        return Optional.ofNullable(label);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Optional<String> getDescriptionOptional() {
        return Optional.ofNullable(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Optional<VatLiability> getLiabilityOptional() {
        return Optional.ofNullable(liability);
    }

    public void setLiability(VatLiability liability) {
        this.liability = liability;
    }

    public Optional<VatDeducibility> getDeducibilityOptional() {
        return Optional.ofNullable(deducibility);
    }

    public void setDeducibility(VatDeducibility deducibility) {
        this.deducibility = deducibility;
    }


    public void setFinancial(boolean financial) {
        this.financial = financial;
    }

    public boolean isFinancial() {
        return financial;
    }

    public boolean isIntraCom() {
        return intraCom;
    }

    public void setIntraCom(boolean intraCom) {
        this.intraCom = intraCom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATVatCode atVatCode = (ATVatCode) o;
        return financial == atVatCode.financial && intraCom == atVatCode.intraCom && Objects.equals(id, atVatCode.id) && Objects.equals(code, atVatCode.code) && Objects.equals(rate, atVatCode.rate) && Objects.equals(header, atVatCode.header) && Objects.equals(label, atVatCode.label) && Objects.equals(description, atVatCode.description) && liability == atVatCode.liability && deducibility == atVatCode.deducibility;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, rate, header, label, description, liability, deducibility, financial, intraCom);
    }

    @Override
    public String toString() {
        return "ATVatCode{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", rate=" + rate +
                '}';
    }
}
