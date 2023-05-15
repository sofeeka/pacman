import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinningFrame extends JFrame
{
    WinningFrame(int score)
    {
        setTitle("Winning Frame");

        JLabel label = new JLabel("Congratulations!");

        Button button = new Button("EXIT");
        add(label, BorderLayout.NORTH);
        add(button, BorderLayout.SOUTH);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EndGameFrame endGameFrame = new EndGameFrame(score);
            }
        });
    }
}
