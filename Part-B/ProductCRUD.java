import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    // Database credentials
    static final String URL = "jdbc:mysql://localhost:3306/your_database_name"; // Replace with your DB name
    static final String USER = "root";  // Replace with your MySQL username
    static final String PASSWORD = "your_password";  // Replace with your MySQL password

    public static void main(String[] args) {
        Connection conn = null;
        Scanner sc = new Scanner(System.in);

        try {
            // 1. Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Establish Connection
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false); // Enable manual transaction handling
            System.out.println("✅ Connected to database successfully!");

            int choice;
            do {
                System.out.println("\n===== PRODUCT MANAGEMENT MENU =====");
                System.out.println("1. Create (Insert Product)");
                System.out.println("2. Read (Display Products)");
                System.out.println("3. Update (Modify Product)");
                System.out.println("4. Delete (Remove Product)");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        insertProduct(conn, sc);
                        break;
                    case 2:
                        displayProducts(conn);
                        break;
                    case 3:
                        updateProduct(conn, sc);
                        break;
                    case 4:
                        deleteProduct(conn, sc);
                        break;
                    case 5:
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println("❌ Invalid choice. Try again!");
                }
            } while (choice != 5);

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("❌ Error closing connection: " + e.getMessage());
            }
            sc.close();
        }
    }

    // CREATE operation
    private static void insertProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter Product ID: ");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline
            System.out.print("Enter Product Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();

            String sql = "INSERT INTO Product (ProductID, ProductName, Price, Quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.setInt(4, qty);

            int rows = ps.executeUpdate();
            conn.commit(); // Commit transaction
            System.out.println(rows + " product(s) inserted successfully!");
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback transaction
                System.out.println("❌ Insert failed. Transaction rolled back.");
            } catch (SQLException ex) {
                System.out.println("❌ Rollback error: " + ex.getMessage());
            }
        }
    }

    // READ operation
    private static void displayProducts(Connection conn) {
        try {
            String sql = "SELECT * FROM Product";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n--- Product Table ---");
            System.out.printf("%-10s %-20s %-10s %-10s%n", "ProductID", "ProductName", "Price", "Quantity");
            System.out.println("-----------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d %-20s %-10.2f %-10d%n",
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Read error: " + e.getMessage());
        }
    }

    // UPDATE operation
    private static void updateProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter Product ID to update: ");
            int id = sc.nextInt();
            System.out.print("Enter new Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter new Quantity: ");
            int qty = sc.nextInt();

            String sql = "UPDATE Product SET Price = ?, Quantity = ? WHERE ProductID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, price);
            ps.setInt(2, qty);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("✅ Product updated successfully!");
            } else {
                System.out.println("⚠️ No product found with ID " + id);
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("❌ Update failed. Transaction rolled back.");
            } catch (SQLException ex) {
                System.out.println("❌ Rollback error: " + ex.getMessage());
            }
        }
    }

    // DELETE operation
    private static void deleteProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter Product ID to delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM Product WHERE ProductID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("✅ Product deleted successfully!");
            } else {
                System.out.println("⚠️ No product found with ID " + id);
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("❌ Delete failed. Transaction rolled back.");
            } catch (SQLException ex) {
                System.out.println("❌ Rollback error: " + ex.getMessage());
            }
        }
    }
}
