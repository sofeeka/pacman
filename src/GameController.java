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

class PacmanIconChanger extends Thread
{
    GameView gameView;
    GameModel gameModel;
    PacmanIconChanger(GameView gameView, GameModel gameModel)
    {
        this.gameView = gameView;
        this.gameModel = gameModel;
    }

    @Override
    public void run()
    {
        while(true)
        {
            Game.mySleep(100);
            gameView.getPacmanView().changeIcon();
        }
    }
}
class PacmanMover extends Thread
{
    GameModel gameModel;

    PacmanMover(GameModel gameModel )
    {
        this.gameModel = gameModel;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                sleep(200);
            } catch (Exception e) {}

            int shiftX = 0;
            int shiftY = 0;

            switch( gameModel.getPacman().getDirection() )
            {
                case UP -> shiftY = -1;
                case DOWN -> shiftY = 1;
                case LEFT -> shiftX = -1;
                case RIGHT -> shiftX = 1;
            }

            if( shiftX == 0 && shiftY == 0 )
                continue;

            int newX = gameModel.getPacman().getX() + shiftX;
            int newY = gameModel.getPacman().getY() + shiftY;

            // cannot move outside game board bounds
            if( newX < 0 || newX >= gameModel.getWidth() || newY < 0 || newY >= gameModel.getHeight() )
                continue;

            // cannot move to walls
            if( gameModel.elementIsWall(newX, newY ))
                continue;

            //
            gameModel.getPacman().setXY(newX, newY);
        }
    }
}


public class GameController
{
    private GameModel gameModel;
    private GameView gameView;
    private PacmanMover pacmanMover;
    private PacmanIconChanger pacmanIconChanger;
    private GameController_Ghosts gameController_ghosts;

    GameController(GameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;

        gameView.getTable().addKeyListener(new KeysHandler(this));

        pacmanMover = new PacmanMover(gameModel);
        pacmanMover.start();

        pacmanIconChanger = new PacmanIconChanger(gameView, gameModel);
        pacmanIconChanger.start();

        gameController_ghosts = new GameController_Ghosts(this);
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void shutDown()
    {
        pacmanMover.interrupt();
        pacmanIconChanger.interrupt();

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