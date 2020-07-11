package be.valuya.accountingtroll.event;

import be.valuya.accountingtroll.domain.ATAccountingEntry;
import be.valuya.accountingtroll.domain.ATThirdParty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class ThirdPartyBalanceChangeEvent {

    private ATThirdParty thirdParty;
    private BigDecimal newBalance;
    private LocalDate date;

    private ATAccountingEntry accountingEntry;

    public ATThirdParty getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(ATThirdParty thirdParty) {
        this.thirdParty = thirdParty;
    }

    public Optional<ATAccountingEntry> getAccountingEntryOptional() {
        return Optional.ofNullable(accountingEntry);
    }

    public void setAccountingEntryOptional(ATAccountingEntry accountingEntry) {
        this.accountingEntry = accountingEntry;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ThirdPartyBalanceChangeEvent{" +
                "thirdParty=" + thirdParty +
                ", newBalance=" + newBalance +
                ", date=" + date +
                ", accountingEntry=" + accountingEntry +
                '}';
    }
}
