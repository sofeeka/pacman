package game;

import game.upgrades.Upgrade_FreezeGhosts;
import game.upgrades.Upgrade_SpeedUp;

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

            Thread thread = new Thread(new Upgrade_SpeedUp(game));
            thread.start();

            Thread thread1 = new Thread(new Upgrade_FreezeGhosts(game));
            thread1.start();
        }
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
