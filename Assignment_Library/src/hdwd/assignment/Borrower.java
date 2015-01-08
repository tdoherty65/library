package hdwd.assignment;

public class Borrower {
 
 int booksBorrowed = 0;
 int id;
 String ssn;
 String name; 
 String address; 
 String phone;
 int titleno;
 int copyno;
 String date;
 
 
 public Borrower (String s, String n, String a, String p) {
  ssn = s;
  name = n;
  address = a;
  phone = p;
 
 }
 
 
 public Borrower (String s, int t, int c, String d) {
	  ssn = s;
	  titleno = t;
	  copyno = c;
	  date = d;
	  
	 
	 }
 

 public int getCountBooksBorrowed(){
  return booksBorrowed;
 }
 
    public void incrementCount(){
     booksBorrowed++;
    }
    

 public String toString() {
  return "[Borrower, id:" + id + ", ssn: " + ssn + ", name:" + name + ", address:" + address + ", phone:" + phone + "]";
 }
}
