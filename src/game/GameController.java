package game;

import game.ghost.GameController_Ghost;
import game.pacman.GameController_Pacman;
import game.pacman.GameModel_Pacman;
import game.ui.WinningFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowEvent;

class KeysHandler extends KeyAdapter
{
    private final GameController gameController;

    KeysHandler( GameController gameController )
    {
        this.gameController = gameController;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        GameModel_Pacman m_pacman = gameController.getGame().getPacman().getModel();

        switch (keyCode)
        {
            case KeyEvent.VK_UP -> m_pacman.setDirection(Game.Direction.UP);
            case KeyEvent.VK_DOWN -> m_pacman.setDirection(Game.Direction.DOWN);
            case KeyEvent.VK_LEFT -> m_pacman.setDirection(Game.Direction.LEFT);
            case KeyEvent.VK_RIGHT -> m_pacman.setDirection(Game.Direction.RIGHT);
        }

        int modifiers = e.getModifiers();
        if (keyCode == KeyEvent.VK_Q && (modifiers & KeyEvent.CTRL_MASK) != 0 && (modifiers & KeyEvent.SHIFT_MASK) != 0)
        {
            gameController.getGame().stopGame();
//            System.out.println("Ctrl + Shift + Q pressed");
        }
    }
}

public class GameController
{
    Game game;
    private final GameModel gameModel;
    private final GameView gameView;

    GameController(Game game) {
        this.game = game;
        this.gameModel = game.getModel();
        this.gameView = game.getView();

        gameView.getTable().addKeyListener(new KeysHandler(this));
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void shutDown()
    {
    }

    public Game getGame() {
        return game;
    }
}

/*
    getComponent().addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
          System.out.println("enter");
      }
    });
 */