package hdwd.assignment;
public class Book {
	
	private int id;
	private String isbn; //was isbn
	private String title;  //was title
	private String pubyear;  //was pubyear
	private Author auth = null;  //was Department dept = null;

	public Book(){}
	
	public Book(String is, String t, String p){
		isbn = is;
		title = t;
		pubyear = p;
		
	}
	
	public Book(int i, String is, String t, String p, String a){
		id = i;
		isbn = is;
		title = t;
		pubyear = p;
		
	}
	
	public Author getauth() {
		return auth;
	}

	public void setauth(Author d) {
		auth = d;
	}

	public int getId() {
		return id;
	}

	public void setId(int i) {
		id = i;
	}
	
	public String getisbn() {
		return isbn;
	}

	public void setisbn(String is) {
		isbn = is;
	}

	public String gettitle() {
		return title;
	}

	public void settitle(String t) {
		title = t;
	}

	public String getpubyear() {
		return pubyear;
	}

	public void setpubyear(String p) {
		pubyear = p;
	}

	
	public String toString() {
		return "[Book, id:"+id+", isbn: "+isbn+", title:"+title+", pubyear:"+pubyear+"]";
	}
}
