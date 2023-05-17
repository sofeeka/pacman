package game.pacman;

import game.Game;
import game.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

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
    Pacman pacman;
    GameView gameView;
    ImageIcon fullSizeIconOpen;
    ImageIcon fullSizeIconClosed;
    ImageIcon renderingIconOpen;
    ImageIcon renderingIconClosed;
    private boolean open;
    private final PacmanIconChanger pacmanIconChanger;

    int width;
    int height;
    Game.Direction direction;

    public GameView_Pacman(Pacman pacman)
    {
        this.pacman = pacman;
        this.gameView = pacman.getGame().getView();
        fullSizeIconOpen = new ImageIcon("images\\pacman.half.png");
        fullSizeIconClosed = new ImageIcon("images\\pacman.full.png");

        open = true;
        direction = Game.Direction.STILL;

        pacmanIconChanger = new PacmanIconChanger(this);
        pacmanIconChanger.start();
    }

    private Image rotateImagePerDirection( Image originalImage, Game.Direction direction )
    {
        if(direction == Game.Direction.RIGHT)
            return originalImage;

        int rotationAngle = 0;

        switch( direction )
        {
            case UP -> rotationAngle = 270;
            case LEFT -> rotationAngle = 180;
            case DOWN -> rotationAngle = 90;
        }

        int width = originalImage.getWidth(null);
        int height = originalImage.getHeight(null);

        // Create a new BufferedImage with rotated dimensions
        BufferedImage rotatedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);

        // Create an AffineTransform to rotate the image
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotationAngle), height / 2, height / 2);

        // Get the graphics object of the rotated image
        Graphics2D g2d = rotatedImage.createGraphics();

        // Apply the transformation and draw the original image onto the rotated image
        g2d.drawImage(originalImage, transform, null);
        g2d.dispose();

//        return Toolkit.getDefaultToolkit().createImage(rotatedImage.getSource());
        return rotatedImage;
    }
    public void render( JTable t, Graphics g )
    {
        Rectangle cellRect = t.getCellRect(pacman.getModel().getY(), pacman.getModel().getX(), true);

        if(this.width != cellRect.width || this.height != cellRect.height || pacman.getModel().getDirection() != this.direction || renderingIconOpen == null )
        {
            Image open = fullSizeIconOpen.getImage();
            open = rotateImagePerDirection( open, pacman.getModel().getDirection());
            Image resizedOpen = open.getScaledInstance(cellRect.width, cellRect.height, Image.SCALE_SMOOTH);
            renderingIconOpen = new ImageIcon(resizedOpen);

            Image closed = fullSizeIconClosed.getImage();
            closed = rotateImagePerDirection( closed, pacman.getModel().getDirection());
            Image resizedClosed = closed.getScaledInstance(cellRect.width, cellRect.height, Image.SCALE_SMOOTH);
            renderingIconClosed = new ImageIcon(resizedClosed);
        }

        int x = cellRect.x;
        int y = cellRect.y;

        getRenderingIcon().paintIcon(t, g, x, y);
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
