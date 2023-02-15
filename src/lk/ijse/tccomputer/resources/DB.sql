SELECT Employee.empID,Employee.name,Employee.mail,Employee.contact,EmpSalaryDetail.salary FROM Employee INNER JOIN EmpSalaryDetail ON Employee.empID = EmpSalaryDetail.empID WHERE EmpSalaryDetail.salary LIKE '%4%'

select *from orders

select COUNT(orderID)from Orders where date='2022-11-30'

SELECT incomeID,date,month,year,SUM(total) AS total FROM Income WHERE year=2022 && month=11 GROUP BY date ORDER BY date ASC

SELECT * FROM Income GROUP BY year

SELECT id FROM Customer ORDER BY id DESC LIMIT 1

SELECT empID FROM Employee ORDER BY empID DESC LIMIT 1

select * from income WHERE year=2023 && month=1

SELECT * FROM Income WHERE year=2023 && month=1  ORDER BY incomeID ASC

UPDATE User SET password="Password" WHERE userID="Admin"

select*from user