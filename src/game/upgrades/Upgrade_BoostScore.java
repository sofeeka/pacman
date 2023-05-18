package game.upgrades;

import game.Game;

public class Upgrade_BoostScore extends Upgrade_Basic
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
