
----------------- Tables -------------------------

-- Table som beskriver användarna. Användarnamnen måste vara unika.
CREATE TABLE Users (
userName varChar(10) NOT NULL,
password varChar(6) NOT NULL,
email varChar(100) NOT NULL,
PRIMARY KEY (userName)
);

-- Table som beskriver en projektgrupp. Det enda den innehåller är ett unikt projektnamn.
CREATE TABLE ProjectGroups (
groupName varChar(100),
PRIMARY KEY (groupName)
);


-- Definerar förhållande mellan användare och de olika projektgrupperna
CREATE TABLE memberOf (
groupName varChar(100),
member varChar(10),
role varChar(10),
UNIQUE (groupName, member), -- = en medlem kan bara ha en roll per grupp
FOREIGN KEY (groupName) references ProjectGroups(groupName) ON UPDATE CASCADE
ON DELETE CASCADE,
FOREIGN KEY (member) REFERENCES Users(userName) ON UPDATE CASCADE ON DELETE CASCADE
);


-- Tabell som beskriver tidrapporterna. Data hämtas enklast med hjälp av procedures och _t för 11-19 och d,i,f,r samt total uppdateras automatiskt vid insättning
-- och uppdatering genom triggers.
DROP TABLE IF EXISTS TimeReports;
CREATE TABLE TimeReports(
user varChar(10),
date DATE NOT NULL,
groupName varChar(100),
week Integer NOT NULL,
role varChar(10),
total Integer DEFAULT 0,
11_d Integer DEFAULT 0, 11_i Integer DEFAULT 0, 11_f Integer DEFAULT 0, 11_r Integer DEFAULT 0, 11_t Integer DEFAULT 0,
12_d Integer DEFAULT 0, 12_i Integer DEFAULT 0, 12_f Integer DEFAULT 0, 12_r Integer DEFAULT 0, 12_t Integer DEFAULT 0,
13_d Integer DEFAULT 0, 13_i Integer DEFAULT 0, 13_f Integer DEFAULT 0, 13_r Integer DEFAULT 0, 13_t Integer DEFAULT 0,
14_d Integer DEFAULT 0, 14_i Integer DEFAULT 0, 14_f Integer DEFAULT 0, 14_r Integer DEFAULT 0, 14_t Integer DEFAULT 0, 
15_d Integer DEFAULT 0, 15_i Integer DEFAULT 0, 15_f Integer DEFAULT 0, 15_r Integer DEFAULT 0, 15_t Integer DEFAULT 0,
16_d Integer DEFAULT 0, 16_i Integer DEFAULT 0, 16_f Integer DEFAULT 0, 16_r Integer DEFAULT 0, 16_t Integer DEFAULT 0, 
17_d Integer DEFAULT 0, 17_i Integer DEFAULT 0, 17_f Integer DEFAULT 0, 17_r Integer DEFAULT 0, 17_t Integer DEFAULT 0,
18_d Integer DEFAULT 0, 18_i Integer DEFAULT 0, 18_f Integer DEFAULT 0, 18_r Integer DEFAULT 0, 18_t Integer DEFAULT 0,
19_d Integer DEFAULT 0, 19_i Integer DEFAULT 0, 19_f Integer DEFAULT 0, 19_r Integer DEFAULT 0, 19_t Integer DEFAULT 0,
21_t Integer DEFAULT 0,
22_t Integer DEFAULT 0,
23_t Integer DEFAULT 0,
30_t Integer DEFAULT 0,
41_t Integer DEFAULT 0,
42_t Integer DEFAULT 0,
43_t Integer DEFAULT 0,
44_t Integer DEFAULT 0,
100_t Integer DEFAULT 0,
d_t Integer DEFAULT 0, i_t Integer DEFAULT 0, f_t Integer DEFAULT 0, r_t Integer DEFAULT 0,
signed boolean DEFAULT FALSE,
FOREIGN KEY (user) REFERENCES Users(userName) ON UPDATE CASCADE ON DELETE CASCADE, -- !!!Hela tidsrapporten tas borst. kanske bättre med SET NULL
FOREIGN KEY (groupName) REFERENCES ProjectGroups(groupName) ON UPDATE CASCADE ON DELETE CASCADE, -- !!! SOM OVAN
PRIMARY KEY (user, week, groupName) -- = Varje användare kan bara tidsrapportera en gång i veckan
);

