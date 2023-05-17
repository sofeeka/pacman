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
        setBackground(Color.BLACK);

        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());

        JPanel questionContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel questionLabel = new JLabel("Do you want to save your score?");
        questionContainer.add(questionLabel, BorderLayout.CENTER);

        outerPanel.add(questionContainer, BorderLayout.NORTH);

        JPanel textPanel = new JPanel();

        JLabel userLabel = new JLabel("Username: ");
        textPanel.add(userLabel);

        JTextField userTextField = new JTextField(10);
        userTextField.setText("New user");
        textPanel.add(userTextField);

        JLabel scoreLabel = new JLabel("Your score: ");
        textPanel.add(scoreLabel);

        JLabel userScoreLabel = new JLabel(score + "");
        textPanel.add(userScoreLabel);

        outerPanel.add(textPanel, BorderLayout.CENTER);
        add(outerPanel);

        JPanel buttonPanel = new JPanel();

        JButton yesButton = new JButton("YES");
        yesButton.setBackground(Color.YELLOW);
        buttonPanel.add(yesButton);

        yesButton.addActionListener(event -> {
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

        JButton noButton = new JButton("NO");
        noButton.setBackground(Color.YELLOW);
        buttonPanel.add(noButton);

        noButton.addActionListener(event -> dispose());

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
