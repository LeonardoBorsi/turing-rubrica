package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private final String DATABASE_URL = "jdbc:sqlite:./rubrica.db";
    private Connection conn;
    public Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            checkDatabaseCreation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Connection connect() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DATABASE_URL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkDatabaseCreation() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                String createUserTableSql = "CREATE TABLE IF NOT EXISTS user (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT NOT NULL UNIQUE," +
                        "password TEXT NOT NULL" +
                        ")";
                stmt.execute(createUserTableSql);

                String createPersonTableSql = "CREATE TABLE IF NOT EXISTS person (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "surname TEXT NOT NULL," +
                        "address TEXT," +
                        "phone TEXT," +
                        "age INTEGER," +
                        "owner INTEGER," +
                        "FOREIGN KEY(owner) REFERENCES user(id)" +
                        ")";
                stmt.execute(createPersonTableSql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
