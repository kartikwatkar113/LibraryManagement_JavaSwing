package library.dbhelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
	private static final String URL = "jdbc:mysql://localhost:3306/library_management"; // Update to your MySQL URL
	private static final String USER = "root"; // Replace with your MySQL username
	private static final String PASSWORD = "Crwatkar4667"; // Replace with your MySQL password

	public static Connection connect() {
		try {
			// MySQL connection
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			return conn;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
