import java.sql.*;

public class FetchEmployeeData {
    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/your_database_name"; // Replace with your DB name
        String user = "root";  // Replace with your MySQL username
        String password = "your_password";  // Replace with your MySQL password

        // JDBC objects
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 1. Load and register MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Establish connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Database connected successfully!");

            // 3. Create statement object
            stmt = conn.createStatement();

            // 4. Execute SQL query
            String query = "SELECT EmpID, Name, Salary FROM Employee";
            rs = stmt.executeQuery(query);

            // 5. Display fetched records
            System.out.println("\n--- Employee Table Data ---");
            while (rs.next()) {
                int empId = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");

                System.out.println("EmpID: " + empId + ", Name: " + name + ", Salary: " + salary);
            }
        } 
        catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found: " + e.getMessage());
        } 
        catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
        } 
        finally {
            // 6. Close connections in reverse order
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("❌ Error closing resources: " + e.getMessage());
            }
        }
    }
}
