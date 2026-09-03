import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<Student> students;
    private static final String FILE_NAME = "students.dat";

    public StudentManager() {
        students = loadStudents();
    }

    public boolean addStudent(Student student) {
        if (searchStudent(student.getId()) != null) {
            return false;
        }
        students.add(student);
        saveStudents();
        return true;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public Student searchStudent(int id) {
        for (Student student : students) {
            if (student.getId() == id) return student;
        }
        return null;
    }

    public boolean updateStudent(int id, String name, int age, String course, String email) {
        Student student = searchStudent(id);
        if (student == null) return false;

        student.setName(name);
        student.setAge(age);
        student.setCourse(course);
        student.setEmail(email);
        saveStudents();
        return true;
    }

    public boolean deleteStudent(int id) {
        Student student = searchStudent(id);
        if (student == null) return false;

        students.remove(student);
        saveStudents();
        return true;
    }

    @SuppressWarnings("unchecked")
    private List<Student> loadStudents() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Student>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void saveStudents() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            output.writeObject(new ArrayList<>(students));
        } catch (IOException e) {
            System.out.println("Warning: Could not save data.");
        }
    }
}
