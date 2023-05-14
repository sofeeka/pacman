import javax.swing.*;
import java.awt.*;

public class EndGameFrame extends JDialog
{
    private String userName;
    private  boolean okClicked; // todo may be useful to exit the whole game
    EndGameFrame(int score)
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

        JButton okButton = new JButton("OK");
        okButton.setBackground(Color.YELLOW);
        buttonPanel.add(okButton);

        okButton.addActionListener(event -> {
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

        JButton cancelButton = new JButton("cancel");
        cancelButton.setBackground(Color.YELLOW);
        buttonPanel.add(cancelButton);

        cancelButton.addActionListener(event -> dispose());

        outerPanel.add(buttonPanel, BorderLayout.SOUTH);

        //todo figure out the size
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void setUserName(String userName)
    {
        this.userName = userName;
    }
}
