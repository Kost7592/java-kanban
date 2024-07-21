package http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import utility.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler {
    TaskManager taskManager;
    private final Gson gson;
    String response;

    public HistoryHandler(TaskManager newTaskManager) {
        this.taskManager = newTaskManager;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException { //обработчик входящих запросов
        String method = exchange.getRequestMethod();
        if (method.equals("GET")) {
            getHistoryList(exchange);
        } else {
            writeResponse(exchange, "Такой операции не существует", 404);
        }
    }

    private void getHistoryList(HttpExchange exchange) throws IOException { //получение истории просмотров
        if (taskManager.getHistory().isEmpty()) {
            writeResponse(exchange, "История пуста!", 200);
        } else {
            response = gson.toJson(taskManager.getHistory());
            writeResponse(exchange, response, 200);
        }
    }
}
