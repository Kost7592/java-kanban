package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Класс BaseHttpHandler реализует интерфейс HttpHandler и служит базовым классом для обработки HTTP-запросов.
 * Он наследуется от класса HttpHandler, который принимает в качестве аргумента объект типа HttpExchange.
 * Класс BaseHttpHandler предоставляет методы для обработки запросов, записи ответов и получения идентификатора задачи.
 * Этот класс может быть использован как базовый для создания более специализированных обработчиков HTTP-запросов,
 * которые будут наследоваться от него и расширять его функциональность.
 */
public class BaseHttpHandler implements HttpHandler {
    protected static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * public void handle(): основной метод обработки запроса. Вызывается при поступлении нового запроса.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
    }

    /**
     * protected void writeResponse(HttpExchange exchange, String responseText, int responseCode) throws IOException:
     * защищённый метод, используемый для записи ответа на запрос.
     * Принимает в качестве аргументов объект обмена (exchange),
     * текст ответа (responseText) и код ответа (responseCode).
     */
    protected void writeResponse(HttpExchange exchange, String responseText, int responseCode) throws IOException {
        if (responseText.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseText.getBytes(DEFAULT_CHARSET);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    /**
     * protected Optional<Integer> getTaskId(HttpExchange exchange): защищённый метод для получения идентификатора
     * текущей задачи. Возвращает значение типа Optional, содержащее идентификатор задачи или пустое значение,
     * идентификатор не найден.
     */
    protected Optional<Integer> getTaskId(HttpExchange exchange) {
        String[] pathParts = exchange.getRequestURI().getQuery().split("=");
        try {
            return Optional.of(Integer.parseInt(pathParts[1]));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }
}
