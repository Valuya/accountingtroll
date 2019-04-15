package be.valuya.accountingtroll;

import be.valuya.accountingtroll.event.ArchiveNotFoundIgnoredEvent;
import be.valuya.accountingtroll.event.BalanceChangeEvent;

public interface AccountingEventListener {

    void handleBalanceChangeEvent(BalanceChangeEvent balanceChangeEvent);

    void handleArchiveNotFoundIgnoredEvent(ArchiveNotFoundIgnoredEvent balanceChangeEvent);
}
