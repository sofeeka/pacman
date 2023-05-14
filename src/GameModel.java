import java.io.File;
import java.util.concurrent.TimeUnit;
enum Direction
{
    STILL,
    UP,
    DOWN,
    LEFT,
    RIGHT;
}
class PacmanModel
{
    private int x;
    private int y;
    private Direction direction;

    PacmanModel(int x, int y)
    {
        this.x = x;
        this.y = y;
        direction = Direction.STILL;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}

public class GameModel {
    private int width; // x
    private int height; // y
    private Element[][] gameBoard;
    private int userScore;
    private int lives;
    private PacmanModel pacman;

    GameModel(int x, int y)
    {
        width = x;
        height = y;
        gameBoard = new Element[y][x];
        initGameBoard();
        userScore = 0;
        lives = 3;
        pacman = new PacmanModel(3, 3);
    }

    private void initGameBoard()
    {
        MazeGenerator generator = new MazeGenerator();
        int[][] maze = generator.getMaze( height, width );

        gameBoard = new Element[height][width];

        for (int row = 0; row < height - 1; row++) {
            for (int col = 0; col < width - 1; col++) {
                gameBoard[row][col] = ( maze[row][col] == 1 ) ? Element.WALL : Element.POINT;
            }
        }


//        for (int i = 0; i < gameBoard.length; i++)
//        {
//            for (int j = 0; j < gameBoard[0].length; j++)
//            {
//                gameBoard[i][j] = Element.getRandomElement();
//            }
//        }
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

    public PacmanModel getPacman() {
        return pacman;
    }
}
