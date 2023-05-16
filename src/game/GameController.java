package game;

import game.ghost.GameController_Ghosts;
import game.pacman.GameController_Pacman;
import game.pacman.GameModel_Pacman;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowEvent;

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
                pacman.setDirection(Game.Direction.UP);
            }
            case KeyEvent.VK_DOWN -> {
                pacman.setDirection(Game.Direction.DOWN);
            }
            case KeyEvent.VK_LEFT -> {
                pacman.setDirection(Game.Direction.LEFT);
            }
            case KeyEvent.VK_RIGHT -> {
                pacman.setDirection(Game.Direction.RIGHT);
            }
        }

        int modifiers = e.getModifiers();
        if (keyCode == KeyEvent.VK_Q && (modifiers & KeyEvent.CTRL_MASK) != 0 && (modifiers & KeyEvent.SHIFT_MASK) != 0)
        {
            gameController.stopGame();
//            System.out.println("Ctrl + Shift + Q pressed");
        }
    }
}

public class GameController
{
    private GameModel gameModel;
    private GameView gameView;
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

    public void stopGame()
    {
        shutDown();

        gameView.setVisible(false);
        gameView.dispatchEvent(new WindowEvent(gameView, WindowEvent.WINDOW_CLOSING));
        gameView.dispose();
    }

    public void userWon()
    {
        showWinningFrame();
        stopGame();
    }

    private void showWinningFrame()
    {
        WinningFrame winningFrame = new WinningFrame(gameModel.getUserScore());
        winningFrame.setVisible(true);
        winningFrame.dispose();
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