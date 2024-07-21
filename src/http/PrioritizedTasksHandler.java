package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import utility.DurationTypeAdapter;
import utility.LocalDateTimeTypeAdapter;
import utility.TaskManager;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class PrioritizedTasksHandler extends BaseHttpHandler {
    TaskManager taskManager;
    private final Gson gson;
    String response;

    public PrioritizedTasksHandler(TaskManager newTaskManager) {
        this.taskManager = newTaskManager;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class,new DurationTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException { //обработчик входщяих запросов
        String method = exchange.getRequestMethod();

        if (method.equals("GET")) {
            getPrioritizedTasks(exchange);
        } else {
            writeResponse(exchange, "Такой операции не существует", 404);
        }
    }

    private void getPrioritizedTasks(HttpExchange exchange) throws IOException { //получение отсортированного списка
        response = gson.toJson(taskManager.getPrioritizedTasks());
        writeResponse(exchange, response, 200);
    }

}

