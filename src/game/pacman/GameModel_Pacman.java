package game.pacman;

import game.*;
public class GameModel_Pacman
{
    private final Pacman pacman;

    private Position pos;
    private Position microShift; // shift from current cell in the moving direction to animate movement between cells
    private Game.Direction direction;
    private Game.Direction nextDirection;
    private int speed; // how many ms it takes to move from one cell to another

    public final int MICRO_STEPS_QTY = 8;

    public GameModel_Pacman(Pacman pacman)
    {
        this.pacman = pacman;
        speed = 200;

        pos = pacman.getGame().getModel().getRandromPointPosition();
        direction = Game.Direction.STILL;
        nextDirection = Game.Direction.STILL;

        microShift = new Position();
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
        this.setMicroShift( new Position( 0, 0 ));

        int x = pos.getX();
        int y = pos.getY();

        GameModel gameModel = pacman.getGame().getModel();

        switch( gameModel.getElementAt( x, y ) )
        {
            case POINT        -> gameModel.pointEaten(x, y);
            case POWER_PELLET -> gameModel.powerPelletEaten( x, y );
        }

        gameModel.positionChanged();
    }

    synchronized public Position getMicroShift()
    {
        return microShift;
    }

    synchronized public void setMicroShift( Position p )
    {
        microShift = p;
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

    public void setNextDirection(Game.Direction direction)
    {
        this.nextDirection = direction;
    }
    public Game.Direction getNextDirection()
    {
        return this.nextDirection;
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
