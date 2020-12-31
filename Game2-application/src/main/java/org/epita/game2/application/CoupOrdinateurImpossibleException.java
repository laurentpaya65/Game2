package org.epita.game2.application;

public class CoupOrdinateurImpossibleException extends RuntimeException {

    public CoupOrdinateurImpossibleException() {
    }

    public CoupOrdinateurImpossibleException(String message) {
        super(message);
    }

    public CoupOrdinateurImpossibleException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoupOrdinateurImpossibleException(Throwable cause) {
        super(cause);
    }

    public CoupOrdinateurImpossibleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
