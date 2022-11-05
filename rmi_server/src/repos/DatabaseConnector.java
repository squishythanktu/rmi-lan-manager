package repos;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {
	private static String DB_HOST = "localhost";
	private static int DB_PORT = 3306;
	private static String DB_NAME = "pbl4";
	private static String DB_USERNAME = "root";
	private static String DB_PASSWORD = "";

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String uri = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
			Connection conn = null;
			conn = DriverManager.getConnection(uri, DB_USERNAME, DB_PASSWORD);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
