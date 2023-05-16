package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HighScoreFrame extends JFrame
{
    private ArrayList<HighScoreRecord> highScoreRecords;
    private JList<String> highScoreList;


    HighScoreFrame()
    {
        setTitle("High Scores");

        HighScore highScore = new HighScore();
        this.highScoreRecords = highScore.getHighScores();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (HighScoreRecord highScoreRecord : highScoreRecords) {
            listModel.addElement(highScoreRecord.toString());
        }

        highScoreList = new JList<>(listModel);
        highScoreList.setCellRenderer(new HighScoreRenderer());

        setLayout(new BorderLayout());
        add(new JScrollPane(highScoreList), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(200,300);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    class HighScoreRenderer extends DefaultListCellRenderer
    {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus)
        {
//            if (value instanceof HighScoreRecord) {
//                String record = (String) value;
//            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        }
    }

}
