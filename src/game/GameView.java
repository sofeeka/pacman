package game;

import game.ghost.GameModel_Ghost;
import game.ghost.GameView_Ghost;
import game.pacman.GameModel_Pacman;
import game.pacman.GameView_Pacman;
import game.ui.EndGameFrame;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

class ViewTableResizer extends ComponentAdapter
{
    private final ViewTable table;
    private final ViewTableCellRenderer viewTableCellRenderer;

    ViewTableResizer(GameView gameView)
    {
        this.table = gameView.getTable();
        this.viewTableCellRenderer = table.getTableCellRenderer();
    }

    @Override
    public void componentResized(ComponentEvent e)
    {
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
class ViewTableModel extends AbstractTableModel
{
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
    public Game.Element getValueAt(int rowIndex, int columnIndex) {
        return gameModel.getElementAt(columnIndex, rowIndex);
    }
}
class ViewTableCellRenderer extends DefaultTableCellRenderer
{
    private final GameModel gameModel;
    private static Map<Game.Element, ImageIcon> imageCache = new HashMap<>();
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
            Game.Element element = gameModel.getElementAt(column, row);
            ImageIcon imageIcon = getImageIcon(element);

            setIcon(imageIcon); // todo: resize
        }
        return this;
    }

    private ImageIcon getImageIcon(Game.Element element)
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

    private ImageIcon createImageIcon(Game.Element element)
    {
        String path;
        switch (element)
        {
            case POINT -> path = "images\\point.png";
            case WALL -> path = "images\\wall.png";
            case FOOD -> path = "images\\food.png";
            case POWER_PELLET -> path = "images\\pellet.png";
//            case EMPTY -> path = "images\\black.png"; //todo change picture
            default -> path = "images\\black.png";
        }
        return new ImageIcon(path);
    }

}
class ViewTable extends JTable
{
    private final GameModel gameModel;
    private final GameView gameView;
    private final ViewTableCellRenderer viewTableCellRenderer;

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
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        GameModel_Pacman pacman = gameModel.getPacman();
        gameView.getPacmanView().renderPacman(this, g, getCellRect(pacman.getY(), pacman.getX(), true));

        GameModel_Ghost ghost = gameModel.getGhost();
        gameView.getGameView_Ghost().render(this, g, getCellRect(ghost.getY(), ghost.getX(), true));
    }

    public ViewTableCellRenderer getTableCellRenderer()
    {
        return this.viewTableCellRenderer;
    }
}

class GameViewCloseHandler extends WindowAdapter
{
    GameModel gameModel;
    GameViewCloseHandler(GameModel gameModel)
    {
        this.gameModel = gameModel;
    }
    @Override
    public void windowClosing(WindowEvent e) {
        gameModel.getGameView().shutDown();

        EndGameFrame endGameFrame = new EndGameFrame(gameModel.getUserScore());
        endGameFrame.setVisible(true);
    }}

public class GameView extends JFrame
{
    private final ViewTable table;
    private final JLabel scoreLabel;
    private final JLabel livesLabel;
    private final JLabel timeLabel;
    private final JLabel pointsLabel;
    private final GameView_Stopwatch stopwatch;
    private final GameModel gameModel;
    private final GameView_Pacman gameViewPacman;
    private final GameView_Ghost gameView_Ghost;
    GameView(GameModel gameModel)
    {
        this.gameModel = gameModel;
        gameViewPacman = new GameView_Pacman(this);
        gameView_Ghost = new GameView_Ghost(gameModel.getGhost(), this);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // DO_NOTHING_ON_CLOSE

        addWindowListener(new GameViewCloseHandler(gameModel));

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
    public GameView_Pacman getPacmanView() {
        return gameViewPacman;
    }

    public GameView_Ghost getGameView_Ghost() {
        return gameView_Ghost;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void shutDown()
    {
        gameView_Ghost.shutDown();
        gameViewPacman.shutDown();
        stopwatch.shutDown();
    }
}