import javax.swing.*;

class GameRunner extends Thread
{
    private int x;
    private int y;

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
    }
}

public class Game
{
    Game()
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
