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
    private GameModel gameModel;

    private int x;
    private int y;
    private Direction direction;

    PacmanModel(GameModel gameModel, int x, int y)
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

public class GameModel {
    GameView gameView;
    private int width; // x
    private int height; // y
    private Element[][] gameBoard;
    private int userScore;
    private int lives;
    private PacmanModel pacman;
    private int SCORE_PER_POINT = 100;

    GameModel(int x, int y)
    {
        width = x;
        height = y;
        gameBoard = new Element[y][x];
        initGameBoard();
        userScore = 0;
        lives = 3;
        pacman = new PacmanModel( this,3, 3); //todo
    }

    void setGameView( GameView gameView )
    {
        this.gameView = gameView;
    }

    private void initGameBoard()
    {
        MazeGenerator generator = new MazeGenerator();
        int[][] maze = generator.getMaze( height, width );

        gameBoard = new Element[height][width];

        for (int row = 0; row < height ; row++) {
            for (int col = 0; col < width ; col++) {
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public PacmanModel getPacman() {
        return pacman;
    }

    public Element getElementAt( int x, int y )
    {
        return gameBoard[y][x];
    }

    public boolean elementIsWall( int x, int y )
    {
        return ( getElementAt( x, y ) == Element.WALL );
    }
    public boolean elementIsPoint( int x, int y )
    {
        return ( getElementAt( x, y ) == Element.POINT );
    }

    public void setElementToEmpty( int x, int y)
    {
        gameBoard[y][x] = Element.EMPTY;
//        System.out.println("eaten " + x + " " + y);
    }

    public int remainingPointsQty()
    {
        int qty = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (elementIsPoint(i, j))
                    qty++;
            }
        }
        return qty;
    }

    public void modelChanged()
    {
        gameView.modelChanged();
    }

    public void pointEaten()
    {
        this.userScore += SCORE_PER_POINT;
    }
}
