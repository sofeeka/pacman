package game.pacman;

import game.Game;
import game.GameModel;
import game.Position;

class PacmanMover extends Thread
{
    GameModel_Pacman m_pacman;

    PacmanMover( GameModel_Pacman pacman )
    {
        this.m_pacman = pacman;
        setName( "Pacman mover" );
    }

    @Override
    public void run()
    {
        final GameModel gameModel = m_pacman.getPacman().getGame().getModel();

        while(!Thread.interrupted())
        {
            synchronized (this){
                if( m_pacman.getNextDirection() != Game.Direction.STILL ) {
                    if (m_pacman.getPacman().getGame().getModel().canGoInDirection(m_pacman.getPos(), m_pacman.getNextDirection())) {
                        m_pacman.setDirection( m_pacman.getNextDirection() );
                        m_pacman.setNextDirection( Game.Direction.STILL );
                    }
                }

                int speed = m_pacman.getSpeed();
                Game.Direction direction = m_pacman.getDirection();
                boolean canGoInDirection = gameModel.canGoInDirection(m_pacman.getPos(), direction);
                Position newPos = gameModel.getNewPosInDirection(m_pacman.getPos(), direction);

                try {
                    if (direction == Game.Direction.STILL || !canGoInDirection) {
                        Thread.sleep(speed);
                        continue;
                    } else {
                        // micro shifts
                        for (int i = 0; i < m_pacman.MICRO_STEPS_QTY; i++) {
                            Thread.sleep(speed / m_pacman.MICRO_STEPS_QTY);

                            switch (direction) {
                                case LEFT -> m_pacman.setMicroShift(new Position(-i, 0));
                                case RIGHT -> m_pacman.setMicroShift(new Position(i, 0));
                                case UP -> m_pacman.setMicroShift(new Position(0, -i));
                                case DOWN -> m_pacman.setMicroShift(new Position(0, i));
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    return;
                }

                m_pacman.setPos(newPos);
            }
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
