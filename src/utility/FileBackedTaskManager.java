package utility;

import exception.LoadFromFileException;
import exception.ManagerSaveException;
import modules.*;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.util.Objects.isNull;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private Path file;

    private FileBackedTaskManager(Path file) {
        super();
        this.file = file;
    }

    public FileBackedTaskManager() {
        super();
    }

    @Override
    public void removeTaskById(Integer id) {
        super.removeTaskById(id);
        saveTask();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        saveTask();
    }

    @Override
    public void removeEpicById(Integer id) {
        super.removeEpicById(id);
        saveTask();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        saveTask();
    }

    @Override
    public void removeSubtaskById(Integer id) {
        super.removeSubtaskById(id);
        saveTask();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        saveTask();
    }

    @Override
    public Task createTask(Task newTask) {
        Task task1 = super.createTask(newTask);
        saveTask();
        return task1;
    }

    @Override
    public Epic createEpic(Epic newEpic) {
        Epic epic1 = super.createEpic(newEpic);
        saveTask();
        return epic1;
    }

    @Override
    public Subtask createSubtask(Subtask newSubtask) {
        Subtask subtask1 = super.createSubtask(newSubtask);
        saveTask();
        return subtask1;
    }

    @Override
    public void updateTask(Task newTask) {
        super.updateTask(newTask);
        saveTask();
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        super.updateSubtask(newSubtask);
        saveTask();
    }

    @Override
    public void updateEpic(Epic newEpic) {
        super.updateEpic(newEpic);
        saveTask();
    }

    public void saveTask() { //сохранение задачи в файл
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

    private static Task getFromString(String taskString) { //получение задачи из строки текста
        String[] taskLines = taskString.split(",");
        Integer id = Integer.parseInt(taskLines[0]);
        TaskType type = TaskType.valueOf(taskLines[1]);
        String name = taskLines[2];
        TaskStatus status = TaskStatus.valueOf(taskLines[3]);
        String description = taskLines[4];
        if (type.equals(TaskType.EPIC)) {
            return new Epic(id, TaskType.EPIC, name, status, description, new ArrayList<>());
        } else if (type == TaskType.SUBTASK) {
            int epic = Integer.parseInt(taskLines[5]);
            return new Subtask(id, TaskType.SUBTASK, name, status,description, epic);
        } else {
            return new Task(id, type, name,  status, description);
        }
    }

    public void loadFromFile(File file) { //прием задач из файла
        try (BufferedReader reader = new BufferedReader(new FileReader(file.toString()))) {
            reader.readLine();
            while (true) {
                String line = reader.readLine();
                if (isNull(line) || line.isEmpty()) {
                    break;
                }
                Task task = getFromString(line);
                if (task.getType().equals(TaskType.TASK)) {
                    tasks.put(task.getId(), task);
                } else if (task.getType().equals(TaskType.EPIC)) {
                    epics.put(task.getId(),(Epic) task);
                } else {
                    Subtask subtask = (Subtask) task;
                    subtasks.put(task.getId(),subtask);
                    Epic epic = epics.get(subtask.getEpicId());
                    epic.getSubtasksId().add(subtask.getId());
                }
                countOfId++;
            }
        } catch (FileNotFoundException exception) {
            throw new LoadFromFileException("Файл " + file + " не найден. " + exception.getMessage());
        } catch (IOException exception) {
            throw new LoadFromFileException("Ошибка чтения файла." + exception.getMessage());
        }
    }

}
