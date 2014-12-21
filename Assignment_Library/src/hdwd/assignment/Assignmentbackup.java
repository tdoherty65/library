package hdwd.assignment;

import hdwd.util.DBConnect;

import java.sql.*;
import java.util.Scanner;

public class Assignment {

	/**
	 * The program starts here
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Assignment.java\n");

		Scanner keyboard = new Scanner(System.in);

		while (true) {
			try {
				// Establishing database connection
				Connection conn = DBConnect.connect();

				// Menu of the application
				System.out.println("===========================================================");
				System.out.println("Please select an option by typing the corresponding number.");
				System.out.println("0. Exit");
				System.out.println("1. Q1a.Insert Book and Author (WORKING)");
				System.out.println("2. Q1b.Insert Book Borrower (WORKING)");
				System.out.println("3. Q2. Search For Book By Author(WORKING");
				System.out.println("4. Q3. Search For Book by ISBN");
				System.out.println("5. Q4  Lend Book (Limited to 5 books)");
				System.out.println("6. Q5  Display All Authors (WORKING)");
				System.out.println("===========================================================");

				int option = Integer.parseInt(keyboard.nextLine());

				// 0. exit the program
				if (option == 0) {
					System.out.println("Exiting the program");
					break;
				}

				// Q1a. insert book and author
				else if (option == 1) {
					System.out.println("Enter ISBN of the book.");
					String isbn = keyboard.nextLine();

					System.out.println("Enter Title of the book.");
					String title = keyboard.nextLine();

					System.out.println("Enter Publishing Year of the book.");
					String pubyear = keyboard.nextLine();

					System.out.println("Enter Author of the book.");
					String auth = keyboard.nextLine();
					
					System.out.println("Enter Author Nationality.");
					String nat = keyboard.nextLine();

					Book boo = new Book(isbn, title, pubyear);
					Author a = new Author(auth, nat);
					addBook(conn, boo, a);

					System.out.println("The record has been successfully entered.");
				}
					// Q1b. add a borrower
					else if (option == 2) {
						System.out.println("Enter Borrower SSN.");
						String ssn = keyboard.nextLine();

						System.out.println("Enter Borrower Name .");
						String name = keyboard.nextLine();

						System.out.println("Enter Borrower Address.");
						String address = keyboard.nextLine();

						System.out.println("Enter Borrower Phone.");
						String phone = keyboard.nextLine();
						
						Borrower borr = new Borrower (ssn, name, address, phone);
						
						addBorrower(conn, borr);

						System.out.println("The record has been successfully entered.");
				}

				// Q2. Search for book based on author
				else if (option == 3) {
					System.out.println("Enter Authors Name.");
					String author = keyboard.nextLine();
					
					Author auth = new Author (author);
					Book[] books = getAllBooks(conn, auth);
					for (int i = 0; i < books.length; i++) {
						System.out.printf("Book #%d : ID=%s, isbn=%s, title=%s, pubyear=%s \n", i, books[i].id, books[i].isbn, books[i].title, books[i].pubyear);
					}
				}
		
			
				// 3. Display how many copies of a Book in library (Display book count by ISBN)
				else if (option == 4) {
					
				System.out.println("Enter ISBN Number.");
				String isbn = keyboard.nextLine();
					
					ISBN is = new ISBN (isbn);
					Book[] books = getAllBooksISBN(conn, is);
					for (int i = 0; i < books.length; i++) {
						System.out.printf("Book #%d : ID=%s, isbn=%s, title=%s, pubyear=%s \n", i, books[i].id, books[i].isbn, books[i].title, books[i].pubyear);
					}
										
					
				}
						
				
				// 4. Lend Book (Limited to 5 books)
				
				
				

				// 5. BONUS display all authors BONUS
				else if (option == 6) {
					Author[] authors = getAllAuthors(conn);
					for (int i = 0; i < authors.length; i++) {
						System.out.printf("Author #%d : ID=%s, Name=%s, Nationality=%s \n", i, authors[i].id, authors[i].name, authors[i].nationality);
					}
				}				
				
				// unknown option
				else {
					System.out.println("Invalid option.");
				}

				// close the connection
				DBConnect.closeConnection(conn);
			}
			catch (Exception e) {
				System.out.println("Error");
			}
		} 

		keyboard.close();
	}

	/**
	 * Call the InsertBook stored procedure with the correct parameters. The %s
	 * signifies a String to be replaced The %d signifies an int to be replaced.
	 * 
	 * @param conn
	 * @param boo
	 *            Book
	 * @param auth
	 *            Author
	 */
	//add book
	public static void addBook(Connection conn, Book boo, Author auth) {
		try {
			Statement st = conn.createStatement();
			String sql = String.format("CALL InsertBook('%s', '%s', '%s', '%s', '%s')", 
											boo.isbn, boo.title, boo.pubyear, auth.name, auth.nationality);
			ResultSet rs = st.executeQuery(sql);
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//add borrower
	public static void addBorrower(Connection conn, Borrower borr ) {
		try {
			Statement st = conn.createStatement();
			String sql = String.format("CALL InsertBorrower('%s', '%s', '%s', '%s')", 
											borr.ssn, borr.name, borr.address, borr.phone);
			ResultSet rs = st.executeQuery(sql);
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Get all of the authors from the database and return in an Author array.
	 * If an error occurs return an empty array.
	 * 
	 * @param conn
	 */
	//get all authors
	public static Author[] getAllAuthors(Connection conn) {
		Author[] authors;

		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("CALL GetAllAuthors()");

			int total = 0, i = 0;

			// find out how many items need to be stored in the array
			while (rs.next())
				total++;
			authors = new Author[total];

			// reset the iterator to the start of the results and save the
			// details of each row to a new Author object
			rs.beforeFirst();
			while (rs.next())
				authors[i++] = new Author(rs.getInt("authorid"), rs.getString("authorname"), rs.getString("authornat"));

			rs.close();
			return authors;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return new Author[0];
		}
		
	}
	
	//get all books method used in Q2
	public static Book[] getAllBooks(Connection conn, Author auth) {
		Book[] books;

		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("CALL GetAllBooks(\""+auth.name+"\")");

			int total = 0, i = 0;

			// find out how many items need to be stored in the array
			while (rs.next())
				total++;
			books = new Book [total];

			// reset the iterator to the start of the results and save the
			// details of each row to a new Author object
			rs.beforeFirst();
			while (rs.next())
				books[i++] = new Book(rs.getInt("id"), rs.getString("isbn"), rs.getString("title"), rs.getString("pubyear"));

			rs.close();
			return books;
		}
			catch (SQLException e) {
				e.printStackTrace();
				return new Book[0];
			
		}
	}
	
		//get all books by ISBN method used in Q3
			public static Book[] getAllBooksISBN(Connection conn, ISBN is) {
			Book[] books;

				try {
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery("CALL GetAllBooksISBN(\""+is.isbn+"\")");

					int total = 0, i = 0;

					// find out how many items need to be stored in the array
					while (rs.next())
						total++;
					books = new Book [total];

					// reset the iterator to the start of the results and save the
					// details of each row to a new ISBN object
					//rs.beforeFirst();
					//while (rs.next())
						books[i++] = new Book(rs.getInt("id"), rs.getString("isbn"), rs.getString("title"), rs.getString("pubyear"));

					rs.close();
					return books;
		
		}
		catch (SQLException e) {
			e.printStackTrace();
			return new Book[0];	//END of AllBooksISBN method
		}//end of methods
	}


}
