import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_UP;

public class GameController implements KeyListener
{
    private GameModel gameModel;

    GameController(GameModel gameModel)
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


    @Override
    public void keyReleased(KeyEvent e) {
        // Handle key released event if needed
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Handle key typed event if needed
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