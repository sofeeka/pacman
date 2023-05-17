package game.pacman;

import game.*;
public class GameModel_Pacman
{
    private final Pacman pacman;

    private Position pos;
    private Game.Direction direction;
    private int speed; // how many ms it takes to move from one cell to another

    public GameModel_Pacman(Pacman pacman)
    {
        this.pacman = pacman;
        speed = 200;

        pos = pacman.getGame().getModel().getRandromPointPosition();
        direction = Game.Direction.STILL;
    }

    public int getX() { return pos.getX(); }
    public int getY() {
        return pos.getY();
    }

    public Position getPos()
    {
        return pos;
    }
    public void setPos( Position pos )
    {
        this.pos = pos;

        int x = pos.getX();
        int y = pos.getY();

        GameModel gameModel = pacman.getGame().getModel();

        switch( gameModel.getElementAt( x, y ) )
        {
            case POINT -> {
                gameModel.pointEaten(x, y);
            }

            case POWER_PELLET -> {
                gameModel.powerPelletEaten( x, y );
            }
        }

        gameModel.positionChanged();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void speedUp(double multiplier)
    {
        setSpeed((int)(getSpeed()/multiplier)); // decrease time needed to move between cells
    }

    public void setDirection(Game.Direction direction) {
        this.direction = direction;
    }

    public Game.Direction getDirection() {
        return direction;
    }

    public void modelChanged()
    {
        pacman.getGame().getModel().modelChanged();
    }

    public void shutDown()
    {
    }

    public Pacman getPacman() {
        return pacman;
    }
}
