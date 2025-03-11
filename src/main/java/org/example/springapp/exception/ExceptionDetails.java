package org.example.springapp.exception;

import java.util.Date;

public record ExceptionDetails(Date date, String exceptionMessage) {
}
