import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;

import static java.awt.event.KeyEvent.VK_UP;

class KeysHandler extends KeyAdapter
{
    private GameModel gameModel;

    KeysHandler( GameModel gameModel )
    {
        this.gameModel = gameModel;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode)
        {
            case KeyEvent.VK_UP -> {
                gameModel.getPacman().setDirection(Direction.UP);
                System.out.println("up");
            }
            case KeyEvent.VK_DOWN -> {
                gameModel.getPacman().setDirection(Direction.DOWN);
                System.out.println("down");
            }
            case KeyEvent.VK_LEFT -> {
                gameModel.getPacman().setDirection(Direction.LEFT);
            }
            case KeyEvent.VK_RIGHT -> {
                gameModel.getPacman().setDirection(Direction.RIGHT);
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

    GameController(GameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;

        gameView.getTable().addKeyListener(new KeysHandler(gameModel));
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