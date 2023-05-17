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
        // Update view
        getGameView().modelChanged();
    }

    synchronized public void positionChanged()
    {
        GameModel_Pacman m_pacman = game.getPacman().getModel();

        // Check if pacman ate any frightened ghost or any ghost ate pacman
        for( Ghost ghost : game.getGhosts() ) {
            GameModel_Ghost m_ghost = ghost.getModel();

            if (m_ghost.isHidden())
                continue;

            if (m_pacman.getX() == m_ghost.getX() && m_pacman.getY() == m_ghost.getY()) {
                if (m_ghost.isFrightened())
                    ghostEaten(m_ghost);
                else {
                    pacmanEaten();
                    break;
                }
            }
        }

        // Check if pacman ate any upgrade
        if(game.getUpgrader().getModel().isThrown())
        {
            if( m_pacman.getPos().isSame( game.getUpgrader().getModel().getPos() ))
            {
                upgradeEaten();
            }
        }
    }
    public void pointEaten( int x, int y )
    {
        setElementToEmpty( x, y );
        addScore( SCORE_PER_POINT );

        if( getRemainingPointsQty() == 0)
        {
            game.userWon();
        }

        getGameView().updatePointsLeftLabel();
    }
    public void ghostEaten( GameModel_Ghost ghost )
    {
        addScore( SCORE_PER_GHOST );

        Position p = this.getRandromPointPosition();
        ghost.setPos( p );
        ghost.setAsNotFrightened();
    }
    public void powerPelletEaten( int x, int y )
    {
        setElementToEmpty( x, y );
        addScore( SCORE_PER_POWER_PELLET );

        // Frighten all ghosts
        for( Ghost ghost : game.getGhosts() ) {
            ghost.getModel().setAsFrightened();
        }
    }

    private void pacmanEaten()
    {
        this.lives--;
        game.getView().updateLivesLabel();

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

    private void upgradeEaten()
    {
        getGame().getUpgrader().getModel().setAsEaten();
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

    public Position getNewPosInDirection( Position p, Game.Direction direction )
    {
        Position newPos = new Position( p );

        int shiftX = 0;
        int shiftY = 0;

        switch (direction) {
            case UP    -> shiftY = -1;
            case DOWN  -> shiftY = 1;
            case LEFT  -> shiftX = -1;
            case RIGHT -> shiftX = 1;
        }

        if (shiftX == 0 && shiftY == 0)
            return newPos;

        newPos.setXY( p.getX() + shiftX, p.getY() + shiftY );
        return newPos;
    }
    public boolean canGoInDirection( int x, int y, Game.Direction direction )
    {
        Position newPos = getNewPosInDirection(new Position( x, y ), direction);

        // cannot move outside game board bounds
        if (newPos.getX() < 0 || newPos.getX() >= this.getWidth() || newPos.getY() < 0 || newPos.getY() >= this.getHeight())
            return false;

        // cannot move to walls
        if (this.elementIsWall(newPos.getX(), newPos.getY()))
            return false;

        return true;
    }

    private void addScore( int value )
    {
        setUserScore( this.userScore + ( int )( value * scoreMultiplier ) );
    }
    private void setUserScore( int score )
    {
        this.userScore = score;
        game.getView().updateScoreLabel();
    }

    public Game getGame() {
        return game;
    }

    void shutDown()
    {}
}
