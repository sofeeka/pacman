import java.awt.event.WindowEvent;

public class GameModel {
    GameView gameView;
    private int width; // x
    private int height; // y
    private Element[][] gameBoard;
    private int userScore;
    private int lives;
    private final GameModel_Pacman pacman;
    private final GameModel_Ghost ghost;
    private final int SCORE_PER_POINT = 100;
    private final int SCORE_PER_POWER_PELLET = 200;
    private final int SCORE_PER_GHOST = 400;

    GameModel(int x, int y)
    {
        width = x;
        height = y;

        gameBoard = new Element[y][x];
        initGameBoard();

        userScore = 0;
        lives = 3;

        pacman = new GameModel_Pacman(this);
        ghost = new GameModel_Ghost(this);
    }

    void setGameView( GameView gameView )
    {
        this.gameView = gameView;
    }

    public GameView getGameView() {
        return gameView;
    }

    private void initGameBoard()
    {
        int mazeX = width;
        int mazeY = height;

        if ( mazeX % 2 == 0 )
            mazeX++;
        if ( mazeY % 2 == 0 )
            mazeY++;

        MazeGenerator generator = new MazeGenerator();
        int[][] maze = generator.getMaze( mazeY, mazeX );

        gameBoard = new Element[height][width];

        for (int row = 0; row < height ; row++) {
            for (int col = 0; col < width ; col++) {
                gameBoard[row][col] = ( maze[row][col] == 1 ) ? Element.WALL : Element.POINT;
            }
        }

        MazeImprover.improveMaze( this );
        restartBoard();
    }

    public void setDimensions(int x, int y)
    {
        this.width = x;
        this.height = y;
    }

    public Element[][] getGameBoard()
    {
        return this.gameBoard;
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

    public GameModel_Pacman getPacman() {
        return pacman;
    }

    public GameModel_Ghost getGhost() {
        return ghost;
    }

    public Element getElementAt(int x, int y )
    {
        return gameBoard[y][x];
    }

    public boolean elementIs( int x, int y, Element element )
    {
        return ( getElementAt( x, y ) == element );
    }
    public boolean elementIsWall( int x, int y )
    {
        return elementIs( x, y, Element.WALL );
    }
    public boolean elementIsPoint( int x, int y )
    {
        return elementIs( x, y, Element.POINT );
    }
    public boolean elementIsEmpty( int x, int y )
    {
        return elementIs( x, y, Element.EMPTY);
    }

    public void setElementTo(int x, int y, Element element )
    {
        gameBoard[y][x] = element;
    }
    public void setElementToEmpty( int x, int y)
    {
        setElementTo(x, y, Element.EMPTY);
    }

    public void setElementToPoint( int x, int y)
    {
        setElementTo(x, y, Element.POINT);
    }

    public int getRemainingPointsQty()
    {
        int qty = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (elementIsPoint(i, j))
                    qty++;
            }
        }

        if (qty == 0) //todo: end game frame
            createWinningFrame(getUserScore());
            /*addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    EndGameFrame endGameFrame = new EndGameFrame(gameModel.getUserScore());
                }
            });*/
        return qty;
    }

    private void createWinningFrame(int userScore)
    {
        WinningFrame winningFrame = new WinningFrame(userScore);
    }

    public void modelChanged()
    {
        if( pacman.getX() == ghost.getX() && pacman.getY() == ghost.getY() )
        {
            if( ghost.isFrightened() )
                ghostEaten( ghost );
            else
                pacmanEaten();
        }

        gameView.modelChanged();
    }

    public void pointEaten()
    {
        this.userScore += SCORE_PER_POINT;
    }

    public void ghostEaten( GameModel_Ghost ghost )
    {
        this.userScore += SCORE_PER_GHOST;

        Position p = this.getRandromPointPosition();
        ghost.setXY( p.getX(), p.getY() );
        ghost.setAsNotFrightened();
    }

    public void powerPelletEaten()
    {
        this.userScore += SCORE_PER_POWER_PELLET;
        this.ghost.setAsFrightened();
    }

    private void pacmanEaten()
    {
        this.lives--;
        if( this.lives == 0 ) {
            gameView.setVisible(false);
            gameView.dispatchEvent(new WindowEvent(gameView, WindowEvent.WINDOW_CLOSING));
            gameView.dispose();
            return;
        }

        //
        this.pacman.setDirection(Direction.STILL);

        // set pacman to a new random position
        Position p = this.getRandromPointPosition();
        this.pacman.setXY(p.getX(), p.getY() );

        // set all empty elements to POINT
        this.restartBoard();

        //
        this.userScore = 0;
    }

    public Position getRandromPointPosition()
    {
        int x;
        int y = ( int )( Math.random() * ( this.height - 2 ) ) + 1;

        while( true )
        {
            x = ( int )( Math.random() * ( this.width - 2 ) ) + 1;
            if( this.elementIsPoint( x, y ) )
                break;
        }

        Position pos = new Position();
        pos.setXY( x, y );
        return pos;
    }

    void restartBoard()
    {
        // set all empty elements to POINT
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (elementIsEmpty(i, j) || elementIs(i, j, Element.POWER_PELLET))
                    setElementToPoint(i, j);
            }
        }

        //
        int pointsQty = getRemainingPointsQty();

        int pelletsQty = pointsQty / 25;
        if( pelletsQty < 1 )
            pelletsQty = 1;

        //
        for( int i = 0; i < pelletsQty; i++ ) {
            Position p = this.getRandromPointPosition();
            setElementTo(p.getX(), p.getY(), Element.POWER_PELLET);
        }
    }
}
