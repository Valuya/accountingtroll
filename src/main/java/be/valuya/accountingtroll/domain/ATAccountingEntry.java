package be.valuya.accountingtroll.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;

@Data
public class ATAccountingEntry implements Comparable<ATAccountingEntry> {

    private final static Comparator<ATAccountingEntry> COMPARATOR = Comparator.nullsLast(
            Comparator.comparing(ATAccountingEntry::getBookPeriod)
                    .thenComparing(ATAccountingEntry::getDate)
                    .thenComparing(ATAccountingEntry::getDocNumber)
    );

    @NotNull
    private ATBookPeriod bookPeriod;
    @NotNull
    private LocalDate date;
    /**
     * Entry amount. Positive values for a 'credit' entry, negative values for a 'debit' entry, regardless
     * of the account type.
     */
    @NotNull
    private BigDecimal amount;
    /**
     * The journal code.
     */
    @NotNull
    private String dbkCode;
    /**
     * Type of journal
     */
    @NotNull
    private AccountingEntryType accountingEntryType;

    /**
     * The document number. This represent the id of the document within a journal/period.
     * Optionally, the doc number may be typed.
     */
    @NotNull
    private String docNumber;
    /**
     * An optional type for the docNumber
     */
    private AccountingEntryDocumentNumberType docNumberType;
    /**
     * An index to order entries
     */
    private int orderingNumber;
    private AccountingEntryDocumentType accountingEntryDocumentType;
    @NotNull
    private ATAccount account;
    /**
     * Each entry should be matched by another, so that the overall balance is zero.
     * Sometimes, the related entry is not (yet) present.
     */
    private boolean matched;

    /**
     * Once matched, each entry is assigned an unique number (the same for both matched entries).
     * This is known as 'lettrage' in the business vocabulary.
     */
    private String matchNumber;

    /**
     * The document from which this entry was derived.
     */
    private ATDocument document;

    /**
     * Vat details
     */
    private ATTax tax;

    /**
     * Currency details
     */
    private ATCurrencyAmount currencyAmount;
    private ATThirdParty thirdParty;
    private LocalDate documentDate;
    private LocalDate dueDate;
    private String comment;
    private ATAccountingEntry matchedEntry;

    @Override
    public int compareTo(ATAccountingEntry o) {
        return COMPARATOR.compare(this, o);
    }
}
