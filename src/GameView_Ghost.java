import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class GameView_Ghost
{
    GameView gameView;
    ArrayList<ImageIcon> fullSizeIcons;
    ArrayList<ImageIcon> renderingIcons;
    private int iconIndex;
    int width;
    int height;

    GameView_Ghost(GameView gameView)
    {
        this.gameView = gameView;

        fullSizeIcons = new ArrayList<>();
        fullSizeIcons.add(new ImageIcon("images\\ghost1.png"));
        fullSizeIcons.add(new ImageIcon("images\\ghost2.png"));

        iconIndex = 0;
    }
    public void render(Component c, Graphics g, Rectangle cellRect)
    {
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

        gameView.renderModel();
    }
    private ImageIcon getRenderingIcon() {
        return this.renderingIcons.get(iconIndex);
    }

}