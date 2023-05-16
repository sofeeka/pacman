package game.ghost;

import game.Game;
import game.GameController;
import game.GameModel;

class GhostMover extends Thread
{
    GameModel_Ghost ghost;
    GhostMover(GameModel_Ghost ghost)
    {
        this.ghost = ghost;
    }

    @Override
    public void run()
    {
        GameModel gameModel = ghost.getGameModel();

        final int SLEEP_TIME = 200;

        while(true)
        {
            try
            {
                sleep(SLEEP_TIME);
            } catch (Exception e) {}

            if( ghost.isFrightened() )
            {
                ghost.decreaseRemainingFrightened( SLEEP_TIME );
            }

            int shiftX = 0;
            int shiftY = 0;

            switch( Game.Direction.getRandomDirection() )
            {
                case UP -> shiftY = -1;
                case DOWN -> shiftY = 1;
                case LEFT -> shiftX = -1;
                case RIGHT -> shiftX = 1;
            }

            if( shiftX == 0 && shiftY == 0 )
                continue;

            int newX = ghost.getX() + shiftX;
            int newY = ghost.getY() + shiftY;

            // cannot move outside game board bounds
            if( newX < 0 || newX >= gameModel.getWidth() || newY < 0 || newY >= gameModel.getHeight() )
                continue;

            // cannot move to walls
            if( gameModel.elementIsWall(newX, newY ))
                continue;

            //
            ghost.setXY(newX, newY);
        }
    }
}
public class GameController_Ghosts
{
    GameController gameController;
    GhostMover ghostMover;

    public GameController_Ghosts(GameController gameController)
    {
        this.gameController = gameController;
        ghostMover = new GhostMover(gameController.getGameModel().getGhost());
        ghostMover.start();
    }

    public void shutDown()
    {
        ghostMover.interrupt();
    }
}
