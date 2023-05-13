import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
class TableResizer extends ComponentAdapter {
    private JTable table;

    TableResizer(JTable table) {
        this.table = table;
    }

}

public class GameView extends JFrame
{
    private JTable table;
    GameView(int dimX, int dimY)
    {
        table = new JTable(dimY, dimX);

        table.addComponentListener(new TableResizer(table));

        add(table, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        pack();
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void renderModel(GameModel gameModel)
    {

    }
    }
