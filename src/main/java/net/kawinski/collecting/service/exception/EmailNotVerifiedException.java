package net.kawinski.collecting.service.exception;

public class EmailNotVerifiedException extends CredentialsException {
    private static final long serialVersionUID = 1L;

    public EmailNotVerifiedException() {
    }

    public EmailNotVerifiedException(final String message) {
        super(message);
    }

    public EmailNotVerifiedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EmailNotVerifiedException(final Throwable cause) {
        super(cause);
    }

    public EmailNotVerifiedException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
