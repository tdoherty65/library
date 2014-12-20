package hdwd.assignment; //copied from department

public class Author {

	int id;
	String name;
	String nationality;

	public Author(String n) {
		name = n;
	}

	public Author(int i, String n) {
		id = i;
		name = n;
	}
	
	public Author(String n, String na) {
		name = n;
		nationality = na;
	}
	
	public Author(int i, String n, String na) {
		id = i;
		name = n;
		nationality = na;
	}

	public String toString() {
		return "[Author, id:" + id + ", name:" + name + ", nationality:" + nationality + "]";
	}
}
