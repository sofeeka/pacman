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
    GameModel_Ghost ghost;
    GameView gameView;
    ArrayList<ImageIcon> fullSizeIcons;
    ArrayList<ImageIcon> renderingIcons;
    private int iconIndex;
    private boolean iconFlashing;
    int width;
    int height;
    GhostIconChanger ghostIconChanger;

    public GameView_Ghost(GameModel_Ghost ghost, GameView gameView)
    {
        this.ghost = ghost;
        this.gameView = gameView;

        fullSizeIcons = new ArrayList<>();
        fullSizeIcons.add(new ImageIcon("images\\ghost1.png"));
        fullSizeIcons.add(new ImageIcon("images\\ghost2.png"));

        iconIndex = 0;

        ghostIconChanger = new GhostIconChanger(this);
        ghostIconChanger.start();
    }
    public void render(Component c, Graphics g, Rectangle cellRect)
    {
        if( iconIndex == -1 ) // frightened ghost is flashing
            return;

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

        getRenderingIcon().paintIcon(c, g, x, y);
    }

    public void changeIcon()
    {
        iconIndex++;
        if( iconIndex >= fullSizeIcons.size())
            iconIndex = 0;

        if( ghost.isFrightened() && iconIndex > 0 )
            iconIndex = -1;

        gameView.renderModel();
    }
    private ImageIcon getRenderingIcon() {
        return this.renderingIcons.get(iconIndex);
    }

    public void shutDown()
    {
        ghostIconChanger.interrupt();
    }

}