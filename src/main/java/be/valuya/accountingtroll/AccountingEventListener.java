package be.valuya.accountingtroll;

import be.valuya.accountingtroll.event.ArchiveFileNotFoundIgnoredEvent;
import be.valuya.accountingtroll.event.ArchiveFolderNotFoundIgnoredEvent;
import be.valuya.accountingtroll.event.AccountBalanceChangeEvent;

public interface AccountingEventListener {

    void handleBalanceChangeEvent(AccountBalanceChangeEvent accountBalanceChangeEvent);

    void handleArchiveFileNotFoundIgnoredEvent(ArchiveFileNotFoundIgnoredEvent ignoredEvent);

    void handleArchiveFolderNotFoundIgnoredEvent(ArchiveFolderNotFoundIgnoredEvent ignoredEvent);
}
