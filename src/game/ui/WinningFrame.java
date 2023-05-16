package game.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinningFrame extends JDialog
{
    public WinningFrame(int score)
    {
        setTitle("Winning Frame");
        setModal( true );

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

        pack();
        setLocationRelativeTo(null);
    }
}
