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

/**Этот класс является наследником класса BaseHttpHandler и предназначен для обработки запросов,
 * связанных с приоритетными задачами.*/
public class PrioritizedTasksHandler extends BaseHttpHandler {
    private final Gson gson;
    private final TaskManager taskManager;
    String response;

    public PrioritizedTasksHandler(TaskManager newTaskManager) {
        this.taskManager = newTaskManager;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
    }

    /**Основной метод класса, который обрабатывает входящие запросы. Этот метод может выполнять различные действия
     * в зависимости от типа запроса, например, получать список приоритетных задач или обрабатывать другие запросы,
     * связанные с приоритетными задачами;*/
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equals("GET")) {
            getPrioritizedTasks(exchange);
        } else {
            writeResponse(exchange, "Такой операции не существует", 404);
        }
    }

    /**Метод для получения списка приоритетных задач.*/
    private void getPrioritizedTasks(HttpExchange exchange) throws IOException {
        response = gson.toJson(taskManager.getPrioritizedTasks());
        writeResponse(exchange, response, 200);
    }

}

