package game.ui;

import com.sun.source.tree.NewClassTree;

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

        Font font = new Font("SansSerif", Font.BOLD, 30);
        label.setFont(font);
        label.setForeground(Color.DARK_GRAY);

        Button button = new Button("THANKS");
        add(label, BorderLayout.CENTER);
        add(button, BorderLayout.PAGE_END);

//        setSize(300, 100);
        pack();
        setLocationRelativeTo(null);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EndGameFrame endGameFrame = new EndGameFrame(score);
                dispose();
            }
        });

    }
}
