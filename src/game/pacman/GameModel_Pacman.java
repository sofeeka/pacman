package game.pacman;

import game.*;
public class GameModel_Pacman
{
    private final Pacman pacman;

    private Position pos;
    private Game.Direction direction;

    public GameModel_Pacman(Pacman pacman)
    {
        this.pacman = pacman;

        pos = pacman.getGame().getModel().getRandromPointPosition();
        direction = Game.Direction.STILL;
    }

    public int getX() { return pos.getX(); }

    public int getY() {
        return pos.getY();
    }

    public void setXY( int x, int y )
    {
        pos.setXY( x, y );

        GameModel gameModel = pacman.getGame().getModel();

        if(gameModel.elementIsPoint(x, y))
        {
            gameModel.setElementToEmpty( x, y );
            gameModel.pointEaten();
        }

        if(gameModel.elementIs(x, y, Game.Element.POWER_PELLET))
        {
            gameModel.setElementToEmpty( x, y );
            gameModel.powerPelletEaten();
        }

        modelChanged();
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
