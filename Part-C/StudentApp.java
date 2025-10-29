// StudentApp.java
import java.util.*;

public class StudentApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            StudentDAO dao = new StudentDAO();
            int choice;

            do {
                System.out.println("\n===== STUDENT MANAGEMENT MENU =====");
                System.out.println("1. Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Student ID: ");
                        int id = sc.nextInt();
                        sc.nextLine(); // consume newline
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Department: ");
                        String dept = sc.nextLine();
                        System.out.print("Enter Marks: ");
                        double marks = sc.nextDouble();

                        Student s = new Student(id, name, dept, marks);
                        dao.addStudent(s);
                        break;

                    case 2:
                        System.out.println("\n--- All Students ---");
                        for (Student st : dao.getAllStudents()) {
                            System.out.println(st);
                        }
                        break;

                    case 3:
                        System.out.print("Enter Student ID to update: ");
                        int uid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter New Name: ");
                        String newName = sc.nextLine();
                        System.out.print("Enter New Department: ");
                        String newDept = sc.nextLine();
                        System.out.print("Enter New Marks: ");
                        double newMarks = sc.nextDouble();

                        Student updated = new Student(uid, newName, newDept, newMarks);
                        dao.updateStudent(updated);
                        break;

                    case 4:
                        System.out.print("Enter Student ID to delete: ");
                        int did = sc.nextInt();
                        dao.deleteStudent(did);
                        break;

                    case 5:
                        System.out.println("👋 Exiting Application...");
                        dao.close();
                        break;

                    default:
                        System.out.println("❌ Invalid choice. Try again!");
                }
            } while (choice != 5);

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
