package hdwd.assignment;

public class Employee {
	
	private int id;
	private String name;
	private String gender;
	private String address;
	private Department dept = null;

	public Employee(){}
	
	public Employee(String n, String g, String a){
		name = n;
		gender = g;
		address = a;
	}
	
	public Employee(int i, String n, String g, String a){
		id = i;
		name = n;
		gender = g;
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

	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String g) {
		gender = g;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String a) {
		address = a;
	}
	
	public String toString() {
		return "[Employee, id:"+id+", name:"+name+", gender:"+gender+", address: "+address+"]";
	}
}
