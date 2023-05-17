package game;

import javax.swing.*;

class GameRunner extends Thread
{
    private final int x;
    private final int y;

    Game game;
    GameRunner( int x, int y)
    {
        this.x = x;
        this.y = y;

        setName( "Game runner" );
    }

    @Override
    public void run()
    {
        game = new Game(x, y);
    }
}

public class GameStarter{
    public static void startNewGame(int x, int y)
    {
        GameRunner runner = new GameRunner(x, y);
        SwingUtilities.invokeLater( runner );
    }
}
