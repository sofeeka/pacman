package game;

import game.ghost.Ghost;
import game.pacman.Pacman;
import game.gui.EndGameFrame;
import game.upgrader.Upgrader;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
            default -> path = "images\\black.png";
        }
        return new ImageIcon(path);
    }
}
class ViewTable extends JTable
{
    private final GameView gameView;
    private final ViewTableCellRenderer viewTableCellRenderer;

    ViewTable(GameView gameView)
    {
        this.gameView = gameView;

        GameModel gameModel = gameView.getGameModel();

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
        // Paint table
        super.paintComponent(g);

        // Paint upgrade icon (strawberry)
        Upgrader upgrader = gameView.getGame().getUpgrader();
        upgrader.getView().render( this, g );

        // Paint pacman
        Pacman pacman = gameView.getGame().getPacman();
        pacman.getView().render( this, g);

        // Paint ghosts
        for( Ghost ghost : gameView.getGame().getGhosts() ) {
            ghost.getView().render(this, g );
        }
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
        int userScore = gameModel.getUserScore();

        gameModel.getGame().shutDown();

        EndGameFrame endGameFrame = new EndGameFrame(userScore);
        endGameFrame.setVisible(true);
    }
}

class GameViewRepainter extends Thread {
    GameView gameView;

    public GameViewRepainter(GameView gameView) {
        this.gameView = gameView;
        setName("Game view repainter");
    }

    @Override
    public void run() {
        synchronized (this) {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    return;
                }

                gameView.getTable().repaint();
            }
        }
    }
}

public class GameView extends JFrame
{
    private Game game;
    private final ViewTable table;
    private final JLabel scoreLabel;
    private final JLabel livesLabel;
    private final JLabel timeLabel;
    private final JLabel pointsLabel;
    private final JLabel upgradeLabel;
    private final GameView_Stopwatch stopwatch;
    private final GameViewRepainter repainter;
    GameView(Game game)
    {
        this.game = game;

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // DO_NOTHING_ON_CLOSE

        addWindowListener(new GameViewCloseHandler(game.getModel()));

        JPanel upperPanel = new JPanel();
        scoreLabel = new JLabel();
        timeLabel = new JLabel();
        livesLabel = new JLabel();
        pointsLabel = new JLabel();
        upgradeLabel = new JLabel();

        upperPanel.add(scoreLabel);
        upperPanel.add(timeLabel);
        upperPanel.add(livesLabel);
        upperPanel.add(pointsLabel);
        upperPanel.add(upgradeLabel);

        add(upperPanel, BorderLayout.PAGE_START);

        JPanel tablePanel = new JPanel(new BorderLayout());
        table = new ViewTable(this);

        table.addComponentListener(new ViewTableResizer(this));

        tablePanel.add(table, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(450, 500);
        setLocationRelativeTo(null);

        stopwatch = new GameView_Stopwatch(timeLabel);
        stopwatch.start();

        repainter = new GameViewRepainter(this);
        repainter.start();

        updateScoreLabel();
        updateLivesLabel();
        updatePointsLeftLabel();

        if (game.getModel().getHeight() > 20 || game.getModel().getWidth() > 20)
            setExtendedState(JFrame.MAXIMIZED_BOTH);

        setVisible(true);
    }

    public void renderModel()
    {
//        table.repaint();
    }

    public void modelChanged()
    {
        renderModel();
    }
    public ViewTable getTable()
    {
        return this.table;
    }

    public GameModel getGameModel() {
        return game.getModel();
    }

    public void shutDown()
    {
        stopwatch.interrupt();
        repainter.interrupt();
    }

    public Game getGame() {
        return game;
    }

    public void setUpgradeLabelText(String s)
    {
        upgradeLabel.setText(s);
    }

    public void updateScoreLabel()
    {
        scoreLabel.setText( "Score: " + game.getModel().getUserScore() );
    }

    public void updateLivesLabel()
    {
        livesLabel.setText( "Lives: " + game.getModel().getLives() );
    }

    public void updatePointsLeftLabel()
    {
        pointsLabel.setText("Points left: " + game.getModel().getRemainingPointsQty());
    }
}
class GameView_Stopwatch extends Thread
{
    long startTime;
    JLabel timeLabel;
    GameView_Stopwatch(JLabel timeLabel)
    {
        this.timeLabel = timeLabel;
        setName( "Game stopwatch" );
    }
    @Override
    public void run()
    {
        startTime = System.currentTimeMillis();

        while(!Thread.interrupted())
        {
            timeLabel.setText(getTime());
            try
            {
                sleep(1000);
            } catch(InterruptedException e) {
                return;
            }
        }
    }

    public String getTime()
    {
        long timePassed = System.currentTimeMillis() - startTime;

        long minutes = TimeUnit.MILLISECONDS.toMinutes(timePassed);
        long seconds =
                TimeUnit.MILLISECONDS.toSeconds(timePassed)
                        - TimeUnit.MINUTES.toSeconds(minutes);

        String stopwatchTime = String.format("Time: %02d:%02d", minutes, seconds);
        return stopwatchTime;
    }
}

