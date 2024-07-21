import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import http.HttpTaskServer;
import modules.*;
import utility.DurationTypeAdapter;
import utility.InMemoryTaskManager;
import utility.LocalDateTimeTypeAdapter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    static InMemoryTaskManager taskManager = new InMemoryTaskManager();

    public static void main(String[] args) throws IOException {
        Task task1 = new Task("Задача 1", "Первая задача."); // заполнение задач
        taskManager.createTask(task1);
        Task task2 = new Task("Задача 2", "Вторая задача");
        taskManager.createTask(task2);
        Task task3 = new Task("Задача 3", "Третья задача");
        taskManager.createTask(task3);

        Epic epic1 = new Epic("Эпик 1", "Первый эпик");
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Эпик 2","Второй эпик");
        taskManager.createEpic(epic2);

        Subtask subtask1Epic1 = new Subtask("Подзадача 1","Первая подзадача эпика 1", epic1.getId());
        taskManager.createSubtask(subtask1Epic1);
        Subtask subtask2Epic1 = new Subtask("Подзадача 2","Вторая подзадача эпика 1", epic1.getId());
        taskManager.createSubtask(subtask2Epic1);
        Subtask subtask1Epic2 = new Subtask("Подзадача 1","Первая подзадача эпика 2", epic2.getId());
        taskManager.createSubtask(subtask1Epic2);
        Subtask subtask2Epic2 = new Subtask("Подзадача 2","Вторая подзадача эпика 2", epic2.getId());
        taskManager.createSubtask(subtask2Epic2);
        System.out.println("Заполнение задач");
        taskManager.getTaskById(task1.getId());
        taskManager.getSubtaskById(subtask1Epic1.getId()); //проверка заполнения истории
        printAllTasks();

        task1.setStatus(TaskStatus.DONE); // обновление статусов
        taskManager.updateTask(task1);
        task2.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task2);
        subtask1Epic1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(subtask1Epic1);
        subtask1Epic2.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(subtask1Epic2);
        subtask2Epic1.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(subtask2Epic1);
        subtask2Epic2.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(subtask2Epic2);
        System.out.println("Обновление статусов");
        printAllTasks();

        taskManager.removeTaskById(task1.getId()); // удаление по id
        taskManager.removeSubtaskById(subtask2Epic1.getId());
        taskManager.removeEpicById(epic2.getId());
        System.out.println("Удаление задач");
        printAllTasks();

        taskManager.removeAllTasks();
        taskManager.removeAllSubtasks();
        taskManager.removeAllEpics();
        System.out.println("Очищение всех списков");
        printAllTasks();

    }

    private static void printAllTasks() {
        System.out.println("Задачи:");
        for (Task task : taskManager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : taskManager.getEpics()) {
            System.out.println(epic);

            for (Task task : taskManager.getEpicSubtasks(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : taskManager.getSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
    }
}
