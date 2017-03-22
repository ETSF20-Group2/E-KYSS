package ekyss.model;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *  Denna klass sköter kopplingen till databasen.
 */
public class Database {

    private static Database db;
    private static String url;
    private static String user;
    private static String password;
    private static Connection conn;

    private Database() {
        url = DatabaseCredentials.getUrl();
        user = DatabaseCredentials.getUser();
        password = DatabaseCredentials.getPass();
    }

    /**
     * Hämtar en instans av databasen.
     * @return Databasen.
     */
    public static Database getInstance() {
        if (db == null) {
            db = new Database();
        }
        return db;
    }

    private void open() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Kopplar ifrån databasen.
     */
    public void close() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returnerar anslutningen till databasen.
     * @return Anslutningen till databasen.
     */
    public Connection getConnection() {
        if (conn == null) {
            open();
        }
        return conn;
    }

}
