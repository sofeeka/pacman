package game.ghost;

import game.Position;

public class GameModel_Ghost
{
    Ghost ghost;
    private Position pos;
    private int remainingFrightened; // milliseconds
    private boolean isFrozen;

    public GameModel_Ghost(Ghost ghost)
    {
        this.ghost = ghost;
        isFrozen = false;

        this.pos = ghost.getGame().getModel().getRandromPointPosition();
        this.remainingFrightened = 0;
    }
    public int getX() {
        return pos.getX();
    }

    public int getY() {
        return pos.getY();
    }

    public void setPos(Position pos) {
        this.pos = pos;
        modelChanged();
    }

    private void setRemainingFrightened(int value )
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

    public void setAsFrozen(boolean isFrozen)
    {
        this.isFrozen = isFrozen;
    }

    public boolean isFrozen() {
        return isFrozen;
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
