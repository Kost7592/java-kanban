package exception;

/**
 * Класс ManagerSaveException представляет собой исключение, которое возникает при попытке сохранить данные.
 */
public class ManagerSaveException extends RuntimeException {

    /**
     * Создаёт новое исключение ManagerSaveException с указанным сообщением.
     *
     * @param message сообщение об ошибке
     */
    public ManagerSaveException(String message) {
        super(message);
    }

    /**
     * Создаёт новое исключение ManagerSaveException с указанным сообщением и причиной.
     *
     * @param message сообщение об ошибке
     * @param cause   причина исключения
     */
    public ManagerSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
