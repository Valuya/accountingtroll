package be.valuya.accountingtroll;

import be.valuya.accountingtroll.event.ArchiveFileNotFoundIgnoredEvent;
import be.valuya.accountingtroll.event.ArchiveFolderNotFoundIgnoredEvent;
import be.valuya.accountingtroll.event.BalanceChangeEvent;

public interface AccountingEventListener {

    void handleBalanceChangeEvent(BalanceChangeEvent balanceChangeEvent);

    void handleArchiveFileNotFoundIgnoredEvent(ArchiveFileNotFoundIgnoredEvent ignoredEvent);

    void handleArchiveFolderNotFoundIgnoredEvent(ArchiveFolderNotFoundIgnoredEvent ignoredEvent);
}
