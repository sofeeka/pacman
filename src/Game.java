import javax.swing.*;

public class Game
{

    Game()
    {

    }

    public void startNewGame(int x, int y)
    {
        GameView gameView = new GameView(x, y);
        gameView.renderModel(new GameModel(x, y));
        //frame
//        model
//        view
//        contr
    }

}
