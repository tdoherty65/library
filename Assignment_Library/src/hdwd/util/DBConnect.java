package hdwd.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DBConnect {
	
	public static Connection connect() {
		Connection conn = null;
		try {
			// Load the JDBC driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			// Database access variables
			String url = "jdbc:mysql://localhost/";
			String database = "library";
			String username = "root";
			String password = "SQLroot";
			conn = DriverManager.getConnection(url+database+"?user="+username+"&password="+password);			
		}
		catch (SQLException e){
			System.err.println("> SQLException: " + e.getMessage());
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			System.err.println("> InstantiationException: " + e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConnection(Connection conn){
		try{
			conn.close();
		}
		catch (Exception e){
			System.err.println("> Unable to close connection.");
			e.printStackTrace();
		}
	}
}
