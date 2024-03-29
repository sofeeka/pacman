package gui;

import javax.swing.*;
import java.awt.*;

public class NewGameFrame extends JDialog {
    private int dimX;
    private int dimY;

    private boolean okClicked;
    private boolean properSized;

    private void setDimensions(int x, int y)
    {
        dimX = x;
        dimY = y;
    }

    public int getDimX()
    {
        return dimX;
    }
    public int getDimY()
    {
        return dimY;
    }

    public boolean isProperSized() {
        return properSized;
    }

    public boolean wasOkClicked()
    {
        return okClicked;
    }
    NewGameFrame()
    {
        setTitle("New Game");
        setModal(true);
        dimX = 15;
        dimY = 15;
        okClicked = false;
        properSized = false;

        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());

        JPanel textPanel = new JPanel();

        JLabel labelX = new JLabel("Enter width");
        labelX.setBackground(Color.BLACK);
        textPanel.add(labelX);

        JTextField textFieldX = new JTextField(5);
        textFieldX.setText(dimX + "");
        textPanel.add(textFieldX);

        JLabel labelY = new JLabel("Enter height");
        labelY.setBackground(Color.BLACK);
        textPanel.add(labelY);

        JTextField textFieldY = new JTextField(5);
        textFieldY.setText(dimY + "");
        textPanel.add(textFieldY);

        outerPanel.add(textPanel, BorderLayout.CENTER);
        add(outerPanel);

        JPanel buttonPanel = new JPanel();

        JButton okButton = new JButton("OK");
        okButton.setBackground(Color.YELLOW);
        buttonPanel.add(okButton);

        okButton.addActionListener(event -> {
            try {
                okClicked = true;
                int dimX = Integer.parseInt(textFieldX.getText().trim());
                int dimY = Integer.parseInt(textFieldY.getText().trim());

                if (dimX < 10 || dimX > 100 || dimY < 10 || dimY > 100)
                    throw new sizeException(dimX, dimY);
                else
                {
                    setDimensions(dimX, dimY);
                    properSized = true;
                }
            } catch (sizeException e)
            {
                JOptionPane.showMessageDialog(this, e.getMessage(),
                                    "Wrong Size", JOptionPane.ERROR_MESSAGE);

                dispose();
                return;
            }
            dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.YELLOW);
        buttonPanel.add(cancelButton);

        cancelButton.addActionListener(event -> {
            dispose();
        });

        outerPanel.add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }
}