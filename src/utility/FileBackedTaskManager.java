package utility;

import exception.LoadFromFileException;
import exception.ManagerSaveException;
import modules.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private Path file;

    public FileBackedTaskManager (Path file) {
        super();
        this.file = file;
    }

    public FileBackedTaskManager () {
        super();
    }

    public void saveTask(Task task) { //сохранение задачи в файл
        try (FileWriter writer = new FileWriter(file.toFile(),false)) {
            writer.write("id,type,name,status,description,epic\n");
            for (Integer key : tasks.keySet()) {
                writer.write(tasks.get(key).toString() + "\n");
            }
            for (Integer key : epics.keySet()) {
                writer.write(epics.get(key).toString() + "\n");
            }
            for (Integer key : subtasks.keySet()) {
                writer.write(subtasks.get(key).toString() + "\n");
            }
        } catch (IOException exception) {
            throw new ManagerSaveException(exception.getMessage());
        }
    }

    public static Task getFromString(String taskString) { //получение задачи из строки текста
        String[] taskLines = taskString.split(",");
        Integer id = Integer.parseInt(taskLines[0]);
        TaskType type = TaskType.valueOf(taskLines[1]);
        String name = taskLines[2];
        TaskStatus status = TaskStatus.valueOf(taskLines[3]);
        String description = taskLines[4];
        if (type.equals(TaskType.EPIC)) {
            return new Epic(id, name, status, description, new ArrayList<>());
        } else if (type.equals(TaskType.SUBTASK)) {
            int epic = Integer.parseInt(taskLines[5]);
            return new Subtask(id, name, status,description, epic);
        } else {
            return new Task(id, type, name,  status, description);
        }

    }

}
