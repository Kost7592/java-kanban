package utility;

import exception.ManagerSaveException;
import modules.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileBackedTaskManagerTest {
    File tempFile;
    FileBackedTaskManager fileManager;

    @BeforeEach
    public void initialization() {
        try {
            tempFile = File.createTempFile("test", "csv");
        } catch(IOException exception) {
            throw new RuntimeException(exception);
        }
        fileManager = new FileBackedTaskManager(tempFile.toPath());
    }

    @Test
    public void checkThatToSaveAndDownloadAnEmptyFile() { // проверка сохранения и загрузки не заполненного файла
        assertNotNull(tempFile);
        fileManager.saveTask();
        fileManager.loadFromFile(tempFile);
    }

    @Test
    public void checkingForSavingTasks() { //проверка сохранения задач в файл
        createTasksManually();
        assertEquals(2, fileManager.getTasks().size());
        assertEquals(1,fileManager.getEpics().size());
        assertEquals(2, fileManager.getSubtasks().size());
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile.toString()))) {
            reader.readLine();
            while (true) {
                String line = reader.readLine();
                if (isNull(line) || line.isEmpty()) {
                    break;
                }
                count++;
            }
        } catch (FileNotFoundException exception) {
            throw new ManagerSaveException("Файл не найден");
        } catch (IOException exception) {
            throw new ManagerSaveException("Ошибка чтения файла");
        }
        assertEquals(5, count);
    }

    @Test
    public void checkingForLoadingTasks() { //проверка загрузки задач из файла
        createTasksManually();
        File copyFile = copyFile(tempFile);
        fileManager.removeAllTasks();
        fileManager.removeAllSubtasks();
        fileManager.removeAllEpics();
        assertEquals(0, fileManager.getTasks().size());
        assertEquals(0, fileManager.getEpics().size());
        assertEquals(0, fileManager.getSubtasks().size());
        fileManager.loadFromFile(copyFile);
        assertEquals(2, fileManager.getTasks().size());
        assertEquals(1,fileManager.getEpics().size());
        assertEquals(2, fileManager.getSubtasks().size());
    }

    private void createTasksManually() { //создание задач "вручную"
        Task task1 = new Task(0, TaskType.TASK, "Task1", TaskStatus.NEW,"Task1 description");
        Task task2 = new Task(1, TaskType.TASK, "Task2", TaskStatus.NEW,"Task2 description");
        Epic epic1 = new Epic(2,TaskType.EPIC, "Epic1",TaskStatus.NEW,"Epic1 description",new ArrayList<>());
        Subtask subtask1 = new Subtask(3, TaskType.SUBTASK,"Subtask1",TaskStatus.NEW,"Subtask1 description",2);
        Subtask subtask2 = new Subtask(4,TaskType.SUBTASK,"Subtask2",TaskStatus.NEW,"Subtask2 description",2);
        fileManager.createTask(task1);
        fileManager.createTask(task2);
        fileManager.createEpic(epic1);
        fileManager.createSubtask(subtask1);
        fileManager.createSubtask(subtask2);
    }

    private File copyFile(File tempFile) { //копирование файла
        File copyFile;
        try {
            copyFile = File.createTempFile("file2","csv");
            Files.copy(tempFile.toPath(), copyFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return copyFile;
    }
}
