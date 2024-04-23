public class Task {
    private static String name;
    private static String description;
    String[] fullTask = new String[2];
    private static Integer Id;

    public static String getName() {
        return name;
    }

    public static String getDescription() {
        return description;
    }

    public static Integer getId() {
        return Id;
    }

    public static void setName(String name) {
        Task.name = name;
    }

    public static void setDescription(String description) {
        Task.description = description;
    }

    public static void setId(Integer id) {
        Id = id;
    }
}


