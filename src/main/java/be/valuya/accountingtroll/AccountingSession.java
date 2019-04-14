package be.valuya.accountingtroll;

import be.valuya.accountingtroll.cache.AccountingCache;

public interface AccountingSession {

    //TODO: type it (what is it actually meant to be?)
    String getSessionType();

    AccountingCache getCache();
}
