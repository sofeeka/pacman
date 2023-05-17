package game;

import game.ghost.Ghost;
import game.maze.*;
import game.ghost.GameModel_Ghost;
import game.pacman.GameModel_Pacman;

public class GameModel {
    Game game;
    private int width; // x
    private int height; // y
    private Game.Element[][] gameBoard;
    private int userScore;
    private int lives;
    private double scoreMultiplier;
    private final int SCORE_PER_POINT = 100;
    private final int SCORE_PER_POWER_PELLET = 200;
    private final int SCORE_PER_GHOST = 400;

    GameModel(Game game, int x, int y)
    {
        this.game = game;

        width = x;
        height = y;

        gameBoard = new Game.Element[y][x];
        initGameBoard();

        userScore = 0;
        lives = 3;
        scoreMultiplier = 1;
    }

    public GameView getGameView() {
        return game.getView();
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

        gameBoard = new Game.Element[height][width];

        for (int row = 0; row < height ; row++) {
            for (int col = 0; col < width ; col++) {
                gameBoard[row][col] = ( maze[row][col] == 1 ) ? Game.Element.WALL : Game.Element.POINT;
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

    public Game.Element[][] getGameBoard()
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

    public Game.Element getElementAt(int x, int y )
    {
        return gameBoard[y][x];
    }
    public boolean elementIs( int x, int y, Game.Element element )
    {
        return ( getElementAt( x, y ) == element );
    }
    public boolean elementIsWall( int x, int y )
    {
        return elementIs( x, y, Game.Element.WALL );
    }
    public boolean elementIsPoint( int x, int y )
    {
        return elementIs( x, y, Game.Element.POINT );
    }
    public boolean elementIsEmpty( int x, int y )
    {
        return elementIs( x, y, Game.Element.EMPTY);
    }

    public void setElementTo(int x, int y, Game.Element element )
    {
        gameBoard[y][x] = element;
    }
    public void setElementToEmpty( int x, int y)
    {
        setElementTo(x, y, Game.Element.EMPTY);
    }
    public void setElementToPoint( int x, int y)
    {
        setElementTo(x, y, Game.Element.POINT);
    }

    public void setScoreMultiplier(double scoreMultiplier)
    {
        this.scoreMultiplier = scoreMultiplier;
    }

    public double getScoreMultiplier() {
        return scoreMultiplier;
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

        return qty;
    }

    public void modelChanged()
    {
        GameModel_Pacman m_pacman = game.getPacman().getModel();

        for( Ghost ghost : game.ghosts ) {
            GameModel_Ghost m_ghost = ghost.getModel();

            if (m_pacman.getX() == m_ghost.getX() && m_pacman.getY() == m_ghost.getY()) {
                if (m_ghost.isFrightened())
                    ghostEaten(m_ghost);
                else {
                    pacmanEaten();
                    break;
                }
            }
        }

        // Update view
        getGameView().modelChanged();
    }

    public void pointEaten()
    {
        this.userScore += SCORE_PER_POINT * scoreMultiplier;

        if( getRemainingPointsQty() == 0)
        {
            game.userWon();
        }
    }
    public void ghostEaten( GameModel_Ghost ghost )
    {
        this.userScore += SCORE_PER_GHOST * scoreMultiplier;

        Position p = this.getRandromPointPosition();
        ghost.setPos( p );
        ghost.setAsNotFrightened();
    }
    public void powerPelletEaten()
    {
        this.userScore += SCORE_PER_POWER_PELLET * scoreMultiplier;

        // Frighten all ghosts
        for( Ghost ghost : game.ghosts ) {
            ghost.getModel().setAsFrightened();
        }
    }

    private void pacmanEaten()
    {
        this.lives--;
        if( this.lives == 0 ) {
            game.stopGame();
            return;
        }

        //
        this.game.getPacman().getModel().setDirection(Game.Direction.STILL);

        // set pacman to a new random position
        Position p = this.getRandromPointPosition();
        this.game.getPacman().getModel().setPos( p );

        // set all empty elements to POINT
//        this.restartBoard();

        //
        // this.userScore = 0;
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
                if (elementIsEmpty(i, j) || elementIs(i, j, Game.Element.POWER_PELLET))
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
            setElementTo(p.getX(), p.getY(), Game.Element.POWER_PELLET);
        }
    }

    public Game getGame() {
        return game;
    }

    void shutDown()
    {}
}
