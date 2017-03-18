
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
CREATE TABLE MemberOf (
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
d_11 Integer DEFAULT 0, i_11 Integer DEFAULT 0, f_11 Integer DEFAULT 0, r_11 Integer DEFAULT 0, t_11 Integer DEFAULT 0,
d_12 Integer DEFAULT 0, i_12 Integer DEFAULT 0, f_12 Integer DEFAULT 0, r_12 Integer DEFAULT 0, t_12 Integer DEFAULT 0,
d_13 Integer DEFAULT 0, i_13 Integer DEFAULT 0, f_13 Integer DEFAULT 0, r_13 Integer DEFAULT 0, t_13 Integer DEFAULT 0,
d_14 Integer DEFAULT 0, i_14 Integer DEFAULT 0, f_14 Integer DEFAULT 0, r_14 Integer DEFAULT 0, t_14 Integer DEFAULT 0, 
d_15 Integer DEFAULT 0, i_15 Integer DEFAULT 0, f_15 Integer DEFAULT 0, r_15 Integer DEFAULT 0, t_15 Integer DEFAULT 0,
d_16 Integer DEFAULT 0, i_16 Integer DEFAULT 0, f_16 Integer DEFAULT 0, r_16 Integer DEFAULT 0, t_16 Integer DEFAULT 0, 
d_17 Integer DEFAULT 0, i_17 Integer DEFAULT 0, f_17 Integer DEFAULT 0, r_17 Integer DEFAULT 0, t_17 Integer DEFAULT 0,
d_18 Integer DEFAULT 0, i_18 Integer DEFAULT 0, f_18 Integer DEFAULT 0, r_18 Integer DEFAULT 0, t_18 Integer DEFAULT 0,
d_19 Integer DEFAULT 0, i_19 Integer DEFAULT 0, f_19 Integer DEFAULT 0, r_19 Integer DEFAULT 0, t_19 Integer DEFAULT 0,
t_21 Integer DEFAULT 0,
t_22 Integer DEFAULT 0,
t_23 Integer DEFAULT 0,
t_30 Integer DEFAULT 0,
t_41 Integer DEFAULT 0,
t_42 Integer DEFAULT 0,
t_43 Integer DEFAULT 0,
t_44 Integer DEFAULT 0,
t_100 Integer DEFAULT 0,
t_d Integer DEFAULT 0, t_i Integer DEFAULT 0, t_f Integer DEFAULT 0, t_r Integer DEFAULT 0,
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
SET @s := CONCAT('UPDATE temp_t SET d_', i, ' := (SELECT sum(d_', i, ')', var, '),'
' i_', i, ' := (SELECT sum(i_', i,')', var, '),'
' f_', i, ' := (SELECT sum(f_', i,')', var, '),'
' r_', i, ' := (SELECT sum(r_', i,')', var, '),'
' t_', i, ' := (SELECT sum(t_', i,')', var, ')');
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
SET @s := CONCAT('UPDATE temp_t SET t_', i, ' := (SELECT sum(t_', i, ')', var, ')');
PREPARE stmt FROM @s;
EXECUTE stmt;
END WHILE;

-- Uppdaterar total
SET @s := CONCAT('UPDATE temp_t SET total := (SELECT sum(total)', var, ')');
PREPARE stmt FROM @s;
EXECUTE stmt;

-- Uppdaterar subaktivitetarnas totala.
SET @s := CONCAT('UPDATE temp_t SET t_d := (SELECT sum(t_d)', var, '),'
' ', 't_i := (SELECT sum(t_i)', var, '),'
' ', 't_f := (SELECT sum(t_f)', var, '),'
' ', 't_r := (SELECT sum(t_r)', var, ')');
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
SET NEW.role = (select role from MemberOf where groupName = NEW.groupName AND member = NEW.user);
SET NEW.t_11 = NEW.d_11 + NEW.i_11 + NEW.f_11 + NEW.r_11;
SET NEW.t_12 = NEW.d_12 + NEW.i_12 + NEW.f_12 + NEW.r_12;
SET NEW.t_13 = NEW.d_13 + NEW.i_13 + NEW.f_13 + NEW.r_13;
SET NEW.t_14 = NEW.d_14 + NEW.i_14 + NEW.f_14 + NEW.r_14;
SET NEW.t_15 = NEW.d_15 + NEW.i_15 + NEW.f_15 + NEW.r_15;
SET NEW.t_16 = NEW.d_16 + NEW.i_16 + NEW.f_16 + NEW.r_16;
SET NEW.t_17 = NEW.d_17 + NEW.i_17 + NEW.f_17 + NEW.r_17;
SET NEW.t_18 = NEW.d_18 + NEW.i_18 + NEW.f_18 + NEW.r_18;
SET NEW.t_19 = NEW.d_19 + NEW.i_19 + NEW.f_19 + NEW.r_19;
SET NEW.t_d = NEW.d_11 + NEW.d_12 + NEW.d_13 + NEW.d_14 + NEW.d_15 + NEW.d_16 + NEW.d_17 + NEW.d_18 + NEW.d_19;
SET NEW.t_i = NEW.i_11 + NEW.i_12 + NEW.i_13 + NEW.i_14 + NEW.i_15 + NEW.i_16 + NEW.i_17 + NEW.i_18 + NEW.i_19;
SET NEW.t_f = NEW.f_11 + NEW.f_12 + NEW.f_13 + NEW.f_14 + NEW.f_15 + NEW.f_16 + NEW.f_17 + NEW.f_18 + NEW.f_19;
SET NEW.t_r = NEW.r_11 + NEW.r_12 + NEW.r_13 + NEW.r_14 + NEW.r_15 + NEW.r_16 + NEW.r_17 + NEW.r_18 + NEW.r_19;
SET NEW.total = NEW.t_d + NEW.t_i + NEW.t_f + NEW.t_r + NEW.t_21 + NEW.t_22 + NEW.t_23 + NEW.t_30 + NEW.t_41 + NEW.t_42 + NEW.t_43 + NEW.t_44 + NEW.t_100;
END$$
DELIMITER ;



-- Trigger som updaterar _t för aktiviteterna med subaktiviteter (11-19) och total när ny tidrapport läggs in. Lägger även till rätt roll.
DROP TRIGGER IF EXISTS updateTotInsrt;
DELIMITER $$
CREATE TRIGGER updateTotInsrt BEFORE INSERT ON TimeReports
FOR EACH ROW
BEGIN
SET NEW.date = NOW();
SET NEW.role = (select role from MemberOf where groupName = NEW.groupName AND member = NEW.user);
SET NEW.t_11 = NEW.d_11 + NEW.i_11 + NEW.f_11 + NEW.r_11;
SET NEW.t_12 = NEW.d_12 + NEW.i_12 + NEW.f_12 + NEW.r_12;
SET NEW.t_13 = NEW.d_13 + NEW.i_13 + NEW.f_13 + NEW.r_13;
SET NEW.t_14 = NEW.d_14 + NEW.i_14 + NEW.f_14 + NEW.r_14;
SET NEW.t_15 = NEW.d_15 + NEW.i_15 + NEW.f_15 + NEW.r_15;
SET NEW.t_16 = NEW.d_16 + NEW.i_16 + NEW.f_16 + NEW.r_16;
SET NEW.t_17 = NEW.d_17 + NEW.i_17 + NEW.f_17 + NEW.r_17;
SET NEW.t_18 = NEW.d_18 + NEW.i_18 + NEW.f_18 + NEW.r_18;
SET NEW.t_19 = NEW.d_19 + NEW.i_19 + NEW.f_19 + NEW.r_19;
SET NEW.t_d = NEW.d_11 + NEW.d_12 + NEW.d_13 + NEW.d_14 + NEW.d_15 + NEW.d_16 + NEW.d_17 + NEW.d_18 + NEW.d_19;
SET NEW.t_i = NEW.i_11 + NEW.i_12 + NEW.i_13 + NEW.i_14 + NEW.i_15 + NEW.i_16 + NEW.i_17 + NEW.i_18 + NEW.i_19;
SET NEW.t_f = NEW.f_11 + NEW.f_12 + NEW.f_13 + NEW.f_14 + NEW.f_15 + NEW.f_16 + NEW.f_17 + NEW.f_18 + NEW.f_19;
SET NEW.t_r = NEW.r_11 + NEW.r_12 + NEW.r_13 + NEW.r_14 + NEW.r_15 + NEW.r_16 + NEW.r_17 + NEW.r_18 + NEW.r_19;
SET NEW.total = NEW.t_d + NEW.t_i + NEW.t_f + NEW.t_r + NEW.t_21 + NEW.t_22 + NEW.t_23 + NEW.t_30 + NEW.t_41 + NEW.t_42 + NEW.t_43 + NEW.t_44 + NEW.t_100;
END$$
DELIMITER ;

--------------------------------------------------------------------------------

----- Insert admin -------------------------------------------------------------
INSERT INTO Users(userName, email, password) VALUES('admin', '', 'adminp');



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

INSERT INTO MemberOf values('1', 'Johannes', 'PG'),
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

