// StudentDAO.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

    private Connection conn;

    // Constructor - connect to DB
    public StudentDAO() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Add Student
    public void addStudent(Student s) throws SQLException {
        String sql = "INSERT INTO Student (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, s.getStudentID());
            ps.setString(2, s.getName());
            ps.setString(3, s.getDepartment());
            ps.setDouble(4, s.getMarks());
            ps.executeUpdate();
            System.out.println("✅ Student added successfully!");
        }
    }

    // View all students
    public List<Student> getAllStudents() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM Student";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("StudentID"),
                        rs.getString("Name"),
                        rs.getString("Department"),
                        rs.getDouble("Marks")
                );
                list.add(s);
            }
        }
        return list;
    }

    // Update Student details
    public void updateStudent(Student s) throws SQLException {
        String sql = "UPDATE Student SET Name=?, Department=?, Marks=? WHERE StudentID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getDepartment());
            ps.setDouble(3, s.getMarks());
            ps.setInt(4, s.getStudentID());
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Student updated successfully!");
            else
                System.out.println("⚠️ No student found with ID " + s.getStudentID());
        }
    }

    // Delete Student
    public void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM Student WHERE StudentID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Student deleted successfully!");
            else
                System.out.println("⚠️ No student found with ID " + id);
        }
    }

    // Close connection
    public void close() throws SQLException {
        if (conn != null) conn.close();
    }
}
