package game.upgrades;

import game.Game;

public class Upgrade_SpeedUp extends Upgrade_Basic
{
    public Upgrade_SpeedUp(Game game)
    {
        super(game);
        title = "SPEED UP";
    }
    @Override
    void applyUpgrade()
    {
        final int speed = game.getPacman().getModel().getSpeed();
        game.getPacman().getModel().speedUp(1.5);

        try{
            Thread.sleep(5000);
        } catch ( InterruptedException e )
        {
            return;
        }

        game.getPacman().getModel().setSpeed(speed);
    }
}
