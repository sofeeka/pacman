import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

class ViewTableResizer extends ComponentAdapter {
    private JTable table;
    private ViewTableCellRenderer viewTableCellRenderer;

    ViewTableResizer(GameView gameView) {
        this.table = gameView.getTable();
        this.viewTableCellRenderer = gameView.getTableCellRenderer();
    }

    @Override
    public void componentResized(ComponentEvent e){
        //source: https://stackoverflow.com/questions/63378007/make-jtable-cells-perfectly-square

        Dimension size = table.getSize();

        int rowCount = table.getRowCount();
        int colCount = table.getColumnCount();

        int cellSize = Math.min(size.width / colCount, size.height / rowCount);

        table.setRowHeight(cellSize);
        viewTableCellRenderer.setCellSize(cellSize, cellSize); // todo

        for (int i = 0; i < colCount; i++){
            table.getColumnModel().getColumn(i).setMaxWidth(cellSize);
        }
    }
}
class ViewTableModel extends AbstractTableModel {
    private Element[][] data;

    ViewTableModel(Element[][] data) {
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
class ViewTableCellRenderer extends DefaultTableCellRenderer {
    private static Map<Element, ImageIcon> imageCache = new HashMap<>();
    private int cellWidth;
    private int cellHeight;

    ViewTableCellRenderer()
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

class ViewTable extends JTable
{
    private GameModel gameModel;
    ViewTable(GameModel gameModel)
    {
        this.gameModel = gameModel;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        renderPacman(gameModel.getPacman(), g);

    }
    private void renderPacman(PacmanModel pacman, Graphics g)
    {
        Rectangle cellRect = getCellRect(pacman.getY(), pacman.getX(), true);

        ImageIcon icon = new ImageIcon("images\\pacman.png");
        Image im = icon.getImage();
        int x = cellRect.x;
        int y = cellRect.y;
//        Point cellLocation = cellRect.getLocationOnScreen();

        Image resizedImage = im.getScaledInstance(cellRect.width, cellRect.height, Image.SCALE_SMOOTH);
        icon = new ImageIcon(resizedImage);

        icon.paintIcon(this, g, x, y);

    }

}
public class GameView extends JFrame
{
    private ViewTable table;
    private JLabel scoreLabel;
    private JLabel livesLabel;
    private JLabel timeLabel;
    private GameView_Stopwatch stopwatch;
    private GameModel gameModel;

    private ViewTableCellRenderer viewTableCellRenderer;
    GameView(GameModel gameModel)
    {
        this.gameModel = gameModel;

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EndGameFrame endGameFrame = new EndGameFrame((int)Math.random() * 100); //todo add real highscore
            }
        });

        JPanel upperPanel = new JPanel();
        scoreLabel = new JLabel();
        timeLabel = new JLabel();
        livesLabel = new JLabel();

        upperPanel.add(scoreLabel); //todo align properly (gridBagLayout)
        upperPanel.add(timeLabel);
        upperPanel.add(livesLabel);

        add(upperPanel, BorderLayout.PAGE_START);

        JPanel tablePanel = new JPanel(new BorderLayout());
        table = new ViewTable(gameModel);
        viewTableCellRenderer = new ViewTableCellRenderer();

        table.addComponentListener(new ViewTableResizer(this));

        tablePanel.setBorder(new LineBorder(Color.BLACK, 3));
        tablePanel.add(table, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        pack();
        setSize(500, 500);
        setLocationRelativeTo(null);

        stopwatch = new GameView_Stopwatch(timeLabel);
        stopwatch.start();

        setVisible(true);

    }

    public void renderModel()
    {
        Element[][] gameBoard = gameModel.getGameBoard();
        ViewTableModel tableModel = new ViewTableModel(gameBoard);
        table.setModel(tableModel);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(viewTableCellRenderer);
        }

        scoreLabel.setText( "Score: " + gameModel.getUserScore() );
        livesLabel.setText( "Lives: " + gameModel.getLives() );

    }

    public JTable getTable()
    {
        return this.table;
    }
    public ViewTableCellRenderer getTableCellRenderer()
    {
        return this.viewTableCellRenderer;
    }
}
