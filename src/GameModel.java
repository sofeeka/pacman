import java.util.concurrent.TimeUnit;

public class GameModel {
    private int width; // x
    private int height; // y
    private Element[][] gameBoard;

    private int userScore;
    private int lives;

    GameModel(int x, int y)
    {
        width = x;
        height = y;
        gameBoard = new Element[y][x];
        initGameBoard();
        userScore = 0;
        lives = 3;
    }

    private void initGameBoard()
    {
        for (int i = 0; i < gameBoard.length; i++)
        {
            for (int j = 0; j < gameBoard[0].length; j++)
            {
                gameBoard[i][j] = Element.getRandomElement();
            }
        }
    }
    public void setDimensions(int x, int y)
    {
        this.width = x;
        this.height = y;
    }

    public Element[][] getGameBoard()
    {
        return  this.gameBoard;
    }
    public int getUserScore() {
        return userScore;
    }
    public int getLives() {
        return lives;
    }

}
