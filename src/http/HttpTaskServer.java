package http;

import com.sun.net.httpserver.HttpServer;
import utility.Managers;
import utility.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Класс представляет собой сервер задач, который обрабатывает запросы от клиентов.
 */
public class HttpTaskServer {
    private static final int PORT = 8080;
    private static final TaskManager TASK_MANAGER = Managers.getDefault();
    private final HttpServer httpServer;

    public HttpTaskServer() throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);

        httpServer.createContext("/tasks", new TaskHandler(TASK_MANAGER));
        httpServer.createContext("/epics", new EpicHandler(TASK_MANAGER));
        httpServer.createContext("/subtasks", new SubtaskHandler(TASK_MANAGER));
        httpServer.createContext("/history", new HistoryHandler(TASK_MANAGER));
        httpServer.createContext("/prioritized", new PrioritizedTasksHandler(TASK_MANAGER));
    }

    public static void main(String[] args) {
        try {
            HttpTaskServer server = new HttpTaskServer();
            server.start();
        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер");
        }
    }

    /**
     * Запускает сервер на указанном порту.
     * @throws IOException если при запуске сервера возникли проблемы с подключением.
     */
    public void start() throws IOException {
        httpServer.start();
    }

    /**
     * Останавливает сервер.
     */
    public void stop() {
        try {
            httpServer.stop(0);
        } catch (Exception e) {
            System.err.println("Не удалось остановить сервер");
        }
    }
}






