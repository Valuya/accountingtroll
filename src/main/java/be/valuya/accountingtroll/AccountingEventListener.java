package be.valuya.accountingtroll;

import be.valuya.accountingtroll.event.ArchiveFileNotFoundIgnoredEvent;
import be.valuya.accountingtroll.event.ArchiveFolderNotFoundIgnoredEvent;
import be.valuya.accountingtroll.event.AccountBalanceChangeEvent;
import be.valuya.accountingtroll.event.ThirdPartyBalanceChangeEvent;

public interface AccountingEventListener {

    void handleAccountBalanceChangeEvent(AccountBalanceChangeEvent accountBalanceChangeEvent);

    void handleThirdPartyBalanceChangeEvent(ThirdPartyBalanceChangeEvent thirdPartyBalanceChangeEvent);

    void handleArchiveFileNotFoundIgnoredEvent(ArchiveFileNotFoundIgnoredEvent ignoredEvent);

    void handleArchiveFolderNotFoundIgnoredEvent(ArchiveFolderNotFoundIgnoredEvent ignoredEvent);
}
