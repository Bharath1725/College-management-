import java.io.*;
import java.util.*;

public class CRMS {
    static Scanner sc = new Scanner(System.in);

    static class Resource {
        int id;
        String name, location;
        boolean available;

        Resource(int id, String name, String location, boolean available) {
            this.id = id;
            this.name = name;
            this.location = location;
            this.available = available;
        }

        public String toString() {
            return id + ". " + name + " | " + location + " | " + (available ? "Available" : "Booked");
        }
    }

    static class Attendance {
        String username, role, department, date;

        Attendance(String username, String role, String department, String date) {
            this.username = username;
            this.role = role;
            this.department = department;
            this.date = date;
        }

        public String toString() {
            return username + " | " + role + " | " + department + " | " + date;
        }
    }

    static class Marks {
        String username, subject, department;
        int score;

        Marks(String username, String subject, int score, String department) {
            this.username = username;
            this.subject = subject;
            this.score = score;
            this.department = department;
        }

        public String toString() {
            return username + " | " + subject + " | " + score + " | " + department;
        }
    }

    static List<Resource> resources = new ArrayList<>();
    static List<Attendance> attendanceList = new ArrayList<>();
    static List<Marks> marksList = new ArrayList<>();
    static final String RESOURCE_FILE = "resources.txt";
    static final String ATTENDANCE_FILE = "attendance.txt";
    static final String MARKS_FILE = "marks.txt";

    static Map<String, String> staffCredentials = new HashMap<>();
    static Map<String, String> studentCredentials = new HashMap<>();

    static void loadResources() {
        try (BufferedReader br = new BufferedReader(new FileReader(RESOURCE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                resources.add(new Resource(Integer.parseInt(p[0]), p[1], p[2], Boolean.parseBoolean(p[3])));
            }
        } catch (IOException ignored) {}
    }

    static void saveResources() {
        try (PrintWriter pw = new PrintWriter(RESOURCE_FILE)) {
            for (Resource r : resources) {
                pw.println(r.id + "|" + r.name + "|" + r.location + "|" + r.available);
            }
        } catch (IOException e) {
            System.out.println("Error saving resources.");
        }
    }

    static void loadAttendance() {
        try (BufferedReader br = new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                attendanceList.add(new Attendance(p[0], p[1], p[2], p[3]));
            }
        } catch (IOException ignored) {}
    }

    static void saveAttendance() {
        try (PrintWriter pw = new PrintWriter(ATTENDANCE_FILE)) {
            for (Attendance a : attendanceList) {
                pw.println(a.username + "|" + a.role + "|" + a.department + "|" + a.date);
            }
        } catch (IOException e) {
            System.out.println("Error saving attendance.");
        }
    }

    static void loadMarks() {
        try (BufferedReader br = new BufferedReader(new FileReader(MARKS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                marksList.add(new Marks(p[0], p[1], Integer.parseInt(p[2]), p[3]));
            }
        } catch (IOException ignored) {}
    }

    static void saveMarks() {
        try (PrintWriter pw = new PrintWriter(MARKS_FILE)) {
            for (Marks m : marksList) {
                pw.println(m.username + "|" + m.subject + "|" + m.score + "|" + m.department);
            }
        } catch (IOException e) {
            System.out.println("Error saving marks.");
        }
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\n[Admin Menu]\n1. Add Resource\n2. Edit Resource\n3. Delete Resource\n4. View All Resources\n5. Logout");
            int choice = sc.nextInt();
            switch (choice) {
                case 1: addResource(); break;
                case 2: editResource(); break;
                case 3: deleteResource(); break;
                case 4: viewResources(); break;
                case 5: return;
            }
        }
    }

    static void staffMenu(String username, String department) {
        while (true) {
            System.out.println("\n[Staff Menu]\n1. View Resources\n2. Book Resource\n3. Mark Attendance\n4. Add Marks\n5. View Attendance (All)\n6. View Marks (All)\n7. Logout");
            int choice = sc.nextInt();
            switch (choice) {
                case 1: viewResources(); break;
                case 2: bookResource(); break;
                case 3: markAttendance(username, "Staff", department); break;
                case 4: addMarks(username, department); break;
                case 5: viewAttendanceByDepartment(department); break;
                case 6: viewMarksByDepartment(department); break;
                case 7: return;
            }
        }
    }

    static void studentMenu(String username, String department) {
        while (true) {
            System.out.println("\n[Student Menu]\n1. View Resources\n2. View My Attendance\n3. View My Marks\n4. Logout");
            int choice = sc.nextInt();
            switch (choice) {
                case 1: viewResources(); break;
                case 2: viewAttendanceByUsername(username); break;
                case 3: viewMarksByUsername(username); break;
                case 4: return;
            }
        }
    }

