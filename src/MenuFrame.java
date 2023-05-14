import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuFrame extends JFrame {
    private JButton newGameButton;
    private JButton highScoresButton;
    private JButton exitButton;
    private JLabel imageLabel;
    public MenuFrame()
    {
        initMenuFrame();
    }

    private void handleNewGameButton()
    {
        NewGameFrame frame = new NewGameFrame();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        if(frame.wasOkClicked())
        {
            Game game = new Game();
            game.startNewGame(frame.getDimX(), frame.getDimY());
        }
    }
    private void handleHighScoresButton()
    {
        // todo: figure out how to use JList
    }
    private void handleExitButton()
    {
        dispose();
    }

    private void initMenuFrame()
    {

        setTitle("Pacman Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon introImage = new ImageIcon("images\\pacman_intro.png");
        Image image = introImage.getImage().getScaledInstance(-1,-1, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(image);
        imageLabel = new JLabel(scaledImageIcon);

        JPanel innerPanel = new JPanel(new GridBagLayout());
        innerPanel.setBackground(Color.BLACK);
        innerPanel.add(imageLabel);

        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setLayout(new GridBagLayout());
        outerPanel.setBackground(Color.BLACK);

        GridBagConstraints imageConstraints = new GridBagConstraints();
        imageConstraints.gridx = 0;
        imageConstraints.gridy = 0;
        imageConstraints.gridwidth = GridBagConstraints.REMAINDER;
        imageConstraints.weighty = 1.0;
        imageConstraints.weightx = 1.0;
        imageConstraints.anchor = GridBagConstraints.CENTER;
        imageConstraints.fill = GridBagConstraints.BOTH;

        outerPanel.add(innerPanel, imageConstraints);

        newGameButton = new JButton("New Game");
        newGameButton.setBackground(Color.YELLOW);

        highScoresButton = new JButton("High Scores");
        highScoresButton.setBackground(Color.YELLOW);

        exitButton = new JButton("Exit");
        exitButton.setBackground(Color.YELLOW);

        GridBagConstraints buttonsConstraints = new GridBagConstraints();
        // insets todo
        buttonsConstraints.anchor = GridBagConstraints.PAGE_START;
        buttonsConstraints.fill = GridBagConstraints.HORIZONTAL;
        buttonsConstraints.insets = new java.awt.Insets(10,10,10,10);

        buttonsConstraints.gridy = 1;
        buttonsConstraints.weightx = 0.33;
        buttonsConstraints.weighty = 0.1;
        outerPanel.add(newGameButton, buttonsConstraints);

        buttonsConstraints.gridx = 1;
        buttonsConstraints.weightx = 0.33;
        buttonsConstraints.weighty = 0.1;
        outerPanel.add(highScoresButton, buttonsConstraints);

        buttonsConstraints.gridx = 2;
        buttonsConstraints.weightx = 0.33;
        buttonsConstraints.weighty = 0.1;
        outerPanel.add(exitButton, buttonsConstraints);

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(Menu.this, "New Game button clicked");
                // todo:
                handleNewGameButton();
            }
        });

        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MenuFrame.this, "High Scores button clicked");
                // todo:
                handleHighScoresButton();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo :
                handleExitButton();
            }
        });

        getContentPane().add(outerPanel);

        setSize(600, 400);

//        pack();
//        setLocationRelativeTo(null);
//        setVisible(true);

    }

}
