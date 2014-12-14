package hdwd.assignment;

public class Department {

	private int id;
	private String name;
	
	public Department(String n){
		name = n;
	}
	
	public Department(int i, String n){
		id = i;
		name = n;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "[Department, id:"+id+", name:"+name+"]";
	}
}
