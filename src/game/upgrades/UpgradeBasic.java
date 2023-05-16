package game.upgrades;

import game.Game;

abstract class UpgradeBasic implements Runnable
{
    protected Game game;
    UpgradeBasic(Game game)
    {
        this.game = game;
    }
    @Override
    public void run()
    {
        applyUpgrade();
    }
    abstract void applyUpgrade();

}
