package org.zegai.miniopoc.exception;

import lombok.Getter;

@Getter
public class UnprocessableRequestException extends RestException {

    public UnprocessableRequestException(String message, Object... args) {
        super(message, args);
    }

    public UnprocessableRequestException(String message, Throwable cause, Object[] args) {
        super(message, cause, args);
    }
}
