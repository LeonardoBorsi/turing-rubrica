package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Auth {
    private final Database db;
    public Auth(Database db) {
        this.db = db;
    }

    public User tryLogin(String username, String password) {
        User u = null;
        try {
            String sql = "SELECT * FROM user WHERE username=? AND password=?";
            PreparedStatement statement = db.connect().prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                u = new User(id, username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.disconnect();
        }
        return u;
    }

    public User register(String username, String password) {
        try {
            String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
            PreparedStatement statement = db.connect().prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.disconnect();
        }
        return tryLogin(username, password);
    }
}
