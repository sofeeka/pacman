package game;

import game.ghost.Ghost;
import game.pacman.Pacman;
import game.ui.WinningFrame;
import game.upgrader.Upgrader;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game
{
    public enum Element {
        EMPTY,
        WALL,
        POINT,
        POWER_PELLET,
        FOOD;

        private static final Random RAND = new Random();

        public static Element getRandomElement()  {
            Element[] elements = values();
            return elements[RAND.nextInt(elements.length)];
        }

        public String getMessage()
        {
            return name();
        }
    }

    public enum Direction
    {
        STILL,
        UP,
        DOWN,
        LEFT,
        RIGHT;

        private static final Random RAND = new Random();

        public static Game.Direction getRandomDirection()
        {
            Game.Direction[] directions = values();
            return directions[RAND.nextInt(directions.length)];
        }

        public static Game.Direction getDirectionByDelta(int dX, int dY)
        {
            if( dX > 0 )
                return RIGHT;
            if( dX < 0 )
                return LEFT;
            if( dY > 0 )
                return DOWN;
            if( dY < 0 )
                return UP;

            return STILL;
        }
    }

    public enum Upgrade {
        SPEED_UP,
        FREEZE_GHOSTS,
        HIDE_GHOSTS,
        BOOST_SCORE;

        private static final Random RAND = new Random();

        public static Game.Upgrade getRandomUpgrade()  {
            Game.Upgrade[] upgrades = values();
            return upgrades[RAND.nextInt(upgrades.length)];
        }
    }

    // MVC
    private GameModel model;
    private GameView view;
    private GameController controller;

    //
    private Pacman pacman;
    private ArrayList<Ghost> ghosts;
    private Upgrader upgrader;

    public Game(int x, int y)
    {
        model = new GameModel(this, x, y);
        view = new GameView(this);
        controller = new GameController( this );

        createPacman();
        createGhosts();
        createUpgrader();
    }

    public void stopGame()
    {
        shutDown();

        view.setVisible(false);
        view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
        view.dispose();
    }

    private void createPacman()
    {
        pacman = new Pacman(this);
    }

    private void createGhosts()
    {
        final int ghostsQty = Math.max( getModel().getRemainingPointsQty() / 30, 2 );

        ghosts = new ArrayList<>();
        for( int i = 0; i < ghostsQty; i++ )
            ghosts.add( new Ghost(this) );
    }

    void createUpgrader()
    {
        upgrader = new Upgrader(this);
    }
    public GameModel getModel() {
        return model;
    }

    public GameView getView() {
        return view;
    }

    public GameController getController() {
        return controller;
    }

    public void userWon()
    {
        showWinningFrame();
        stopGame();
    }

    private void showWinningFrame()
    {
        WinningFrame winningFrame = new WinningFrame(model.getUserScore());
        winningFrame.setVisible(true);
        winningFrame.dispose();
    }

    void shutDown()
    {
        upgrader.shutDown();

        pacman.shutDown();

        for( Ghost ghost : ghosts )
            ghost.shutDown();

        controller.shutDown();
        view.shutDown();
        model.shutDown();
    }

    public Pacman getPacman() {
        return pacman;
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    public Upgrader getUpgrader() {
        return upgrader;
    }
}
