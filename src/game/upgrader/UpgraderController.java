package game.upgrader;

import game.Game;
import game.upgrades.*;

class GameUpgradeThrower extends Thread
{
    Upgrader upgrader;
    public GameUpgradeThrower(Upgrader upgrader)
    {
        this.upgrader = upgrader;
    }

    @Override
    public void run()
    {
        while(true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                return;
            }

            // old upgrade still exists, do not throw a new one
            if ( upgrader.getModel().isThrown())
                continue;

            // Throw a new upgrade with 25% probability
            if( Math.random() > 0.25 )
                continue;

            //
            upgrader.getModel().setAsThrown();
        }
    }
}
public class UpgraderController {
    Upgrader upgrader;
    GameUpgradeThrower thrower;

    public UpgraderController(Upgrader upgrader) {
        this.upgrader = upgrader;

        thrower = new GameUpgradeThrower(upgrader);
        thrower.start();
    }

    public void shutDown()
    {
        thrower.interrupt();
    }
}