-----------------------------------------------------------------

----------- Procedure som hämtar tidrapport beroende på inparametrar -----------
DROP PROCEDURE IF EXISTS sumHelp;
DELIMITER $$
CREATE PROCEDURE sumHelp(IN groupName varChar(100), IN user varChar(25), IN role varChar(25), IN week Integer)
BEGIN
DECLARE i Integer;
DECLARE var varChar(255);
-- Skapar en varChar med villkor att använda i prepared statement. Byggs på efter vilka parametrar som int är tomma.
SET var := CONCAT(' FROM TimeReports WHERE groupName = \'',groupName,'\'');
IF user <> '' THEN set var := CONCAT(var, ' AND user = \'', user, '\''); END IF;
IF role <> '' THEN set var := CONCAT(var, ' AND role = \'', role, '\''); END IF;
IF week <> 0 THEN set var := CONCAT(var, ' AND week = \'', week, '\''); END IF;
-- SELECT var;

-- Skapar en temporär tabell lik TimeReports där orelevanta kolumner är borttagna.
DROP TABLE IF EXISTS temp_t;
CREATE TEMPORARY TABLE temp_t LIKE TimeReports;
ALTER TABLE temp_t DROP COLUMN user, DROP COLUMN signed, DROP COLUMN date, DROP COLUMN groupName, DROP COLUMN week, DROP COLUMN role;
INSERT INTO temp_t values();
SET i := 10;

-- WHILE-loop som går igenom 11-19
WHILE i <> 19 DO
SET i := i+1;
-- Bygger en varChar som ska användas i prepared statement. Kommer sätta in alla värden som går för 11-19. var kan vara olika beroende på parametrar.
SET @s := CONCAT('UPDATE temp_t SET ', i, '_d := (SELECT sum(', i,'_d)', var, '),'
' ', i, '_i := (SELECT sum(', i,'_i)', var, '),'
' ', i, '_f := (SELECT sum(', i,'_f)', var, '),'
' ', i, '_r := (SELECT sum(', i,'_r)', var, '),'
' ', i, '_t := (SELECT sum(', i,'_t)', var, ')');
PREPARE stmt FROM @s;
EXECUTE stmt;
END WHILE;

-- WHILE-loop som går igenom resterande aktiviteter
SET i := 20;
WHILE i <> 100 DO
SET i := i+1;
IF i = 24 THEN SET i = 30; -- Om i = 24 så ska den bli 30
ELSEIF i = 31 THEN SET i = 41; -- Om i = 31 ska den bli 41
ELSEIF i = 45 THEN SET i = 100; -- Om i = 45 ska den bli 100
END IF;
-- Bygger prepared statement
SET @s := CONCAT('UPDATE temp_t SET ', i, '_t := (SELECT sum(', i, '_t)', var, ')');
PREPARE stmt FROM @s;
EXECUTE stmt;
END WHILE;

-- Uppdaterar total
SET @s := CONCAT('UPDATE temp_t SET total := (SELECT sum(total)', var, ')');
PREPARE stmt FROM @s;
EXECUTE stmt;

-- Uppdaterar subaktivitetarnas totala.
SET @s := CONCAT('UPDATE temp_t SET d_t := (SELECT sum(d_t)', var, '),'
' ', 'i_t := (SELECT sum(i_t)', var, '),'
' ', 'f_t := (SELECT sum(f_t)', var, '),'
' ', 'r_t := (SELECT sum(r_t)', var, ')');
PREPARE stmt from @s;
EXECUTE stmt;

