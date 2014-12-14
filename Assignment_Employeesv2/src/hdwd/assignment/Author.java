package hdwd.assignment; //copied from department

public class Author {


		private int id;
		private String name;
		
		public Author (String a){
			name = a;
		}
		
		public Author (int i, String a){
			id = i;
			name = a;
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
			return "[Author, id:"+id+", name:"+name+"]";
		}
	}
