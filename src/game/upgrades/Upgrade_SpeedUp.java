package game.upgrades;

import game.Game;
import game.GameModel;

import static java.lang.Thread.sleep;

public class Upgrade_SpeedUp extends UpgradeBasic
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
