package game.upgrades;

import game.Game;
import game.ghost.Ghost;

import java.util.ArrayList;

public class Upgrade_FriendlyGhosts extends Upgrade_Basic
{
    public Upgrade_FriendlyGhosts(Game game)
    {
        super(game);
        title = "FRIENDS";
    }
    @Override
    void applyUpgrade()
    {
        ArrayList<Ghost> ghosts = game.getGhosts();

        for(Ghost ghost : ghosts )
            ghost.getModel().setAsFriendly(true);

        try{
            Thread.sleep(5000);
        } catch ( InterruptedException e )
        {
            return;
        }

        for(Ghost ghost : ghosts )
            ghost.getModel().setAsFriendly(false);

    }
}

