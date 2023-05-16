package game.pacman;

import game.GameController;
import game.GameModel;

class PacmanMover extends Thread
{
    GameModel_Pacman pacman;

    PacmanMover( GameModel_Pacman pacman )
    {
        this.pacman = pacman;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                sleep(200);
            } catch( InterruptedException e ) {
                return;
            }

            int shiftX = 0;
            int shiftY = 0;

            switch( pacman.getDirection() )
            {
                case UP -> shiftY = -1;
                case DOWN -> shiftY = 1;
                case LEFT -> shiftX = -1;
                case RIGHT -> shiftX = 1;
            }

            if( shiftX == 0 && shiftY == 0 )
                continue;

            int newX = pacman.getX() + shiftX;
            int newY = pacman.getY() + shiftY;

            //
            GameModel gameModel = pacman.getGameModel();

            // cannot move outside game board bounds
            if( newX < 0 || newX >= gameModel.getWidth() || newY < 0 || newY >= gameModel.getHeight() )
                continue;

            // cannot move to walls
            if( gameModel.elementIsWall(newX, newY ))
                continue;

            //
            gameModel.getPacman().setXY(newX, newY);
        }
    }
}

public class GameController_Pacman
{
    GameController gameController;
    PacmanMover mover;

    public GameController_Pacman(GameController gameController)
    {
        this.gameController = gameController;

        mover = new PacmanMover(gameController.getGameModel().getPacman());
        mover.start();
    }

    public void shutDown()
    {
        mover.interrupt();
    }
}
