package org.zegai.miniopoc.exception;

public class RestException extends RuntimeException {
    private final Object[] args;

    public RestException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    public RestException(String message, Throwable cause, Object... args) {
        super(message, cause);
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }
}
