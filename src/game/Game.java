package game;

import javax.swing.*;
import java.util.Random;

class GameRunner extends Thread
{
    private final int x;
    private final int y;

    GameRunner( int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void run()
    {
        GameModel gameModel = new GameModel(x, y);
        GameView gameView = new GameView(gameModel);
        gameModel.setGameView( gameView );

        GameController gameController = new GameController(gameModel, gameView );
        gameModel.setGameController( gameController );
    }
}

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

    public Game()
    {
    }

    public void startNewGame(int x, int y)
    {
        GameRunner runner = new GameRunner(x, y);
        SwingUtilities.invokeLater( runner );
    }

    public static void mySleep(int time)
    {
        try
        {
            Thread.sleep(time);
        } catch (InterruptedException e)
        {
            //e.printStackTrace();
        }
    }
}
