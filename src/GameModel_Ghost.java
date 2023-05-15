public class GameModel_Ghost
{
    private final GameModel gameModel;
    private int x;
    private int y;
    GameModel_Ghost(GameModel gameModel)
    {
        this.gameModel = gameModel;

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

    public GameModel getGameModel() {
        return gameModel;
    }

    public void modelChanged()
    {
        gameModel.modelChanged();
    }
}
