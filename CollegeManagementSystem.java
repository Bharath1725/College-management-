import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Student {
     int studentId;
     String name;
     String department;

    public Student(int studentId, String name, String department) {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
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
        StudentManagement studentManagement = new StudentManagement();
        studentManagement.addStudent("Bharath", "Information Technology");
        studentManagement.addStudent("Vijay", "Electrical Engineering");
        studentManagement.addStudent("Jasiya", "Civil Enginerring");
        System.out.println("\n--- All Students ---");
        studentManagement.getAllStudents().forEach(System.out::println);
        Student alice = studentManagement.getStudent(3);
        if (alice != null) {
            System.out.println("\n--- Retrieved Student ---");
            System.out.println(alice);
        }
    }
}
