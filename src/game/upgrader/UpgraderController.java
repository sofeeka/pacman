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

            //

            //
            if( Math.random() > 0.25 )
                continue;

            //
            UpgradeBasic upgrade = createRandomUpgrade();
            if (upgrade != null)
            {
                Thread thread = new Thread(upgrade);
                thread.start();
            }
        }
    }
    private UpgradeBasic createRandomUpgrade()
    {
        Game.Upgrade upgrade = Game.Upgrade.getRandomUpgrade();

        switch (upgrade)
        {
            case SPEED_UP : return new Upgrade_SpeedUp(game);
            case FREEZE_GHOSTS : return new Upgrade_FreezeGhosts(game);
            case HIDE_GHOSTS : return new Upgrade_HideGhosts(game);
            case BOOST_SCORE : return new Upgrade_BoostScore(game);
//            default : return null;
        }
        return null;
    }
}
public class UpgraderController {
    Upgrader upgrader;
    GameUpgradeThrower thrower;

    public UpgraderController(Upgrader upgrader) {
        this.upgrader = upgrader;

        thrower = new GameUpgradeThrower(game);
        thrower.start();
    }

    public void shutDown()
    {
        thrower.interrupt();
    }
}
