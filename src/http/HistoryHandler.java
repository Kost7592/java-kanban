package http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import utility.TaskManager;

import java.io.IOException;

/**Этот класс является наследником класса BaseHttpHandler и предназначен для обработки запросов, связанных с историей.*/
public class HistoryHandler extends BaseHttpHandler {
    private final Gson gson;
    private final TaskManager taskManager;
    String response;

    public HistoryHandler(TaskManager newTaskManager) {
        this.taskManager = newTaskManager;
        this.gson = new Gson();
    }

    /**Основной метод класса, который обрабатывает входящие запросы.
     * Этот метод может выполнять различные действия в зависимости от типа запроса, например, получать список истории
     * или обрабатывать другие запросы, связанные с историей.*/
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (method.equals("GET")) {
            getHistoryList(exchange);
        } else {
            writeResponse(exchange, "Такой операции не существует", 404);
        }
    }

    /** Метод, который получает список истории. Он может использоваться для выполнения различных операций с данными
     * истории, таких как их отображение, редактирование или удаление.*/
    private void getHistoryList(HttpExchange exchange) throws IOException {
        if (taskManager.getHistory().isEmpty()) {
            writeResponse(exchange, "История пуста!", 200);
        } else {
            response = gson.toJson(taskManager.getHistory());
            writeResponse(exchange, response, 200);
        }
    }
}
