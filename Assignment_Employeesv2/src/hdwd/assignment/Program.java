package hdwd.assignment;

		import hdwd.util.DBConnect;

		import java.sql.*;
import java.util.Scanner;

public class Program {	

			/**
			 * The program starts here
			 * 
			 * @param args
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
						System.out.println("1. Add a new book");
						System.out.println("2. Search for a book by Author");
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
							System.out.println("Enter ISBN of the book.");
							String isbn = keyboard.next();
							
							System.out.println("Enter Title of the book.");
							String title = keyboard.next();
							
							System.out.println("Enter Publishing Year of the book.");
							String pubyear = keyboard.next();
												
							System.out.println("Enter Author of the book.");
							String auth = keyboard.next();

							Book boo = new Book (isbn, title, pubyear);
							Author a = new Author(auth);
							addBook(conn, boo, a);

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
			 */// HELP ME HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			public static void addBook(Connection conn, Book boo, Author auth) {
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
					
					
					rs = st.executeQuery("CALL insertEmployee(\""+boo.getisbn()+"\", \""+boo.gettitle()+"\", \""+boo.getpubyear()+"\", \""+dept.getId()+"\")");
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
