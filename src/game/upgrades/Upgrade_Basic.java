package game.upgrades;

import game.Game;

abstract public class Upgrade_Basic implements Runnable
{
    protected Game game;
    protected String title;
    Upgrade_Basic(Game game)
    {
        this.game = game;
    }
    @Override
    public void run()
    {
        game.getView().setUpgradeLabelText(title);
        applyUpgrade();
        game.getView().setUpgradeLabelText("");
    }
    abstract void applyUpgrade();

}
