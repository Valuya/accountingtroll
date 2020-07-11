package be.valuya.accountingtroll;


import be.valuya.accountingtroll.domain.ATAccount;
import be.valuya.accountingtroll.domain.ATAccountBalance;
import be.valuya.accountingtroll.domain.ATAccountingEntry;
import be.valuya.accountingtroll.domain.ATBookPeriod;
import be.valuya.accountingtroll.domain.ATBookYear;
import be.valuya.accountingtroll.domain.ATDocument;
import be.valuya.accountingtroll.domain.ATThirdParty;
import be.valuya.accountingtroll.domain.ATThirdPartyBalance;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

public interface AccountingManager {

    Optional<LocalDateTime> getLastAccountModificationTime();

    Stream<ATAccount> streamAccounts();

    Stream<ATBookYear> streamBookYears();

    Stream<ATBookPeriod> streamPeriods();

    Stream<ATThirdParty> streamThirdParties();

    Stream<ATThirdPartyBalance> streamThirdPartyBalances();

    Stream<ATAccountingEntry> streamAccountingEntries();

    Stream<ATAccountBalance> streamAccountBalances();

    Stream<ATDocument> streamDocuments();

    InputStream streamDocumentContent(ATDocument document) throws Exception;

    void uploadDocument(String documentRelativePath, InputStream documentStream) throws Exception;

}
