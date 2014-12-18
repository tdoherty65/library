package hdwd.assignment;

public class Borrower {
	//copied from book
	int id;
	String ssn;
	String name; 
	String address; 
	String phone;
	
	
	public Borrower (String s, String n, String a, String p) {
		ssn = s;
		name = n;
		address = a;
		phone = p;
	}



	public String toString() {
		return "[Borrower, id:" + id + ", ssn: " + ssn + ", name:" + name + ", address:" + address + ", phone:" + phone + "]";
	}
}



