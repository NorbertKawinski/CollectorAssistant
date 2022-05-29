package net.kawinski.collecting.service.exception;

public class CredentialsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CredentialsException() {
    }

    public CredentialsException(final String message) {
        super(message);
    }

    public CredentialsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CredentialsException(final Throwable cause) {
        super(cause);
    }

    public CredentialsException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
