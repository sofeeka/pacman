package game.ghost;

import game.Game;
import game.GameController;
import game.GameModel;

class GhostMover extends Thread
{
    GameModel_Ghost m_ghost;
    Game.Direction direction;
    GhostMover(GameModel_Ghost ghost)
    {
        this.m_ghost = ghost;
        direction = Game.Direction.STILL;
    }

    private boolean canGoInDirection( Game.Direction direction )
    {
        GameModel gameModel = m_ghost.getGhost().getGame().getModel();

        int shiftX = 0;
        int shiftY = 0;

        switch (direction) {
            case UP -> shiftY = -1;
            case DOWN -> shiftY = 1;
            case LEFT -> shiftX = -1;
            case RIGHT -> shiftX = 1;
        }

        if (shiftX == 0 && shiftY == 0)
            return false;

        int newX = m_ghost.getX() + shiftX;
        int newY = m_ghost.getY() + shiftY;

        // cannot move outside game board bounds
        if (newX < 0 || newX >= gameModel.getWidth() || newY < 0 || newY >= gameModel.getHeight())
            return false;

        // cannot move to walls
        if (gameModel.elementIsWall(newX, newY))
            return false;

        return true;
    }

    @Override
    public void run()
    {
        GameModel gameModel = m_ghost.getGhost().getGame().getModel();

        final int SLEEP_TIME = 500;

        while(true)
        {
            try
            {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                return;
            }

            if( m_ghost.isFrightened() )
            {
                m_ghost.decreaseRemainingFrightened( SLEEP_TIME );
            }

            if (direction == Game.Direction.STILL || Math.random() > 0.75) // continue with the prior move direction with some probability
                direction = Game.Direction.getRandomDirection();

            if( !canGoInDirection( direction )) {
                if( canGoInDirection( Game.Direction.LEFT ) )
                    direction = Game.Direction.LEFT;
                else
                if( canGoInDirection( Game.Direction.UP ) )
                    direction = Game.Direction.UP;
                else
                if( canGoInDirection( Game.Direction.RIGHT ) )
                    direction = Game.Direction.RIGHT;
                else
                if( canGoInDirection( Game.Direction.DOWN ) )
                    direction = Game.Direction.DOWN;
            }

            int shiftX = 0;
            int shiftY = 0;

            switch (this.direction) {
                case UP -> shiftY = -1;
                case DOWN -> shiftY = 1;
                case LEFT -> shiftX = -1;
                case RIGHT -> shiftX = 1;
            }

            if (shiftX == 0 && shiftY == 0)
                continue;

            int newX = m_ghost.getX() + shiftX;
            int newY = m_ghost.getY() + shiftY;

            // cannot move outside game board bounds
            if (newX < 0 || newX >= gameModel.getWidth() || newY < 0 || newY >= gameModel.getHeight())
                continue;

            // cannot move to walls
            if (gameModel.elementIsWall(newX, newY))
                continue;

            //
            m_ghost.setXY(newX, newY);
        }
    }
}
public class GameController_Ghost
{
    Ghost ghost;
    final GhostMover ghostMover;

    public GameController_Ghost(Ghost ghost)
    {
        this.ghost = ghost;
        ghostMover = new GhostMover(ghost.getModel());
        ghostMover.start();
    }

    public void shutDown()
    {
        ghostMover.interrupt();
    }
}