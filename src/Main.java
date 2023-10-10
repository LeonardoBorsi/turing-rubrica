import javax.swing.*;
import classes.Auth;
import classes.Database;
import ui.AuthFrame;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        Auth auth = new Auth(db);

        SwingUtilities.invokeLater(() -> {
            new AuthFrame(auth, db).setVisible(true);
        });
    }
}