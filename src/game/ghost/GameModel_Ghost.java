package game.ghost;

import game.GameModel;
import game.Position;

public class GameModel_Ghost
{
    Ghost ghost;
    private int x;
    private int y;
    private int remainingFrightened;

    public GameModel_Ghost(Ghost ghost)
    {
        this.ghost = ghost;

        this.remainingFrightened = 0;

        Position p = ghost.getGame().getModel().getRandromPointPosition();

        this.x = p.getX();
        this.y = p.getY();
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

        modelChanged();
    }

    private void setRemainingFrightened( int value )
    {
        this.remainingFrightened = value;

        if( this.remainingFrightened < 0 )
            this.remainingFrightened = 0;

        this.modelChanged();
    }

    public void setAsFrightened()
    {
        this.setRemainingFrightened( 5000 ); // 5 sec
    }

    public void setAsNotFrightened()
    {
        this.setRemainingFrightened( 0 ); // 5 sec
    }

    public boolean isFrightened()
    {
        return ( this.remainingFrightened > 0 );
    }

    public void decreaseRemainingFrightened( int ms )
    {
        this.setRemainingFrightened( this.remainingFrightened - ms );
    }

    public void modelChanged()
    {
        ghost.getGame().getModel().modelChanged();
    }

    public Ghost getGhost() {
        return ghost;
    }
    public void shutDown()
    {
    }

}
