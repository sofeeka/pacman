import javax.swing.*;
import java.awt.*;

public class GameModel_Ghost
{
    private GameModel gameModel;
    private int x;
    private int y;
    GameModel_Ghost(GameModel gameModel, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.gameModel = gameModel;
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

    public GameModel getGameModel() {
        return gameModel;
    }

    public void modelChanged()
    {
        gameModel.modelChanged();
    }
}
