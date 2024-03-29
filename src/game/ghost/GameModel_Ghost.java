package game.ghost;

import game.Position;

public class GameModel_Ghost
{
    Ghost ghost;
    private Position pos;
    private int remainingFrightened;
    private boolean isFrozen;
    private boolean isHidden;
    private boolean isFriendly;

    public GameModel_Ghost(Ghost ghost)
    {
        this.ghost = ghost;
        isFrozen = false;

        this.pos = ghost.getGame().getModel().getRandomNonWallPosition();
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

        if ( ghost.getModel().isFriendly && ghost.getGame().getModel().elementIsPoint(pos.getX(), pos.getY()))
            ghost.getGame().getModel().pointEaten(pos.getX(), pos.getY());

        ghost.getGame().getModel().positionChanged();
        modelChanged();
    }

    private void setRemainingFrightened(int value )
    {
        this.remainingFrightened = value;

        if( this.remainingFrightened < 0 )
            this.remainingFrightened = 0;

        this.modelChanged();
    }

    public void setAsFrightened(int time)
    {
        this.setRemainingFrightened( time );
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

    public boolean isHidden() {
        return isHidden;
    }

    public void setAsHidden(boolean hidden) {
        if (!hidden)
            ghost.getModel().setAsFrightened(2000);
        isHidden = hidden;
    }

    public boolean isFriendly() {
        return isFriendly;
    }

    public void setAsFriendly(boolean friendly) {
        isFriendly = friendly;
    }

    public void modelChanged()
    {
        ghost.getGame().getModel().modelChanged();
    }

    public Position getPos() {
        return pos;
    }

    public Ghost getGhost() {
        return ghost;
    }

    public void shutDown()
    {
    }

}
