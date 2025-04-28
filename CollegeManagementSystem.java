import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Person {
    String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Student extends Person {
     int studentId;
     String department;

    public Student(int studentId, String name, String department) {
        super(name);
        this.studentId = studentId;
        this.department = department;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}

class Course {
     String courseCode;
     String courseName;

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}

class StudentManagement {
     Map<Integer, Student> students = new HashMap<>();
     int nextStudentId = 1;

    public void addStudent(String name, String department) {
        Student student = new Student(nextStudentId++, name, department);
        students.put(student.getStudentId(), student);
        System.out.println("Student " + name + " added with ID: " + student.getStudentId());
    }

    public Student getStudent(int studentId) {
        return students.get(studentId);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }
}

public class CollegeManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagement studentManagement = new StudentManagement();
        
        System.out.println("Enter the number of students to add:");
        int numberOfStudents = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numberOfStudents; i++) {
            System.out.println("Enter name of student " + (i + 1) + ":");
            String name = scanner.nextLine();
            System.out.println("Enter department of student " + (i + 1) + ":");
            String department = scanner.nextLine();
            studentManagement.addStudent(name, department);
        }

        System.out.println("\n--- All Students ---");
        studentManagement.getAllStudents().forEach(System.out::println);

        System.out.println("Enter the student ID to retrieve:");
        int studentId = scanner.nextInt();
        Student retrievedStudent = studentManagement.getStudent(studentId);
        if (retrievedStudent != null) {
            System.out.println("\n--- Retrieved Student ---");
            System.out.println(retrievedStudent);
        } else {
            System.out.println("Student with ID " + studentId + " not found.");
        }

        scanner.close();
    }
}
