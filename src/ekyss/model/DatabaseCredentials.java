package ekyss.model;

/**
 *  Denna klass innehåller uppgifter för att komma åt databasen.
 */
public class DatabaseCredentials {

    private static String url = "jdbc:mysql://vm23.cs.lth.se/pusp1702hbg";
    private static String user = "pusp1702hbg";
    private static String pass = "er74a0et";

    /**
     * Returnerar JDBC-addressen till databasen.
     * @return Sträng innehållandes JDBC-addressen.
     */
    static String getUrl() {
        return url;
    }

    /**
     * Returnerar användarnamnet till databasen.
     * @return Sträng innehållandes användarnamnet till databasen.
     */
    static String getUser() {
        return user;
    }

    /**
     * Returnerar lösenordet till databasen.
     * @return Sträng innehållandes lösenordet till databasen.
     */
    static String getPass() {
        return pass;
    }
}
