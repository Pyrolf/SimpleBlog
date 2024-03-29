package com.gkwc.simpleblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Email Taken")
public class EmailTakenException extends RuntimeException {
    public EmailTakenException(String message) {
        super(message);
    }
}
