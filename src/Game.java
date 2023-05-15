import javax.swing.*;

public class Game
{

    Game()
    {

    }

    public void startNewGame(int x, int y)
    {
        GameModel gameModel = new GameModel(x, y);

        GameView gameView = new GameView(gameModel);

        gameModel.setGameView( gameView );

        gameView.renderModel();
        GameController gameController = new GameController(gameModel, gameView );
        //frame
//        view
    }

}
