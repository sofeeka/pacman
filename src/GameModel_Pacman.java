class GameModel_Pacman
{
    private final GameModel gameModel;
    private int x;
    private int y;
    private Direction direction;

    GameModel_Pacman(GameModel gameModel)
    {
        this.gameModel = gameModel;

        Position p = gameModel.getRandromPointPosition();
        this.x = p.getX();
        this.y = p.getY();

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

    public GameModel getGameModel() {
        return gameModel;
    }
}
