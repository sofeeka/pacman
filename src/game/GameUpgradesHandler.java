package game;

import game.upgrades.*;

class GamesUpgrades_Thread extends Thread
{
    Game game;
    public GamesUpgrades_Thread(Game game)
    {
        this.game = game;
    }
    @Override
    public void run()
    {
        while(true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                return;
            }

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
public class GameUpgradesHandler
{
    Game game;
    GamesUpgrades_Thread thread;
    public GameUpgradesHandler(Game game)
    {
        this.game = game;
        thread = new GamesUpgrades_Thread(game);
        thread.start();
    }

    public void shutDown()
    {
        thread.interrupt();
    }

}
