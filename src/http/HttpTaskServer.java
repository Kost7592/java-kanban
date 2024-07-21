package http;

import com.sun.net.httpserver.HttpServer;
import utility.Managers;
import utility.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
        public HttpServer httpServer;
        private static final int PORT = 8080;
        private static final TaskManager taskManager = Managers.getDefault();

        public HttpTaskServer() throws IOException {
            httpServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);

            httpServer.createContext("/tasks", new TaskHandler(taskManager));
            httpServer.createContext("/epics", new EpicHandler(taskManager));
            httpServer.createContext("/subtasks", new SubtaskHandler(taskManager));
            httpServer.createContext("/history", new HistoryHandler(taskManager));
            httpServer.createContext("/prioritized", new PrioritizedTasksHandler(taskManager));
        }

        public static void main(String[] args) {
            try {
                HttpTaskServer server = new HttpTaskServer();
                server.start();
            } catch (IOException e) {
                System.err.println("Не удалось запустить сервер");
            }
        }

        public void start() throws IOException { //запуск сервера
            httpServer.start();
        }

        public void stop() { //остановка сервера
            try {
                httpServer.stop(0);
            } catch (Exception e) {
                System.err.println("Не удалось остановить сервер");
            }
        }
    }






