package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import exception.TaskVerifiedException;
import modules.Subtask;
import utility.DurationTypeAdapter;
import utility.LocalDateTimeTypeAdapter;
import utility.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

/**Этот класс наследуется от класса BaseHttpHandler, который имплементирует интерфейс HttpHandler.
 * Класс SubtaskHandler предназначен для работы с подзадачами и предоставляет методы для их создания,
 * получения и удаления.*/
public class SubtaskHandler extends BaseHttpHandler {
    private final Gson gson;
    private final TaskManager taskManager;
    String response;

    public SubtaskHandler(TaskManager newTaskManager) {
        this.taskManager = newTaskManager;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter()).create();
    }

    /**
     * Основной метод обработки запроса.
     * Вызывает соответствующие методы в зависимости от типа запроса (GET, POST DELETE).
     * Так же, обрабатывает не определенный запрос.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET":
                getSubTask(exchange);
                break;
            case "POST":
                addSubTask(exchange);
                break;
            case "DELETE":
                deleteSubTask(exchange);
                break;
            default:
                writeResponse(exchange, "Такой операции не существует", 404);
        }
    }

    /**
     * Метод получения подзадачи по идентификатору.
     */
    private void getSubTask(HttpExchange exchange) throws IOException {
        if (exchange.getRequestURI().getQuery() == null) {
            response = gson.toJson(taskManager.getSubtasks());
            writeResponse(exchange, response, 200);
            return;
        }
        if (getTaskId(exchange).isEmpty()) {
            writeResponse(exchange, "Некорректный идентификатор " + getTaskId(exchange), 400);
            return;
        }
        int id = getTaskId(exchange).get();
        Subtask subtaskById = taskManager.getSubtaskById(id);
        if (isNull(subtaskById)) {
            writeResponse(exchange, "Подзадач с id " + id + " не найдено!", 404);
            return;
        }
        response = gson.toJson(subtaskById);
        writeResponse(exchange, response, 200);
    }

    /**
     * Метод добавления подзадачи.
     */
    private void addSubTask(HttpExchange exchange) throws IOException {
        try {
            InputStream json = exchange.getRequestBody();
            String jsonTask = new String(json.readAllBytes(), DEFAULT_CHARSET);
            Subtask subTask = gson.fromJson(jsonTask, Subtask.class);
            if (subTask == null) {
                writeResponse(exchange, "Подзадача не должна быть пустой!", 400);
                return;
            }
            Subtask subtaskById = taskManager.getSubtaskById(subTask.getId());
            if (subtaskById == null) {
                taskManager.createSubtask(subTask);
                writeResponse(exchange, "Подзадача добавлена!", 201);
                return;
            }
            taskManager.updateSubtask(subTask);
            writeResponse(exchange, "Подзадача обновлена!", 200);

        } catch (JsonSyntaxException e) {
            writeResponse(exchange, "Получен некорректный JSON", 400);
        } catch (TaskVerifiedException exp) {
            writeResponse(exchange, "Обнаружено пересечение по времени ", 406);
        }
    }

    /**
     * Метод удаления подзадачи.
     */
    private void deleteSubTask(HttpExchange exchange) throws IOException {
        if (exchange.getRequestURI().getQuery() == null) {
            writeResponse(exchange, "Не указан id подзадачи!", 404);
            return;
        }
        if (getTaskId(exchange).isEmpty()) {
            writeResponse(exchange, "Некорректный id подзадачи!", 404);
            return;
        }
        int id = getTaskId(exchange).get();
        if (taskManager.getSubtaskById(id) == null) {
            writeResponse(exchange, "Подзадачи с id " + id + " не найдено!", 404);
            return;
        }
        taskManager.removeSubtaskById(id);
        writeResponse(exchange, "Подзадача удалена!", 200);
    }
}

