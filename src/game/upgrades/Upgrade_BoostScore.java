package game.upgrades;

import game.Game;
import game.ghost.Ghost;

import java.util.ArrayList;

public class Upgrade_BoostScore extends UpgradeBasic
{
    public Upgrade_BoostScore(Game game)
    {
        super(game);
        title = "SCORE BOOSTER";
    }
    @Override
    void applyUpgrade()
    {
        double scoreMultiplier = game.getModel().getScoreMultiplier();

        game.getModel().setScoreMultiplier(2);

        try{
            Thread.sleep(5000);
        } catch ( InterruptedException e )
        {
            return;
        }

        game.getModel().setScoreMultiplier(scoreMultiplier);
    }

}
