package game.ghost;

import game.Game;
import game.GameView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

class GhostIconChanger extends Thread
{
    GameView_Ghost gameView_Ghost;

    GhostIconChanger( GameView_Ghost gameView_Ghost )
    {
        this.gameView_Ghost = gameView_Ghost;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try {
                Thread.sleep(200);
            }
            catch(InterruptedException e){
                return;
            }

            gameView_Ghost.changeIcon();
        }
    }
}

public class GameView_Ghost
{
    Ghost ghost;
    ArrayList<ImageIcon> fullSizeIcons;
    ArrayList<ImageIcon> renderingIcons;
    private int iconIndex;
    int width;
    int height;
    GhostIconChanger ghostIconChanger;

    public GameView_Ghost(Ghost ghost)
    {
        this.ghost = ghost;

        fullSizeIcons = new ArrayList<>();
        fullSizeIcons.add(new ImageIcon("images\\ghost1.png"));
        fullSizeIcons.add(new ImageIcon("images\\ghost2.png"));

        iconIndex = 0;

        ghostIconChanger = new GhostIconChanger(this);
        ghostIconChanger.start();
    }
    synchronized public void render(JTable t, Graphics g)
    {
        if( iconIndex == -1 ) // frightened ghost is flashing, hidden stays hidden
            return;

        Rectangle cellRect = t.getCellRect(ghost.getModel().getY(), ghost.getModel().getX(), true);

        if(this.width != cellRect.width || this.height != cellRect.height || renderingIcons == null) // todo check direction
        {
            renderingIcons = new ArrayList<>();

            for( ImageIcon icon : fullSizeIcons )
            {
                Image fullSizeImage = icon.getImage();
                Image renderingImage = fullSizeImage.getScaledInstance(cellRect.width, cellRect.height, Image.SCALE_SMOOTH);
                renderingIcons.add( new ImageIcon( renderingImage ) );
            }
        }

        int x = cellRect.x;
        int y = cellRect.y;

        getRenderingIcon().paintIcon(t, g, x, y);
    }

    synchronized public void changeIcon()
    {
        iconIndex++;

        if( iconIndex >= fullSizeIcons.size())
            iconIndex = 0;

        if( ghost.getModel().isFrightened() && iconIndex > 0 || ghost.getModel().isHidden())
            iconIndex = -1;

        ghost.getGame().getView().renderModel();
    }
    synchronized private ImageIcon getRenderingIcon() {
        return this.renderingIcons.get(iconIndex);
    }

    public void shutDown()
    {
        ghostIconChanger.interrupt();
    }
}