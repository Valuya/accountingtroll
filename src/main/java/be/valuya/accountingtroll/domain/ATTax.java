package be.valuya.accountingtroll.domain;

import java.math.BigDecimal;

public class ATTax {

    private ATVatCode vatCode;
    private BigDecimal vatBase;
    private BigDecimal vatAmount;
    private String vatImputation;

    public ATVatCode getVatCode() {
        return vatCode;
    }

    public void setVatCode(ATVatCode vatCode) {
        this.vatCode = vatCode;
    }

    public BigDecimal getVatBase() {
        return vatBase;
    }

    public void setVatBase(BigDecimal vatBase) {
        this.vatBase = vatBase;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public String getVatImputation() {
        return vatImputation;
    }

    public void setVatImputation(String vatImputation) {
        this.vatImputation = vatImputation;
    }
}
