import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentManager manager = new StudentManager();

    public static void main(String[] args) {
        int choice;

        do {
            printMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> searchStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> System.out.println("\nThank you for using Student Management System!");
                default -> System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n======================================");
        System.out.println("      STUDENT MANAGEMENT SYSTEM");
        System.out.println("======================================");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
        System.out.println("======================================");
    }

    private static void addStudent() {
        System.out.println("\n--- Add Student ---");
        int id = readPositiveInt("Enter Student ID: ");
        String name = readNonEmpty("Enter Name: ");
        int age = readPositiveInt("Enter Age: ");
        String course = readNonEmpty("Enter Course: ");
        String email = readEmail();

        boolean added = manager.addStudent(new Student(id, name, age, course, email));
        if (added) {
            System.out.println("Student added successfully.");
        } else {
            System.out.println("A student with this ID already exists.");
        }
    }

    private static void viewStudents() {
        System.out.println("\n--- Student Records ---");
        List<Student> students = manager.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void searchStudent() {
        System.out.println("\n--- Search Student ---");
        int id = readPositiveInt("Enter Student ID: ");
        Student student = manager.searchStudent(id);

        if (student != null) {
            System.out.println("\nStudent Found:");
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");
        int id = readPositiveInt("Enter Student ID to update: ");
        Student student = manager.searchStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        String name = readNonEmpty("Enter New Name: ");
        int age = readPositiveInt("Enter New Age: ");
        String course = readNonEmpty("Enter New Course: ");
        String email = readEmail();

        manager.updateStudent(id, name, age, course, email);
        System.out.println("Student updated successfully.");
    }

    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        int id = readPositiveInt("Enter Student ID to delete: ");

        if (manager.deleteStudent(id)) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static int readPositiveInt(String message) {
        while (true) {
            int value = readInt(message);
            if (value > 0) return value;
            System.out.println("Please enter a positive value.");
        }
    }

    private static String readNonEmpty(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("This field cannot be empty.");
        }
    }

    private static String readEmail() {
        while (true) {
            String email = readNonEmpty("Enter Email: ");
            if (email.contains("@") && email.contains(".")) return email;
            System.out.println("Please enter a valid email address.");
        }
    }
}
