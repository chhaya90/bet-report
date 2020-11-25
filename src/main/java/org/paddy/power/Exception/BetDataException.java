package org.paddy.power.Exception;
/**
 * Exception produced by <code>bet-report</code>.
 */
public class BetDataException extends Exception {
    private final BetDataExceptionCode errorCode;

    public BetDataException(final BetDataExceptionCode errorCode, final Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode.getErrorCode();
    }

    public String getErrorMessage() {
        return errorCode.getErrorMessage();
    }
}
