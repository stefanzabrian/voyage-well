package com.dev.voyagewell.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ErrorDetails {
    private final Date timpestamp;
    private final String message;
    private final String details;
}
