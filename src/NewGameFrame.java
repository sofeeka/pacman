import javax.swing.*;
import java.awt.*;

public class NewGameFrame extends JFrame {
    private MenuFrame menuFrame;
    private int dimX;
    private int dimY;

    private void setDimensions(int x, int y)
    {
        dimX = x;
        dimY = y;
        System.out.println(x + " <- x, y -> " + y);
    }
    NewGameFrame(MenuFrame menuFrame)
    {
        this.menuFrame = menuFrame;
        setTitle("New Game");

        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());

        JPanel textPanel = new JPanel();

        JLabel labelX = new JLabel("Enter width");
        labelX.setBackground(Color.BLACK);
        textPanel.add(labelX);
        JTextField textFieldX = new JTextField(5);
        textPanel.add(textFieldX);

        JTextField textFieldY = new JTextField(5);
        JLabel labelY = new JLabel("Enter height");
        labelY.setBackground(Color.BLACK);
        textPanel.add(labelY);
        textPanel.add(textFieldY);

        outerPanel.add(textPanel, BorderLayout.CENTER);
        add(outerPanel);

        JPanel buttonPanel = new JPanel();

        JButton okButton = new JButton("OK");
        okButton.setBackground(Color.YELLOW);
        buttonPanel.add(okButton);

//todo: розкоментити
/*
        okButton.addActionListener(actionEvent -> {
            try {
                int dimX = Integer.parseInt(textFieldX.getText().trim());
                int dimY = Integer.parseInt(textFieldY.getText().trim());
                setDimensions(dimX, dimY);
            } catch (Exception e)
            { }
            dispose();
        });
*/
        setDimensions(15,15);

        JButton cancelButton = new JButton("cancel");
        cancelButton.setBackground(Color.YELLOW);
        buttonPanel.add(cancelButton);

        cancelButton.addActionListener(actionEvent -> {
            dispose();
            menuFrame.setVisible(true);
        });

        //todo add gridBagLayout
        outerPanel.add(buttonPanel, BorderLayout.SOUTH);

        //todo figure out the size
        pack();
        dispose();
    }
}