import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

class KeysHandler extends KeyAdapter
{
    private GameController gameController;

    KeysHandler( GameController gameController )
    {
        this.gameController = gameController;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        GameModel_Pacman pacman = gameController.getGameModel().getPacman();

        switch (keyCode)
        {
            case KeyEvent.VK_UP -> {
                pacman.setDirection(Direction.UP);
            }
            case KeyEvent.VK_DOWN -> {
                pacman.setDirection(Direction.DOWN);
            }
            case KeyEvent.VK_LEFT -> {
                pacman.setDirection(Direction.LEFT);
            }
            case KeyEvent.VK_RIGHT -> {
                pacman.setDirection(Direction.RIGHT);
            }
        }

        int modifiers = e.getModifiers();
        if (keyCode == KeyEvent.VK_Q && (modifiers & KeyEvent.CTRL_MASK) != 0 && (modifiers & KeyEvent.SHIFT_MASK) != 0)
        {
            System.out.println("Ctrl + Shift + Q pressed");
        }
    }
}

public class GameController
{
    private GameModel gameModel;
    private GameView gameView;
//    private PacmanMover pacmanMover;
    private GameController_Pacman gameController_pacman;
    private GameController_Ghosts gameController_ghosts;

    GameController(GameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;

        gameView.getTable().addKeyListener(new KeysHandler(this));

        gameController_pacman = new GameController_Pacman(this);
        gameController_ghosts = new GameController_Ghosts(this);
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void shutDown()
    {
        gameController_pacman.shutDown();
        gameController_ghosts.shutDown();
        gameView.shutDown();
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