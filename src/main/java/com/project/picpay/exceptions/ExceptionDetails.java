package com.project.picpay.exceptions;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected String title;
    protected String details;
    protected int status;
    protected LocalDateTime timestamp;
}
