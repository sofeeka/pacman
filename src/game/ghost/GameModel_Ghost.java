package game.ghost;

import game.Position;

public class GameModel_Ghost
{
    Ghost ghost;
    private Position pos;
    private int remainingFrightened; // milliseconds

    public GameModel_Ghost(Ghost ghost)
    {
        this.ghost = ghost;

        this.pos = ghost.getGame().getModel().getRandromPointPosition();
        this.remainingFrightened = 0;
    }
    public int getX() {
        return pos.getX();
    }

    public int getY() {
        return pos.getY();
    }

    public void setXY( int x, int y )
    {
        this.pos.setXY( x, y );

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
