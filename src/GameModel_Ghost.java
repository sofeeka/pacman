public class GameModel_Ghost
{
    private final GameModel gameModel;
    private int x;
    private int y;

    private int remainingFrightened;

    GameModel_Ghost(GameModel gameModel)
    {
        this.gameModel = gameModel;

        this.remainingFrightened = 0;

        Position p = gameModel.getRandromPointPosition();

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

    public GameModel getGameModel() {
        return gameModel;
    }

    public void modelChanged()
    {
        gameModel.modelChanged();
    }
}
