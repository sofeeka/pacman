package game.pacman;

import game.GameModel;
import game.Position;

class PacmanMover extends Thread
{
    GameModel_Pacman m_pacman;

    PacmanMover( GameModel_Pacman pacman )
    {
        this.m_pacman = pacman;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                sleep(m_pacman.getSpeed());
            } catch( InterruptedException e ) {
                return;
            }

            int shiftX = 0;
            int shiftY = 0;

            switch( m_pacman.getDirection() )
            {
                case UP -> shiftY = -1;
                case DOWN -> shiftY = 1;
                case LEFT -> shiftX = -1;
                case RIGHT -> shiftX = 1;
            }

            if( shiftX == 0 && shiftY == 0 )
                continue;

            int newX = m_pacman.getX() + shiftX;
            int newY = m_pacman.getY() + shiftY;

            //
            GameModel gameModel = m_pacman.getPacman().getGame().getModel();

            // cannot move outside game board bounds
            if( newX < 0 || newX >= gameModel.getWidth() || newY < 0 || newY >= gameModel.getHeight() )
                continue;

            // cannot move to walls
            if( gameModel.elementIsWall(newX, newY ))
                continue;

            //
            m_pacman.setPos(new Position(newX, newY));
        }
    }
}

public class GameController_Pacman
{
    Pacman pacman;
    PacmanMover mover;

    public GameController_Pacman(Pacman pacman)
    {
        this.pacman = pacman;

        mover = new PacmanMover(pacman.getModel());
        mover.start();
    }

    public void shutDown()
    {
        mover.interrupt();
    }
}
