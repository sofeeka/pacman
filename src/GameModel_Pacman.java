class GameModel_Pacman
{
    private GameModel gameModel;

    private int x;
    private int y;
    private Direction direction;

    GameModel_Pacman(GameModel gameModel, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.gameModel = gameModel;
        direction = Direction.STILL;
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
            gameModel.pointEaten();
            gameModel.setElementToEmpty( x, y );
        }

        modelChanged();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void modelChanged()
    {
        gameModel.modelChanged();
    }
}