SELECT * FROM temp_t;

END$$
DELIMITER ;

--------------------------------------------------------------------------------

-------- Triggers --------------------------------------------------------------
-- Trigger som updaterar _t för aktiviteterna med subaktiviteter (11-19) och total när ett värde uppdateras. Lägger även till rätt roll.
DROP TRIGGER IF EXISTS updateTotUpdt;
DELIMITER $$
CREATE TRIGGER updateTotUpdt BEFORE UPDATE ON TimeReports
FOR EACH ROW
BEGIN
SET NEW.date = NOW();
SET NEW.role = (select role from memberOf where groupName = NEW.groupName AND userName = NEW.user);
SET NEW.11_t = NEW.11_d + NEW.11_i + NEW.11_f + NEW.11_r;
SET NEW.12_t = NEW.12_d + NEW.12_i + NEW.12_f + NEW.12_r;
SET NEW.13_t = NEW.13_d + NEW.13_i + NEW.13_f + NEW.13_r;
SET NEW.14_t = NEW.14_d + NEW.14_i + NEW.14_f + NEW.14_r;
SET NEW.15_t = NEW.15_d + NEW.15_i + NEW.15_f + NEW.15_r;
SET NEW.16_t = NEW.16_d + NEW.16_i + NEW.16_f + NEW.16_r;
SET NEW.17_t = NEW.17_d + NEW.17_i + NEW.17_f + NEW.17_r;
SET NEW.18_t = NEW.18_d + NEW.18_i + NEW.18_f + NEW.18_r;
SET NEW.19_t = NEW.19_d + NEW.19_i + NEW.19_f + NEW.19_r;
SET NEW.d_t = NEW.11_d + NEW.12_d + NEW.13_d + NEW.14_d + NEW.15_d + NEW.16_d + NEW.17_d + NEW.18_d + NEW.19_d;
SET NEW.i_t = NEW.11_i + NEW.12_i + NEW.13_i + NEW.14_i + NEW.15_i + NEW.16_i + NEW.17_i + NEW.18_i + NEW.19_i;
SET NEW.f_t = NEW.11_f + NEW.12_f + NEW.13_f + NEW.14_f + NEW.15_f + NEW.16_f + NEW.17_f + NEW.18_f + NEW.19_f;
SET NEW.r_t = NEW.11_r + NEW.12_r + NEW.13_r + NEW.14_r + NEW.15_r + NEW.16_r + NEW.17_r + NEW.18_r + NEW.19_r;
SET NEW.total = NEW.d_t + NEW.i_t + NEW.f_t + NEW.r_t + NEW.21_t + NEW.22_t + NEW.23_t + NEW.30_t + NEW.41_t + NEW.42_t + NEW.43_t + NEW.44_t + NEW.100_t;
END$$
DELIMITER ;



