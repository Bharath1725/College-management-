 Description
CRMS is a simple console-based Java application designed to manage college resources, attendance, and marks for both staff and students. It provides different interfaces for Admin, Staff, and Student users, each with role-specific functionalities.

Features
Add, edit, and delete resources.

View all resources.

 Staff
View and book available resources.

Mark attendance for themselves.

Add marks for students.

View attendance and marks for their department.

 Student
View available resources.

View their own attendance.

View their own marks.

 Data Storage
Data is persisted across sessions using simple text files:

resources.txt: Stores resource details.

attendance.txt: Stores attendance records.

marks.txt: Stores marks records.

 Login Credentials
Admin

Username: admin

Password: admin123

Sample Staff

Username: staff

Password: staff123

Sample Student

Username: student

Password: student123

 Department is required during login and is used for access filtering.

 How to Run
Ensure you have Java 8 or above installed.

Save the code in a file named CRMS.java.

Create the following empty files in the same directory:

resources.txt

attendance.txt

marks.txt

Compile the program:

bash
Copy
Edit
javac CRMS.java
Run the program:

bash
Copy
Edit
java CRMS
 Notes
The application runs in a loop until the user chooses to exit.

File I/O is used for persistent data.

Simple command-line interface (CLI).

Resource IDs are auto-incremented.

Admin actions directly affect the shared resources file.

 Potential Improvements
GUI interface using JavaFX or Swing.

Database integration (e.g., SQLite, MySQL).

Add password encryption.

Input validation and error handling improvements.

Logging and audit trails.
