package com.github.guiziin227.restspringboot.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String errorMessage, String details) {
    public ExceptionResponse(Date timestamp, String errorMessage, String details) {
        this.timestamp = timestamp;

        this.errorMessage = errorMessage;
        this.details = details;
    }
}
