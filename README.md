# E-KYSS - Webbaserat tidrapporteringssystem

## Bibliotek
* [commons-beanutils-1.9.3.jar](http://commons.apache.org/proper/commons-beanutils/download_beanutils.cgi) Annvänds i klassen __BeanUtilities__ för att mata _JavaBeans_ via _HttpServletRequest_-anrop.
* [commons-collections-3.2.2.jar]() Används av packetet _commons_beanutils-1.9.3.jar_ för att hantera inmatning av parametrar till _JavaBeans_.
* [commons-logging-1.2.jar](https://commons.apache.org/proper/commons-logging/download_logging.cgi) Används av packetet _commons-beanutils-1.9.3.jar_ för att hantera _exceptions_.
* [mysql-connector-java-5.1.41-bin.jar](https://dev.mysql.com/downloads/connector/j/) Används i klassen __Database__ för att upprätta förbindelse med _MySQL_-server.
* [jstl-1.2.jar](https://jstl.java.net/) Används för __Java Tag Language__, för att skapa block-systemet för _JSP_.

## MySQL

### Skapa användaren och tomt databas
```
> mysql -u root -p
> CREATE DATABASE ekyss CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
> CREATE USER 'ekyss'@'localhost' IDENTIFIED BY '';
> GRANT ALL PRIVILEGES ON ekyss.localhost TO 'ekyss'@'localhost';
> FLUSH PRIVILEGES;
```