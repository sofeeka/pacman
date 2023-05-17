package game.upgrader;

import javax.swing.*;
import java.awt.*;

public class UpgraderView {
    Upgrader upgrader;

    ImageIcon originalIcon;
    ImageIcon renderingIcon;
    int iconWidth;
    int iconHeight;

    public UpgraderView(Upgrader upgrader) {
        this.upgrader = upgrader;
        originalIcon = new ImageIcon("images\\food.png");
    }

    public void render( JTable t, Graphics g )
    {
        if( !upgrader.getModel().isDropped() )
            return;

        game.Position pos = upgrader.getModel().getPos();

        Rectangle cellRect = t.getCellRect(pos.getY(), pos.getX(), true);

        if(this.iconWidth != cellRect.width || this.iconHeight != cellRect.height || renderingIcon == null )
        {
            Image open = originalIcon.getImage();
            Image resizedOpen = open.getScaledInstance(cellRect.width, cellRect.height, Image.SCALE_SMOOTH);
            renderingIcon = new ImageIcon(resizedOpen);

            iconWidth = cellRect.width;
            iconHeight = cellRect.height;
        }

        renderingIcon.paintIcon(t, g, cellRect.x, cellRect.y);
    }

    public void shutDown()
    {}
}
