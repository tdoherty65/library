DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS departments;
DROP PROCEDURE IF EXISTS insertEmployee;
DROP PROCEDURE IF EXISTS getByAddress;
DROP PROCEDURE IF EXISTS getByName;
DROP PROCEDURE IF EXISTS getByDepartment;
DROP PROCEDURE IF EXISTS getAllDetails;
DROP PROCEDURE IF EXISTS getEmployeeCount;

CREATE TABLE employees (
	emp_id INT(11) NOT NULL AUTO_INCREMENT,
	dept_id INT(11) REFERENCES departments(dept_id), 
	emp_name VARCHAR(10), 
	emp_gender VARCHAR(10),
	emp_address VARCHAR(10),	
	PRIMARY KEY (emp_id)
);

CREATE TABLE departments (
	dept_id INT(11) NOT NULL AUTO_INCREMENT, 
	dept_name VARCHAR(10),
	PRIMARY KEY (dept_id)
);


INSERT INTO departments (dept_name) 
VALUES 
	('Finance'),
	('HR');
	
INSERT INTO employees (dept_id, emp_name, emp_gender, emp_address) 
VALUES 
	(1, 'Joe', 'Male', 'Cork'),
	(1, 'Jack', 'Male', 'Kerry'),
	(2, 'Mary', 'Female', 'Cork'),
	(1, 'Lucy', 'Female', 'Dublin'),
	(2, 'Larry', 'Male', 'Dublin');	
	
	
	
CREATE PROCEDURE insertEmployee(
	IN ip_name VARCHAR(10),
	IN ip_gender VARCHAR(10),
	IN ip_address VARCHAR(10),	
	IN ip_dept_id INT(11)
)	
INSERT INTO employees (dept_id, emp_name, emp_gender, emp_address) 
VALUES (ip_dept_id, ip_name, ip_gender, ip_address);


CREATE PROCEDURE insertDepartment(
	IN ip_name VARCHAR(10)
)	
INSERT INTO departments (dept_name) 
VALUES (ip_name);


CREATE PROCEDURE getByAddress(IN ip_address VARCHAR(10))
SELECT *
FROM employees, departments
WHERE employees.dept_id = departments.dept_id
AND emp_address = ip_address;

CREATE PROCEDURE getByName(IN ip_name VARCHAR(10))
SELECT *
FROM employees, departments
WHERE employees.dept_id = departments.dept_id
AND employees.emp_name = ip_name;

CREATE PROCEDURE getDeptByName(IN ip_name VARCHAR(10))
SELECT *
FROM departments
WHERE dept_name = ip_name;

CREATE PROCEDURE getAllDetails()
SELECT *
FROM employees, departments
WHERE employees.dept_id = departments.dept_id;

CREATE PROCEDURE getEmployeeCount()
SELECT COUNT(emp_id) AS num FROM employees;
