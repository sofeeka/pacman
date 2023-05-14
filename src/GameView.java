import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

class TableResizer extends ComponentAdapter {
    private JTable table;
    private myCellRenderer tableCellRenderer;

    TableResizer(GameView gameView) {
        this.table = gameView.getTable();
        this.tableCellRenderer = gameView.getTableCellRenderer();
    }

    @Override
    public void componentResized(ComponentEvent e){
        //source: https://stackoverflow.com/questions/63378007/make-jtable-cells-perfectly-square

        Dimension size = table.getSize();

        int rowCount = table.getRowCount();
        int colCount = table.getColumnCount();

        int cellSize = Math.min(size.width / colCount, size.height / rowCount);

        table.setRowHeight(cellSize);
        tableCellRenderer.setCellSize(cellSize, cellSize); // todo

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
    public Element getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
}
class myCellRenderer extends DefaultTableCellRenderer {
    private static Map<Element, ImageIcon> imageCache = new HashMap<>();
    private int cellWidth;
    private int cellHeight;

    myCellRenderer()
    {
        setCellSize(15,15);
    }


    public void setCellSize( int w, int h)
    {
        this.cellWidth = w;
        this.cellHeight = h;

        imageCache.clear();
    }


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column)
    {
        if (value instanceof Element)
        {
            Element element = (Element) value;
            ImageIcon imageIcon = getImageIcon(element);

            setIcon(imageIcon); // todo: resize
        }

        return this;
    }

    private ImageIcon getImageIcon(Element element)
    {
        ImageIcon imageIcon = imageCache.get(element);

        if (imageIcon != null)
            return imageIcon;

        imageIcon = createImageIcon(element);
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);

        imageIcon = new ImageIcon(resizedImage);

        imageCache.put(element, imageIcon);

        return imageIcon;
    }

    private ImageIcon createImageIcon(Element element)
    {
        String path;
        switch (element)
        {
            case POINT -> path = "images\\point.png";
            case WALL -> path = "images\\wall.png";
            case FOOD -> path = "images\\food.png";
            default -> path = "images\\black.png";
        }
        return new ImageIcon(path);
    }

}
public class GameView extends JFrame
{
    private JTable table;
    private myCellRenderer tableCellRenderer;

    private int highScore;
    GameView(int dimX, int dimY)
    {
        JLabel label = new JLabel("High Score: " );

        table = new JTable(dimY, dimX);
        tableCellRenderer = new myCellRenderer();

        table.addComponentListener(new TableResizer(this));

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

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
        }

    }

    public JTable getTable()
    {
        return this.table;
    }
    public myCellRenderer getTableCellRenderer()
    {
        return  this.tableCellRenderer;
    }
}
