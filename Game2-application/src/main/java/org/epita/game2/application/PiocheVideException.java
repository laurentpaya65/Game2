package org.epita.game2.application;

public class PiocheVideException extends RuntimeException {

    public PiocheVideException() {
    }

    public PiocheVideException(String message) {
        super(message);
    }

    public PiocheVideException(String message, Throwable cause) {
        super(message, cause);
    }

    public PiocheVideException(Throwable cause) {
        super(cause);
    }

    public PiocheVideException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
