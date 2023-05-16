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
        while(true)
        {
            try
            {
                sleep(200);
            } catch (Exception e) {}

            int shiftX = 0;
            int shiftY = 0;

            switch( Direction.getRandomDirection() )
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

    GameController_Ghosts(GameController gameController)
    {
        this.gameController = gameController;
        ghostMover = new GhostMover(gameController.getGameModel().getGhost());
        ghostMover.start();
    }

    void shutDown()
    {
        ghostMover.interrupt();
    }
}
