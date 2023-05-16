package game.ui;

import ui.HighScore;

import javax.swing.*;
import java.awt.*;

public class EndGameFrame extends JDialog
{
    private String userName;
    private  boolean okClicked; // todo may be useful to exit the whole game
    public EndGameFrame(int score)
    {
        okClicked = false;

        setTitle("End Game");
        setModal(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());

        JPanel textPanel = new JPanel();

        JLabel userLabel = new JLabel("Username: ");
        userLabel.setBackground(Color.BLACK);
        textPanel.add(userLabel);

        JTextField userTextField = new JTextField(10);
        userTextField.setText("New user");
        textPanel.add(userTextField);

        JLabel scoreLabel = new JLabel("Your score: ");
        scoreLabel.setBackground(Color.BLACK);
        textPanel.add(scoreLabel);

        JLabel userScoreLabel = new JLabel(score + "");
        userScoreLabel.setBackground(Color.BLACK);
        textPanel.add(userScoreLabel);

        outerPanel.add(textPanel, BorderLayout.CENTER);
        add(outerPanel);

        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("save");
        saveButton.setBackground(Color.YELLOW);
        buttonPanel.add(saveButton);

        saveButton.addActionListener(event -> {
            try {

                okClicked = true;
                setUserName(userTextField.getText().trim());
                HighScore highScore = new HighScore();
                highScore.addUserScore(this.userName, score);

            } catch (Exception e)
            {
                e.printStackTrace();
            }
            dispose();
        });

        JButton exitButton = new JButton("exit");
        exitButton.setBackground(Color.YELLOW);
        buttonPanel.add(exitButton);

        exitButton.addActionListener(event -> dispose());

        outerPanel.add(buttonPanel, BorderLayout.SOUTH);

        //todo figure out the size
        pack();
        setLocationRelativeTo(null);
    }

    private void setUserName(String userName)
    {
        this.userName = userName;
    }
}
