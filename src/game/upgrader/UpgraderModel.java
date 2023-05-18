package game.upgrader;

import game.Game;
import game.Position;
import game.upgrades.*;

public class UpgraderModel {
    private final Upgrader upgrader;

    boolean isDropped; // upgrade icon is shown in the game field
    private Position pos; // location where the upgrade icon is shown

    public UpgraderModel(Upgrader upgrader) {
        this.upgrader = upgrader;
        isDropped = false;
    }

    public Position getPos()
    {
        return this.pos;
    }

    public boolean isDropped()
    {
        return this.isDropped;
    }
    public void setAsDropped()
    {
        pos = upgrader.getGame().getModel().getPositionOfRandomGhost();
        isDropped = true;

        modelChanged();
    }

    public void setAsEaten() // apply random upgrade
    {
        isDropped = false;

        Upgrade_Basic upgrade = createRandomUpgrade();
        if (upgrade != null)
        {
            Thread thread = new Thread(upgrade);
            thread.start();
        }
    }

    private Upgrade_Basic createRandomUpgrade()
    {
        Game.Upgrade upgradeType = Game.Upgrade.getRandomUpgrade();

        Game game = upgrader.getGame();

        switch (upgradeType)
        {
            case SPEED_UP : return new Upgrade_SpeedUp(game);
            case FREEZE_GHOSTS : return new Upgrade_FreezeGhosts(game);
            case HIDE_GHOSTS : return new Upgrade_HideGhosts(game);
            case BOOST_SCORE : return new Upgrade_BoostScore(game);
            case FRIENDLY_GHOST : return new Upgrade_FriendlyGhosts(game);
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
