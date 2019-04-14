package be.valuya.accountingtroll;

public interface AccountingEventListener {

    void handleBalanceChangeEven(BalanceChangeEvent balanceChangeEvent);
}
