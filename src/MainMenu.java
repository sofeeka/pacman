import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainMenu extends JFrame {
    private JButton newGameButton;
    private JButton highScoresButton;
    private JButton exitButton;
    private JLabel imageLabel;
    public MainMenu()
    {
        initMainMenu();
    }

    private void handleNewGameButton()
    {

    }
    private void handleHighScoresButton()
    {
        // todo: figure out how to use JList
    }
    private void handleExitButton()
    {
        //todo : dispose?
        // change exit(0) to something less dangerous
    }

    private void initMainMenu()
    {

        setTitle("Pacman Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create image
        ImageIcon introImage = new ImageIcon("C:\\PJATK\\2 semester\\GUI\\Pacman\\pacman_intro.png");
        Image image = introImage.getImage().getScaledInstance(-1,-1, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(image);
        imageLabel = new JLabel(scaledImageIcon);

        // add image to inner panel
        JPanel innerPanel = new JPanel(new GridBagLayout());
        innerPanel.setBackground(Color.BLACK);
        innerPanel.add(imageLabel);

        // create outer panel
        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setLayout(new GridBagLayout());
        outerPanel.setBackground(Color.BLACK);
        // outer panel constrains
        GridBagConstraints imageConstraints = new GridBagConstraints();
        imageConstraints.gridx = 0;
        imageConstraints.gridy = 0;
        imageConstraints.gridwidth = GridBagConstraints.REMAINDER; // span across all columns
        imageConstraints.weighty = 1.0;
        imageConstraints.weightx = 1.0;
        imageConstraints.anchor = GridBagConstraints.CENTER; // align to the center
        imageConstraints.fill = GridBagConstraints.BOTH; // fill both horizontally and vertically

        // add inner panel to outer panel
        outerPanel.add(innerPanel, imageConstraints);

        // create buttons
        newGameButton = new JButton("New Game");
        highScoresButton = new JButton("High Scores");
        exitButton = new JButton("Exit");
        // yellow background
        newGameButton.setBackground(Color.YELLOW);
        highScoresButton.setBackground(Color.YELLOW);
        exitButton.setBackground(Color.YELLOW);

        // buttons constrains
        GridBagConstraints buttonsConstraints = new GridBagConstraints();
        // insets todo
        buttonsConstraints.anchor = GridBagConstraints.PAGE_START;
        buttonsConstraints.fill = GridBagConstraints.HORIZONTAL;
        buttonsConstraints.insets = new java.awt.Insets(10,10,10,10);

        // new game button
        buttonsConstraints.gridy = 1;
        buttonsConstraints.weightx = 0.33;
        buttonsConstraints.weighty = 0.1;
        outerPanel.add(newGameButton, buttonsConstraints);
        // high score button
        buttonsConstraints.gridx = 1;
        buttonsConstraints.weightx = 0.33;
        buttonsConstraints.weighty = 0.1;
        outerPanel.add(highScoresButton, buttonsConstraints);
        // exit button
        buttonsConstraints.gridx = 2;
        buttonsConstraints.weightx = 0.33;
        buttonsConstraints.weighty = 0.1;
        outerPanel.add(exitButton, buttonsConstraints);

        // add ActionListener to newGameButton
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainMenu.this, "New Game button clicked");
                // todo:
                handleNewGameButton();
            }
        });

        // add ActionListener to highScoresButton
        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainMenu.this, "High Scores button clicked");
                // todo:
                handleHighScoresButton();
            }
        });

        // add ActionListener to exitButton
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo : handleExitButton();
                System.exit(0); // terminate the application
            }
        });

        // add panel to the main frame
        getContentPane().add(outerPanel);

        // set the initial size of the window
        setSize(5000, 4000);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            new MainMenu();
        });
    }
}
