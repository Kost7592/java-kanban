package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import exception.TaskVerifiedException;
import modules.Task;
import utility.DurationTypeAdapter;
import utility.LocalDateTimeTypeAdapter;
import utility.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

public class TaskHandler extends BaseHttpHandler {
    TaskManager taskManager;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final Gson gson;
    String response;

    public TaskHandler(TaskManager newTaskManager) {
        this.taskManager = newTaskManager;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class,new DurationTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException { //обработчик входящих запросов
        String method = exchange.getRequestMethod();
        String path = String.valueOf(exchange.getRequestURI());

        System.out.println("Обрабатывается запрос " + path + " с методом " + method);
        switch (method) {
            case "GET":
                getTask(exchange);
                break;
            case "POST":
                addTask(exchange);
                break;
            case "DELETE":
                deleteTask(exchange);
                break;
            default:
                writeResponse(exchange, "Такой операции не существует", 404);
        }
    }

    private void getTask(HttpExchange exchange) throws IOException { //получение задачи
        if (exchange.getRequestURI().getQuery() == null) {
            response = gson.toJson(taskManager.getTasks());
            writeResponse(exchange, response, 200);
            return;
        }

        if (getTaskId(exchange).isEmpty()) {
            writeResponse(exchange, "Некорректный идентификатор " + getTaskId(exchange), 400);
            return;
        }

        int id = getTaskId(exchange).get();
        Task taskById = taskManager.getTaskById(id);
        if (isNull(taskById)) {
            writeResponse(exchange, "Задач с id " + id + " не найдено!", 404);
            return;
        }
        response = gson.toJson(taskById);
        writeResponse(exchange, response, 200);
    }

    private void addTask(HttpExchange exchange) throws IOException { //добавление задачи
        try {
            InputStream json = exchange.getRequestBody();
            String jsonTask = new String(json.readAllBytes(), DEFAULT_CHARSET);
            Task task = gson.fromJson(jsonTask, Task.class);
            if (task == null) {
                writeResponse(exchange, "Задача не должна быть пустой!", 400);
                return;
            }
            Task taskById = taskManager.getTaskById(task.getId());
            if (taskById == null) {
                taskManager.createTask(task);
                writeResponse(exchange, "Задача добавлена!", 201);
                return;
            }
            taskManager.updateTask(task);
            writeResponse(exchange, "Задача обновлена", 200);

        } catch (JsonSyntaxException e) {
            writeResponse(exchange, "Получен некорректный JSON", 400);
        } catch (TaskVerifiedException exp) {
            writeResponse(exchange, "Обнаружено пересечение по времени!", 406);
        }
    }

    private void deleteTask(HttpExchange exchange) throws IOException { //удаление задачи
        String query = exchange.getRequestURI().getQuery();

        if (query == null) {
            writeResponse(exchange, "Не указан id задачи ", 404);
            return;
        }
        if (getTaskId(exchange).isEmpty()) {
            writeResponse(exchange, "Не указан id задачи ", 404);
            return;
        }
        int id = getTaskId(exchange).get();
        if (taskManager.getTaskById(id) == null) {
            writeResponse(exchange, "Задач с таким id " + id + " не найдено!", 404);
            return;
        }
        taskManager.removeTaskById(id);
        writeResponse(exchange, "Задача удалена!", 200);
    }
}