-- Trigger som updaterar _t för aktiviteterna med subaktiviteter (11-19) och total när ny tidrapport läggs in. Lägger även till rätt roll.
DROP TRIGGER IF EXISTS updateTotInsrt;
DELIMITER $$
CREATE TRIGGER updateTotInsrt BEFORE INSERT ON TimeReports
FOR EACH ROW
BEGIN
SET NEW.date = NOW();
SET NEW.role = (select role from memberOf where groupName = NEW.groupName AND userName = NEW.user);
SET NEW.11_t = NEW.11_d + NEW.11_i + NEW.11_f + NEW.11_r;
SET NEW.12_t = NEW.12_d + NEW.12_i + NEW.12_f + NEW.12_r;
SET NEW.13_t = NEW.13_d + NEW.13_i + NEW.13_f + NEW.13_r;
SET NEW.14_t = NEW.14_d + NEW.14_i + NEW.14_f + NEW.14_r;
SET NEW.15_t = NEW.15_d + NEW.15_i + NEW.15_f + NEW.15_r;
SET NEW.16_t = NEW.16_d + NEW.16_i + NEW.16_f + NEW.16_r;
SET NEW.17_t = NEW.17_d + NEW.17_i + NEW.17_f + NEW.17_r;
SET NEW.18_t = NEW.18_d + NEW.18_i + NEW.18_f + NEW.18_r;
SET NEW.19_t = NEW.19_d + NEW.19_i + NEW.19_f + NEW.19_r;
SET NEW.d_t = NEW.11_d + NEW.12_d + NEW.13_d + NEW.14_d + NEW.15_d + NEW.16_d + NEW.17_d + NEW.18_d + NEW.19_d;
SET NEW.i_t = NEW.11_i + NEW.12_i + NEW.13_i + NEW.14_i + NEW.15_i + NEW.16_i + NEW.17_i + NEW.18_i + NEW.19_i;
SET NEW.f_t = NEW.11_f + NEW.12_f + NEW.13_f + NEW.14_f + NEW.15_f + NEW.16_f + NEW.17_f + NEW.18_f + NEW.19_f;
SET NEW.r_t = NEW.11_r + NEW.12_r + NEW.13_r + NEW.14_r + NEW.15_r + NEW.16_r + NEW.17_r + NEW.18_r + NEW.19_r;
SET NEW.total = NEW.d_t + NEW.i_t + NEW.f_t + NEW.r_t + NEW.21_t + NEW.22_t + NEW.23_t + NEW.30_t + NEW.41_t + NEW.42_t + NEW.43_t + NEW.44_t + NEW.100_t;
END$$
DELIMITER ;

--------------------------------------------------------------------------------

----- Insert admin -------------------------------------------------------------
INSERT INTO Users(userName, emial, password) VALUES('admin', '', 'adminp');



----- Inserts for testing (will be removed) -------------------------------------

INSERT INTO Users values('Johannes', '1234', '1234...'),
('Elihn', '1234', '1234....'),
('Kalle', '1234', '1234....'),
('Pelle', '1234', '1234....'),
('Jonas', '1234', '1234....'),
('Kasper', '1234', '1234....'),
('Jesper', '1234', '1234....'),
('Jonathan', '1234', '1234....'),
('Joe', '1234', '1234....'),
('Martin', '1234', '1234....');


INSERT INTO ProjectGroups values('1'), ('2'), ('3');

INSERT INTO memberOf values('1', 'Johannes', 'PG'),
('2', 'Elihn', 'PG'),
('3', 'Kalle', 'PG'),
('1', 'Jonas', 'SG'),
('2', 'Kasper', 'SG'),
('3', 'Jesper', 'SG'),
('1', 'Jonathan', 'UG'),
('2', 'Joe', 'UG'),
('3', 'Martin', 'UG'),
('1', 'Kasper', 'TG'),
('2', 'Jesper', 'TG'),
('3', 'Jonathan', 'TG');

INSERT INTO TimeReports(user, date, groupName, week, 11_d, 11_i, 11_f, 11_r, 12_d, 12_i, 12_f, 12_r, 13_d, 13_i, 13_f, 13_r, 14_d, 14_i, 14_f, 14_r, 15_d, 15_i, 15_f, 15_r, 16_d, 16_i, 16_f, 16_r, 17_d, 17_i, 17_f, 17_r, 18_d, 18_i, 18_f, 18_r,19_d, 19_i, 19_f, 19_r, 21_t, 22_t, 23_t, 30_t, 41_t, 42_t, 43_t, 44_t, 100_t)
VALUES('Johannes', '2017-11-11', '1', 1, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
('Elihn', '2017-11-11', '2', 1, 2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
('Johannes', '2017-11-11', '1', 2, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
('Elihn', '2017-11-11', '2', 2, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
('Jonas', '2017-11-11', '1', 1, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
('Kasper', '2017-11-11', '2', 1, 2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
('Jonathan', '2017-11-11', '1', 2, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
('Jesper', '2017-11-11', '2', 2, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
('Pelle', '2017-11-11', '2', 2, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)
;

