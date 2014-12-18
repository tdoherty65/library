package hdwd.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Apache library used to read the data in the CSV files
 * - https://commons.apache.org/proper/commons-csv/user-guide.html
 * 
 * @author karl
 * 
 CALL EmployeesByLocation('Cork');

 *
 */
public class Import {	
	
	/**
	 * Main logic 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Import.java\n");
		
		try {
			Connection conn = DBConnect.connect();
			
			System.out.println("> building the library database");
			ScriptRunner runner = new ScriptRunner(conn, false, true);			
			runner.setLogWriter(null);
			runner.runScript(new BufferedReader(new FileReader("data/assignment.sql")));		
		} 
		catch (FileNotFoundException e) {
			System.err.println("> Unable to find the data file.");
			e.printStackTrace();
		} 
		catch (IOException e) {
			System.err.println("> An error has occured");
			e.printStackTrace();
		} 
		catch (SQLException e) {
			System.err.println("> An SQL error has occured");
			e.printStackTrace();
		}
		
		System.out.println("> The End.");
	}
}
