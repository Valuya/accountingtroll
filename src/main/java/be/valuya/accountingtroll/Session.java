package be.valuya.accountingtroll;

import be.valuya.accountingtroll.cache.AccountingCache;

public interface Session {

    String getSessionType();

    AccountingCache getCache();
}
