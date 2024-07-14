package exception;

/**
 * Класс LoadFromFileException представляет собой исключение, которое возникает при попытке загрузить данные из файла.
 */
public class LoadFromFileException extends RuntimeException {

    /**
     * Создаёт новое исключение LoadFromFileException с указанным сообщением.
     *
     * @param message сообщение об ошибке
     */
    public LoadFromFileException(String message) {
        super(message);
    }

    /**
     * Создаёт новое исключение LoadFromFileException с указанным сообщением и причиной.
     *
     * @param message сообщение об ошибке
     * @param cause причина исключения
     */
    public LoadFromFileException(String message, Throwable cause) {
        super(message, cause);
    }
}

