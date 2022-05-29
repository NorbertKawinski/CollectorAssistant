package net.kawinski.collecting.service.exception;

public class InvalidTokenException extends CredentialsException {
    private static final long serialVersionUID = 1L;

    public InvalidTokenException() {
    }

    public InvalidTokenException(final String message) {
        super(message);
    }

    public InvalidTokenException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidTokenException(final Throwable cause) {
        super(cause);
    }

    public InvalidTokenException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
