package be.valuya.accountingtroll;

public class AccountingRuntimeException extends RuntimeException {
    public AccountingRuntimeException() {
    }

    public AccountingRuntimeException(String message) {
        super(message);
    }

    public AccountingRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountingRuntimeException(Throwable cause) {
        super(cause);
    }

    public AccountingRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
