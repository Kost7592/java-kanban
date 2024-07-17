package exception;

/**
 * Класс TaskVerifiedException представляет собой исключение, которое возникает при пересечение времени у разных задач.
 */
public class TaskVerifiedException extends RuntimeException {

    /**
     * Создаёт новое исключение TaskVerifiedException с указанным сообщением.
     *
     * @param message сообщение об ошибке
     */
    public TaskVerifiedException(String message) {
        super(message);
    }

    /**
     * Создаёт новое исключение TaskVerifiedException с указанным сообщением и причиной.
     *
     * @param message сообщение об ошибке
     * @param cause причина исключения
     */
    public TaskVerifiedException(String message, Throwable cause) {
        super(message, cause);
    }
}
