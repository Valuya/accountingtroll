package be.valuya.accountingtroll.event;

import be.valuya.accountingtroll.AccountingEventListener;

public class AccountingEventHandler implements AccountingEventListener {

    @Override
    public void handleBalanceChangeEvent(BalanceChangeEvent balanceChangeEvent) {
    }

    @Override
    public void handleArchiveFileNotFoundIgnoredEvent(ArchiveFileNotFoundIgnoredEvent ignoredEvent) {
    }

    @Override
    public void handleArchiveFolderNotFoundIgnoredEvent(ArchiveFolderNotFoundIgnoredEvent ignoredEvent) {
    }
}