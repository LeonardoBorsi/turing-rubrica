package classes;

import javax.swing.*;
import java.awt.*;

public class Utils
{
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static ImageIcon getImageIcon(String path, int width, int height) {
        return new ImageIcon(
                new ImageIcon(Utils.class.getResource(path))
                        .getImage()
                        .getScaledInstance(width, height, Image.SCALE_SMOOTH)
        );
    }
}
