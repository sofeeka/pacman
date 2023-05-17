package game.upgrader;

class GameUpgradeThrower extends Thread
{
    Upgrader upgrader;
    public GameUpgradeThrower(Upgrader upgrader)
    {
        this.upgrader = upgrader;
        setName( "Upgrade thrower" );
    }

    @Override
    public void run()
    {
        while(!Thread.interrupted()) {
            synchronized (this){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    return;
                }

                // old upgrade still exists, do not throw a new one
                if (upgrader.getModel().isDropped())
                    continue;

                // Throw a new upgrade with 25% probability
                if (Math.random() > 0.25)
                    continue;

                //
                upgrader.getModel().setAsDropped();
            }
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
