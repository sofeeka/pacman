import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

class TableResizer extends ComponentAdapter {
    private JTable table;

    TableResizer(JTable table) {
        this.table = table;
    }

    @Override
    public void componentResized(ComponentEvent e){
        //source: https://stackoverflow.com/questions/63378007/make-jtable-cells-perfectly-square

        Dimension size = table.getSize();

        int rowCount = table.getRowCount();
        int colCount = table.getColumnCount();

        int cellSize;

        if (size.height / rowCount > size.width / colCount){
            cellSize = size.width / colCount;
        }
        else{
            cellSize = size.height / rowCount;
        }

        table.setRowHeight(cellSize);

        for (int i = 0; i < colCount; i++){
            table.getColumnModel().getColumn(i).setMaxWidth(cellSize);
        }

    }

}
class GameTableModel extends AbstractTableModel {
    private Element[][] data;

    GameTableModel(Element[][] data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return data[0].length;
    }

    @Override
    public String getValueAt(int rowIndex, int columnIndex) {
        Element e = data[rowIndex][columnIndex];
        return e.getMessage();
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
        Element[][] gameBoard = gameModel.getGameBoard();
        GameTableModel tableModel = new GameTableModel(gameBoard);
        table.setModel(tableModel);

    }
}
