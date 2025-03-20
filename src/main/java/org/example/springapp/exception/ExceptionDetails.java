package org.example.springapp.exception;

import java.util.Date;

/**
 * Класс, представляющий детали исключения.
 * Используется для формирования структурированного ответа в случае возникновения ошибки.
 *
 * @param date            Дата и время возникновения исключения.
 * @param exceptionMessage Сообщение об ошибке.
 */
public record ExceptionDetails(Date date, String exceptionMessage) {
}
