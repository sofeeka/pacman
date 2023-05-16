package game.pacman;

import game.Game;
import game.GameView;

import javax.swing.*;
import java.awt.*;

class PacmanIconChanger extends Thread
{
    GameView_Pacman gameView_Pacman;
    PacmanIconChanger(GameView_Pacman gameView_Pacman )
    {
        this.gameView_Pacman = gameView_Pacman;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try {
                Thread.sleep(150);
            }
            catch(InterruptedException e){
                return;
            }

            gameView_Pacman.changeIcon();
        }
    }
}


public class GameView_Pacman
{
    GameView gameView;
    ImageIcon fullSizeIconOpen;
    ImageIcon fullSizeIconClosed;
    ImageIcon renderingIconOpen;
    ImageIcon renderingIconClosed;
    private boolean open;
    private final PacmanIconChanger pacmanIconChanger;

    int width;
    int height;

    public GameView_Pacman(GameView gameView)
    {
        this.gameView = gameView;
        fullSizeIconOpen = new ImageIcon("images\\pacman_open.png");
        fullSizeIconClosed = new ImageIcon("images\\pacman_closed.png");
        open = true;

        pacmanIconChanger = new PacmanIconChanger(this);
        pacmanIconChanger.start();
    }
    public void renderPacman(Component c, Graphics g, Rectangle cellRect)
    {
        if(this.width != cellRect.width || this.height != cellRect.height || renderingIconOpen == null) // todo check direction
        {
            Image open = fullSizeIconOpen.getImage();
            Image resizedOpen = open.getScaledInstance(cellRect.width, cellRect.height, Image.SCALE_SMOOTH);
            renderingIconOpen = new ImageIcon(resizedOpen);

            Image closed = fullSizeIconClosed.getImage();
            Image resizedClosed = closed.getScaledInstance(cellRect.width, cellRect.height, Image.SCALE_SMOOTH);
            renderingIconClosed = new ImageIcon(resizedClosed);

        }

        int x = cellRect.x;
        int y = cellRect.y;

        getRenderingIcon().paintIcon(c, g, x, y);

    }

    public void changeIcon()
    {
        open = !open;
        gameView.renderModel();
    }

    public ImageIcon getRenderingIcon() {
        return open ? renderingIconOpen : renderingIconClosed;
    }

    public void shutDown()
    {
        pacmanIconChanger.interrupt();
    }
}