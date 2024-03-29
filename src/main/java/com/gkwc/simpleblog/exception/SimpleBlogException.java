package com.gkwc.simpleblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class SimpleBlogException extends RuntimeException {
    public SimpleBlogException(String message) {
        super(message);
    }
}
