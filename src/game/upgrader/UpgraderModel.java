package game.upgrader;

import game.Game;
import game.Position;
import game.upgrades.*;

public class UpgraderModel {
    private final Upgrader upgrader;

    boolean isThrown; // upgrade icon is shown in the game field
    private Position pos; // location where the upgrade icon is shown

    public UpgraderModel(Upgrader upgrader) {
        this.upgrader = upgrader;
        isThrown = false;
    }

    public Position getPos()
    {
        return this.pos;
    }

    public boolean isThrown()
    {
        return this.isThrown;
    }
    public void setAsThrown()
    {
        pos = upgrader.getGame().getModel().getRandromPointPosition();
        isThrown = true;

        modelChanged();
    }

    public void setAsEaten() // apply random upgrade
    {
        isThrown = false;

        UpgradeBasic upgrade = createRandomUpgrade();
        if (upgrade != null)
        {
            Thread thread = new Thread(upgrade);
            thread.start();
        }
    }

    private UpgradeBasic createRandomUpgrade()
    {
        Game.Upgrade upgradeType = Game.Upgrade.getRandomUpgrade();

        Game game = upgrader.getGame();

        switch (upgradeType)
        {
            case SPEED_UP : return new Upgrade_SpeedUp(game);
            case FREEZE_GHOSTS : return new Upgrade_FreezeGhosts(game);
            case HIDE_GHOSTS : return new Upgrade_HideGhosts(game);
            case BOOST_SCORE : return new Upgrade_BoostScore(game);
//            default : return null;
        }

        return null;
    }

    public void modelChanged()
    {
        upgrader.getGame().getModel().modelChanged();
    }

    public void shutDown()
    {}
}