    static void addResource() {
        sc.nextLine();
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter location: ");
        String loc = sc.nextLine();
        int id = resources.size() + 1;
        resources.add(new Resource(id, name, loc, true));
        saveResources();
        System.out.println("Resource added.");
    }

    static void editResource() {
        viewResources();
        System.out.print("Enter Resource ID to edit: ");
        int id = sc.nextInt();
        sc.nextLine();
        for (Resource r : resources) {
            if (r.id == id) {
                System.out.print("New name: ");
                r.name = sc.nextLine();
                System.out.print("New location: ");
                r.location = sc.nextLine();
                System.out.print("Available? true/false: ");
                r.available = sc.nextBoolean();
                saveResources();
                return;
            }
        }
        System.out.println("Resource not found.");
    }

    static void deleteResource() {
        viewResources();
        System.out.print("Enter Resource ID to delete: ");
        int id = sc.nextInt();
        resources.removeIf(r -> r.id == id);
        saveResources();
        System.out.println("Deleted.");
    }

    static void viewResources() {
        if (resources.isEmpty()) {
            System.out.println("No resources available.");
        } else {
            resources.forEach(System.out::println);
        }
    }

    static void bookResource() {
        viewResources();
        System.out.print("Enter Resource ID to book: ");
        int id = sc.nextInt();
        for (Resource r : resources) {
            if (r.id == id) {
                if (!r.available) {
                    System.out.println("Already booked.");
                    return;
                }
                r.available = false;
                saveResources();
                System.out.println("Booked.");
                return;
            }
        }
        System.out.println("Not found.");
    }

    static void markAttendance(String username, String role, String department) {
        sc.nextLine();
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        attendanceList.add(new Attendance(username, role, department, date));
        saveAttendance();
        System.out.println("Attendance marked.");
    }

    static void addMarks(String username, String department) {
        sc.nextLine();
        System.out.print("Enter student username: ");
        String student = sc.nextLine();
        System.out.print("Enter subject: ");
        String subject = sc.nextLine();
        System.out.print("Enter score: ");
        int score = sc.nextInt();
        marksList.add(new Marks(student, subject, score, department));
        saveMarks();
        System.out.println("Marks added.");
    }

    // Staff views department-wide
    static void viewAttendanceByDepartment(String dept) {
        boolean found = false;
        for (Attendance a : attendanceList) {
            if (a.department.equals(dept)) {
                System.out.println(a);
                found = true;
            }
        }
        if (!found) System.out.println("Nothing to display.");
    }

    static void viewMarksByDepartment(String dept) {
        boolean found = false;
        for (Marks m : marksList) {
            if (m.department.equals(dept)) {
                System.out.println(m);
                found = true;
            }
        }
        if (!found) System.out.println("Nothing to display.");
    }

    // Student views personal
    static void viewAttendanceByUsername(String username) {
        boolean found = false;
        for (Attendance a : attendanceList) {
            if (a.username.equals(username)) {
                System.out.println(a);
                found = true;
            }
        }
        if (!found) System.out.println("Nothing to display.");
    }

    static void viewMarksByUsername(String username) {
        boolean found = false;
        for (Marks m : marksList) {
            if (m.username.equals(username)) {
                System.out.println(m);
                found = true;
            }
        }
        if (!found) System.out.println("Nothing to display.");
    }

    public static void main(String[] args) {
        loadResources();
        loadAttendance();
        loadMarks();

        staffCredentials.put("staff", "staff123");
        studentCredentials.put("student", "student123");

        while (true) {
            System.out.println("\nLogin as: 1. Admin 2. Staff 3. Student 4. Exit");
            int choice = sc.nextInt();
            if (choice == 4) break;
            sc.nextLine();
            System.out.print("Enter username: ");
            String user = sc.nextLine();
            System.out.print("Enter password: ");
            String pass = sc.nextLine();
            System.out.print("Enter department: ");
            String dept = sc.nextLine();

            if (choice == 1) {
                if (user.equals("admin") && pass.equals("admin123")) {
                    adminMenu();
                } else {
                    System.out.println("Invalid admin credentials.");
                }
            } else if (choice == 2) {
                if (staffCredentials.containsKey(user) && staffCredentials.get(user).equals(pass)) {
                    staffMenu(user, dept);
                } else {
                    System.out.println("Invalid staff credentials.");
                }
            } else if (choice == 3) {
                if (studentCredentials.containsKey(user) && studentCredentials.get(user).equals(pass)) {
                    studentMenu(user, dept);
                } else {
                    System.out.println("Invalid student credentials.");
                }
            }
        }
    }
}
