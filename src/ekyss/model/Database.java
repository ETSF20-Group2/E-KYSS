package ekyss.model;

import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.Connection;

import com.mysql.jdbc.Connection;

public class Database {

    private static Database db;
    private static String url;
    private static String user;
    private static String password;
    private static Connection conn;

    private Database() {
        url = Do_not_send_up_this_to_GitHub.getUrl();
        user = Do_not_send_up_this_to_GitHub.getUser();
        password = Do_not_send_up_this_to_GitHub.getPass();
    }

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

    public Connection getConnection() {
        if (conn == null) {
            open();
        }
        return conn;
    }

}
