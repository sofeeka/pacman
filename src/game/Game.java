package game;

import game.ghost.Ghost;
import game.pacman.Pacman;
import game.ui.WinningFrame;

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

        public static Game.Direction getRandomDirection()  {
            Game.Direction[] directions = values();
            return directions[RAND.nextInt(directions.length)];
        }
    }

    // MVC
    private GameModel model;
    private GameView view;
    private GameController controller;

    //
    Pacman pacman;
    ArrayList<Ghost> ghosts;

    public Game(int x, int y)
    {
        model = new GameModel(this, x, y);
        view = new GameView(this);
        controller = new GameController( this );

        createPacman();
        createGhosts();
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
        ghosts = new ArrayList<>();

        ghosts.add( new Ghost(this) );
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
}
