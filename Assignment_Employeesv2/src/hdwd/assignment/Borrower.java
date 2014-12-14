package hdwd.assignment;

public class Borrower {
	
	private int id;
	private String ssn; //added by me
	private String name;
	private String phone;
	private String address;
	private Department dept = null;

	public Borrower(){}
	
	public Borrower(String s, String n, String p, String a){
		ssn = s;
		name = n;
		phone = p;
		address = a;
	}
	
	public Borrower(int i, String s, String n, String p, String a){
		id = i;
		ssn = s;
		name = n;
		phone = p;
		address = a;
	}
	
	public Department getDept() {
		return dept;
	}

	public void setDept(Department d) {
		dept = d;
	}

	public int getId() {
		return id;
	}

	public void setId(int i) {
		id = i;
	}
	
	public String getSsn() {
		return ssn;
	}

	public void setSsn(String s) {
		ssn = s;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String p) {
		phone = p;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String a) {
		address = a;
	}
	
	public String toString() {
		return "[Borrower, id:"+id+", ssn: "+ssn+", name:"+name+", phone:"+phone+", address: "+address+"]";
	}
}
