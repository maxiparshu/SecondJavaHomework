package org.example.springapp.exception;

/**
 * Исключение, указывающее на некорректный запрос со стороны клиента.
 * Выбрасывается, когда переданные данные не соответствуют ожидаемым.
 */
public class BadRequestException extends Exception {

    /**
     * Создаёт новое исключение с заданным сообщением.
     *
     * @param message Сообщение, описывающее причину ошибки.
     */
    public BadRequestException(final String message) {
        super(message);
    }
}
