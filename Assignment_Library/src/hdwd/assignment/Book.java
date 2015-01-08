package hdwd.assignment;

public class Book {

 int id;
 int copyno = 0;
 String isbn; 
 String title; 
 String pubyear;
 Author auth = null;

 
 
 public Book(String is, String t, String p) {
  isbn = is;
  title = t;
  pubyear = p;
 }

 public Book(int i, String is, String t, String p) {
  id = i;
  isbn = is;
  title = t;
  pubyear = p;

 }
 
 public Book(String is) {
		isbn = is;
	}

 public String toString() {
  return "[Book, id:" + id + ", isbn: " + isbn + ", title:" + title + ", pubyear:" + pubyear + "]";
 }
}