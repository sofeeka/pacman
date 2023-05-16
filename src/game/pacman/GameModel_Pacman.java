package game.pacman;

import game.*;
public class GameModel_Pacman
{
    private Pacman pacman;
    private final GameModel gameModel;
    private int x;
    private int y;
    private Game.Direction direction;

    public GameModel_Pacman(Pacman pacman)
    {
        this.pacman = pacman;
        this.gameModel = pacman.getGame().getModel();

        Position p = gameModel.getRandromPointPosition();
        this.x = p.getX();
        this.y = p.getY();

        direction = Game.Direction.STILL;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setXY( int x, int y )
    {
        this.x = x;
        this.y = y;

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
        gameModel.modelChanged();
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void shutDown()
    {
    }
}
