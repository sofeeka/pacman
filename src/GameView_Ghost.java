import javax.swing.*;
import java.awt.*;

public class GameView_Ghost
{
    GameView gameView;
    ImageIcon fullSizeIconOpen;
    ImageIcon fullSizeIconClosed;
    ImageIcon renderingIconOpen;
    ImageIcon renderingIconClosed;
    private boolean open;
    int width;
    int height;

    GameView_Ghost(GameView gameView)
    {
        this.gameView = gameView;
        fullSizeIconOpen = new ImageIcon("images\\blue_ghost.png");
        fullSizeIconClosed = new ImageIcon("images\\red_ghost.png");
        open = true;
    }
    public void render(Component c, Graphics g, Rectangle cellRect)
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

}