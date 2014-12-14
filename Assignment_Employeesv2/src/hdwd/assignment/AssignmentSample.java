package hdwd.assignment;

import hdwd.util.DBConnect;

import java.sql.*;
import java.util.Scanner;

public class AssignmentSample {

	/**
	 * The program starts here
	 * 
	 * @param argsd
	 */
	public static void main(String[] args) {
		System.out.println("AssignmentSample.java\n");
		
		Scanner keyboard = new Scanner(System.in);
		
		do {
			try {
				// Establishing database connection
				Connection conn = DBConnect.connect();

				// Menu of the application
				System.out.println("===========================================================");
				System.out.println("Please select an option by typing the corresponding number.");
				System.out.println("0. Exit");				
				System.out.println("1. Add a new book borrower");
				System.out.println("2. Display records of all employees");
				System.out.println("3. Search for an employee");
				System.out.println("4. Check how many employees are there");				
				System.out.println("===========================================================");
				
				int option = keyboard.nextInt();

				// 0. exit the program 
				if (option == 0) {
					System.out.println("Exiting the program");
					break;
				}
				
				// 1. adding an employee
				else if (option == 1) {					
					System.out.println("Enter name of the borrower.");
					String name = keyboard.next();
					
					System.out.println("Enter gender of the borrower.");
					String gender = keyboard.next();
					
					System.out.println("Enter address of the borrower.");
					String address = keyboard.next();
					
					System.out.println("Enter department of the borrower.");
					String dept = keyboard.next();

					Employee emp = new Employee(name, gender, address);
					Department de = new Department(dept);
					addEmployee(conn, emp, de);

					System.out.println("The record has been successfully entered.");
				}
				
				// 2. displaying all employees
				else if (option == 2) {
					Employee[] emps = getAll(conn);
					for (int i = 0; i < emps.length; i++) {
						Department dept = emps[i].getDept();
						System.out.println("Employee #"+(i+1) + 
											", ID="+emps[i].getId() + 
											", Name="+emps[i].getName() + 
											",\t Gender="+emps[i].getGender() + 
											",\t Address="+emps[i].getAddress() + 
											",  \t Department="+dept.getName() );
					}
				}
				
				// 3. search for an employee by name
				else if (option == 3) {
					System.out.println("Please enter the name of the employee.");
					String name = keyboard.next();
					Employee em = search(conn, name);
					Department dept = em.getDept();
					
					System.out.println("Employee Details");
					System.out.println("================");
					System.out.println("Employee ID: "+em.getId());
					System.out.println("Employee Name: "+em.getName());
					System.out.println("Employee Gender: "+em.getGender());
					System.out.println("Employee Address: "+em.getAddress());
					System.out.println("Deptartment ID: "+dept.getId());
					System.out.println("Department Name: "+dept.getName());
					System.out.println();
				}
				
				// 4. display the employee head count 
				else if (option == 4) {
					System.out.println("There are " + count(conn) + " employees.");
				}				
				
				// unknown option
				else {
					System.out.println("Invalid option.");
				}

				// Closing the connection
				DBConnect.closeConnection(conn);
			}
			catch (Exception e) {
				System.out.println("Error");
			}

		} while(true);
		
		keyboard.close();
	}

	/**
	 * First get the ID of the department
	 * If it does not exist then create a new department
	 * Finally add a new employee
	 * 
	 * @param conn
	 * @param emp
	 */
	public static void addEmployee(Connection conn, Employee emp, Department dept) {
		try {			
			Statement st = conn.createStatement();
		    ResultSet rs = null;      
		    
		    // get the id of the department if it exists
		    rs = st.executeQuery("CALL getDeptByName(\""+dept.getName()+"\")");
			while (rs.next()) {
				dept.setId(rs.getInt("dept_id"));				 
			}			
			rs.close();
			
			// if it does not exist then create a new one
			if (dept.getId()==0) {
			    rs = st.executeQuery("CALL insertDepartment(\""+dept.getName()+"\")");  
			    rs.close();
			}			
			
			// try again to get the id 
			rs = st.executeQuery("CALL getDeptByName(\""+dept.getName()+"\")");     
		    while (rs.next()) {
				dept.setId(rs.getInt("dept_id"));				 
			}			
			rs.close();
			
			// now insert the employee details 
			rs = st.executeQuery("CALL insertEmployee(\""+emp.getName()+"\", \""+emp.getGender()+"\", \""+emp.getAddress()+"\", \""+dept.getId()+"\")");
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display details of all employees
	 * 
	 * @param conn
	 */
	public static Employee[] getAll(Connection conn) {
		Employee[] emps = new Employee[count(conn)];
		
		try {
			String sql = "CALL getAllDetails()";
			Statement st = conn.createStatement();
		    ResultSet rs = st.executeQuery(sql);     
		    
		    int i = 0;
			while (rs.next()) {
				Department dept = new Department(rs.getInt("dept_id"), rs.getString("dept_name"));
				Employee emp = new Employee(rs.getInt("emp_id"), rs.getString("emp_name"), rs.getString("emp_gender"), rs.getString("emp_address"));
				emp.setDept(dept);
				emps[i++] = emp; 
			}
			
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return emps;
	}

	/**
	 * Search employee by name by calling the stored procedure getByName('name')
	 */
	public static Employee search(Connection conn, String ename) {
		Employee emp = null;
		try {
			String sql = "CALL getByName(\""+ename+"\")";
			Statement st = conn.createStatement();
		    ResultSet rs = st.executeQuery(sql);     
		      
			while (rs.next()) {
				Department dept = new Department(rs.getInt("dept_id"), rs.getString("dept_name"));
				emp = new Employee(rs.getInt("emp_id"), rs.getString("emp_name"), rs.getString("emp_gender"), rs.getString("emp_address"));
				emp.setDept(dept);
			}
			
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return emp;
	}

	/**
	 * Check how many employees are in the table and return the value
	 * 
	 * @param conn
	 * @return
	 */
	public static int count(Connection conn) {
		int num = -1;
		try {
			String sql = "CALL getEmployeeCount()";
			Statement st = conn.createStatement();
		    ResultSet rs = st.executeQuery(sql);     
		      
			while (rs.next()) 
				num = rs.getInt(1);
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return num;		
	}
}
