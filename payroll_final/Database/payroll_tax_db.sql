drop table Taxtable_Table;

create table Taxtable_Table(
tax_id int ,
tax_Min int,
tax_Max int,
tax_TaxOnLowerLimit int,
tax_TaxOnExcessOverLimit float,
primary key(tax_id)
);

insert into Taxtable_Table(tax_id,tax_Min,tax_Max,tax_TaxOnLowerLimit,tax_TaxOnExcessOverLimit)
values(1,0,250000,0,0),(2,250000,400000,0,.20),(3,400000,800000,30000,.25),(4,800000,2000000,130000,.30),(5,2000000,8000000,490000,.32),(6,8000000,8000001,2410000,.35);

DELIMITER \\
CREATE PROCEDURE allTax()
BEGIN
SELECT tax_Min,tax_Max,tax_TaxOnLowerLimit,tax_TaxOnExcessOverLimit FROM Taxtable_Table;
END \\
DELIMITER ;
call allTax();
drop procedure allTax;

DELIMITER \\
CREATE PROCEDURE allTaxT()
BEGIN
SELECT tax_id,tax_Min,tax_Max,tax_TaxOnLowerLimit,tax_TaxOnExcessOverLimit FROM Taxtable_Table;
END \\
DELIMITER ;
call allTaxT();
drop procedure allTax;

create table sss_table(
	sss_id int primary key auto_increment not null,
	sss_min int not null,
    sss_max double not null,
    sss_ec double not null,
    sss_er double not null,
    sss_ee double not null,
    sss_total double not null
);
 
insert into sss_table (sss_min, sss_max, sss_ec, sss_er, sss_ee, sss_total) 
values 
(0, 2250, 10.00, 170.00, 80, 250.00), 
(2250, 2749.99, 10.00, 210.00, 100, 310.00), 
(2750, 3249.99, 10.00, 250.00, 120, 370.00),
(3250, 3749.99, 10.00, 290.00, 140, 430.00),
(3750, 4249.99, 10.00, 330.00, 160, 490.00),
(4250, 4749.99, 10.00, 370.00, 180, 550.00),
(4750, 5249.99, 10.00, 410.00, 200, 610.00),
(5250, 5749.99, 10.00, 450.00, 220, 670.00 ),
(5750, 6249.99, 10.00, 490.00, 240, 730.00),
(6250, 6749.99, 10.00, 530.00, 260, 790.00),
(6750, 7249.99, 10.00, 570.00, 280, 850.00),
(7250, 7749.99, 10.00, 610.00, 300, 910.00),
(7750, 8249.99, 10.00, 650.00, 320, 970.00 ),
(8250, 8749.99, 10.00, 690.00, 340, 1030.00),
(8750, 9249.99, 10.00, 730.00, 360, 1090.00 ),
(9250, 9749.99, 10.00, 770.00, 380, 1150.00),
(9750, 10249.99, 10.00, 810.00, 400, 1210.00),
(10250, 10749.99, 10.00, 850.00, 420, 1270.00),
(10750, 11249.99, 10.00, 890.00, 440, 1330.00),
(11250, 11749.99, 10.00, 930.00, 460, 1390.00),
(11750, 12249.99, 10.00, 970.00, 480, 1450.00),
(12250, 12749.99, 10.00, 1010.00,	500, 1510.00),
(12750, 13249.99, 10.00, 1050.00, 520, 1570.00),
(13250, 13749.99, 10.00, 1090.00, 540,	1630.00),
(13750, 14249.99, 10.00, 1130.00, 560, 1690.00),
(14250, 14749.99, 10.00, 1170.00, 580,	1750.00),
(14750, 15249.99, 30.00, 1230.00, 600, 1830.00),
(15250, 15749.99, 30.00, 1270.00, 620, 1890.00),
(15750, 16249.99, 30.00, 1310.00, 640, 1950.00),
(16250, 16749.99, 30.00, 1350.00, 660,	2010.00),
(16750, 17249.99, 30.00, 1390.00, 680,	2070.00),
(17250, 17749.99, 30.00, 1430.00, 700,	2130.00),
(17750, 18249.99, 30.00, 1470.00, 720, 2190.00),
(18250, 18749.99, 30.00, 1510.00, 740, 2250.00),
(18750, 19249.99, 30.00, 1550.00, 760, 2310.00),
(19250, 19749.99, 30.00, 1590.00, 780, 2370.00),
(19750,  50000.00, 30.00, 1630.00, 800, 2430.00);

DELIMITER \\
CREATE PROCEDURE allTax_sss()
BEGIN
SELECT sss_min, sss_max, sss_ec, sss_er, sss_ee, sss_total FROM sss_table;
END \\
DELIMITER ;
call allTax_sss();
drop procedure allTax_sss;

DELIMITER \\
CREATE PROCEDURE allTax_sssT()
BEGIN
SELECT sss_id,sss_min, sss_max, sss_ec, sss_er, sss_ee, sss_total FROM sss_table;
END \\
DELIMITER ;
call allTax_sss();
drop procedure allTax_sss;


create table Philhealth_tableV1(
	Philhealth_id int primary key auto_increment not null,
	Philhealth_min float not null,
    Philhealth_max float not null,
    Philhealth_Premiumrate float not null
);
insert into Philhealth_tableV1(Philhealth_min,Philhealth_max,Philhealth_Premiumrate)values
(0,10000,0.0275),(10000.01,49999.99,0.0275),(0,50000,0.0275);

DELIMITER \\
CREATE PROCEDURE allTax_PHILV1()
BEGIN
SELECT Philhealth_min,Philhealth_max,Philhealth_Premiumrate FROM Philhealth_tableV1;
END \\
DELIMITER ;
call allTax_PHILV1();

DELIMITER \\
CREATE PROCEDURE allTax_PHILV1Table()
BEGIN
SELECT Philhealth_id,Philhealth_min,Philhealth_max,Philhealth_Premiumrate FROM Philhealth_tableV1;
END \\
DELIMITER ;
call allTax_PHILV1Table();
drop procedure allTax_PHILV1T;


create table Pagibig_table(
	Pagibig_id int primary key auto_increment not null,
	Pagibig_min float not null,
	Pagibig_max float not null,
    Pagibig_employeeShare float not null,
    Pagibig_employerShare float not null
);
insert into Pagibig_table(Pagibig_min,Pagibig_max,Pagibig_employeeShare,Pagibig_employerShare) values
(0,1500,.01,.02),(1500,0,.02,.02);

DELIMITER \\
CREATE PROCEDURE allTax_PAGibig()
BEGIN
SELECT Pagibig_min,Pagibig_max,Pagibig_employeeShare,Pagibig_employerShare FROM Pagibig_table;
END \\
DELIMITER ;
call allTax_PAGibig();
drop procedure allTax_PAGibig;


DELIMITER \\
CREATE PROCEDURE allTax_PAGibigT()
BEGIN
SELECT Pagibig_id,Pagibig_min,Pagibig_max,Pagibig_employeeShare,Pagibig_employerShare FROM Pagibig_table;
END \\
DELIMITER ;
call allTax_PAGibig();
drop procedure allTax_PAGibig;