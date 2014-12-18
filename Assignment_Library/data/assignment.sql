DROP TABLE IF EXISTS loan;

DROP PROCEDURE IF EXISTS InsertBook;
Create procedure InsertBook(
in isbn varchar(40), 
in title varchar(40), 
publyear int(40), 
author varchar(40), 
authornat varchar(40))

insert into books (ISBN, title, publyear, author, authornat) values(isbn, title, publyear, author, authornat) ;

DROP PROCEDURE IF EXISTS InsertBorrower;
Create procedure InsertBorrower(
in SSN bigint(11), 
in name varchar(20), 
in address varchar(20), 
phone varchar(20), 
nobooks int(11))

insert into borrower(ssn, name, address, phone)  values (ssn, name, address, phone);


DROP PROCEDURE IF EXISTS FindBooks;
Create procedure FindBooks
(in author varchar(50))

select * from books where author = author;

DROP PROCEDURE IF EXISTS FindNumCopies;
Create procedure FindNumCopies(
in isbn varchar(50))

select count from books where isbn = isbn;

#Need to create loan table for this one: I.e.: 


create table loan(
TitleNo int(11), 
CopyNo int(11), 
BorrowerNo int(11), 
Date Date, 
BorrowerName varchar(20));

DROP PROCEDURE IF EXISTS InsertLoan;
create procedure InsertLoan (
titleNo int(11), 
CopyNo int(11), 
BorrowerNo int(11), 
Date date, 
BorrowerName varchar(20))
insert into loan values(titleNo, CopyNo, BorrowerNo, Date, BorrowerName);

DROP PROCEDURE IF EXISTS GetAllAuthors;
CREATE PROCEDURE GetAllAuthors()
SELECT id, author, authornat 
FROM books; 