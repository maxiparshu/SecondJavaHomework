package org.example.springapp.exception;

/**.
 * Выбрасывается, когда переданные данные не найдены.
 */
public class ResourceNotFoundException extends Exception{
    /**
     * Создаёт новое исключение с заданным сообщением.
     *
     * @param message Сообщение, описывающее причину ошибки.
     */
    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
