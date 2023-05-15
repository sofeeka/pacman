import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

class PacmanView
{
    ImageIcon fullSizeIcon;
    ImageIcon renderingIcon;

    int width;
    int height;

    PacmanView()
    {
        fullSizeIcon = new ImageIcon("images\\pacman.png");
    }
    public void renderPacman(Component c, Graphics g, Rectangle cellRect)
    {
        if(this.width != cellRect.width || this.height != cellRect.height || renderingIcon == null) // todo check direction
        {
            Image im = fullSizeIcon.getImage();
            Image resizedImage = im.getScaledInstance(cellRect.width, cellRect.height, Image.SCALE_SMOOTH);
            renderingIcon = new ImageIcon(resizedImage);
        }

        int x = cellRect.x;
        int y = cellRect.y;

        renderingIcon.paintIcon(c, g, x, y);

    }
}
class ViewTableResizer extends ComponentAdapter {
    private ViewTable table;
    private ViewTableCellRenderer viewTableCellRenderer;

    ViewTableResizer(GameView gameView) {
        this.table = gameView.getTable();
        this.viewTableCellRenderer = table.getTableCellRenderer();
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
    GameModel gameModel;

    ViewTableModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public int getRowCount() {
        return gameModel.getHeight();
    }

    @Override
    public int getColumnCount() {
        return gameModel.getWidth();
    }

    @Override
    public Element getValueAt(int rowIndex, int columnIndex) {
        return gameModel.getElementAt(columnIndex, rowIndex);
    }
}
class ViewTableCellRenderer extends DefaultTableCellRenderer {
    private GameModel gameModel;
    private static Map<Element, ImageIcon> imageCache = new HashMap<>();
    private int cellWidth;
    private int cellHeight;

    ViewTableCellRenderer(GameModel gameModel)
    {
        this.gameModel = gameModel;
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
        if (value != null)
        {
            Element element = gameModel.getElementAt(column, row);
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
//            case EMPTY -> path = "images\\black.png"; //todo change picture
            default -> path = "images\\black.png";
        }
        return new ImageIcon(path);
    }

}
class ViewTable extends JTable
{
    private GameModel gameModel;
    private GameView gameView;
    private ViewTableCellRenderer viewTableCellRenderer;

    ViewTable(GameView gameView)
    {
        this.gameView = gameView;
        this.gameModel = gameView.getGameModel();

        ViewTableModel tableModel = new ViewTableModel(gameModel);
        this.setModel(tableModel);

        viewTableCellRenderer = new ViewTableCellRenderer(gameModel);

        for (int i = 0; i < gameModel.getWidth(); i++)
        {
            this.getColumnModel().getColumn(i).setCellRenderer(viewTableCellRenderer);
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        PacmanModel pacman = gameModel.getPacman();
        gameView.getPacmanView().renderPacman(this, g, getCellRect(pacman.getY(), pacman.getX(), true));

    }

    public ViewTableCellRenderer getTableCellRenderer()
    {
        return this.viewTableCellRenderer;
    }

}
public class GameView extends JFrame
{
    private ViewTable table;
    private JLabel scoreLabel;
    private JLabel livesLabel;
    private JLabel timeLabel;
    private JLabel pointsLabel;
    private GameView_Stopwatch stopwatch;
    private GameModel gameModel;
    private PacmanView pacmanView;

    GameView(GameModel gameModel)
    {
        this.gameModel = gameModel;
        pacmanView = new PacmanView();

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
        pointsLabel = new JLabel();

        upperPanel.add(scoreLabel); //todo align properly (gridBagLayout)
        upperPanel.add(timeLabel);
        upperPanel.add(livesLabel);
        upperPanel.add(pointsLabel);

        upperPanel.setSize(500,50);

        add(upperPanel, BorderLayout.PAGE_START);

        JPanel tablePanel = new JPanel(new BorderLayout());
        table = new ViewTable(this);

        table.addComponentListener(new ViewTableResizer(this));

        tablePanel.add(table, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        pack();
        setSize(450, 500);
        setLocationRelativeTo(null);

        stopwatch = new GameView_Stopwatch(timeLabel);
        stopwatch.start();

        setVisible(true);

    }

    public void renderModel()
    {
        scoreLabel.setText( "Score: " + gameModel.getUserScore() );
        livesLabel.setText( "Lives: " + gameModel.getLives() );
        pointsLabel.setText("Points left: " + gameModel.getRemainingPointsQty());

        table.repaint();
    }

    public void modelChanged()
    {
        renderModel();
    }

    public ViewTable getTable()
    {
        return this.table;
    }
    public PacmanView getPacmanView() {
        return pacmanView;
    }

    public GameModel getGameModel() {
        return gameModel;
    }
}
