import javax.swing.*;

public class Pacman {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
        {
            MenuFrame menuFrame = new MenuFrame();
            menuFrame.setLocationRelativeTo(null);
            menuFrame.setVisible(true);
        });
    }
}
