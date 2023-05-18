package game.upgrades;

import game.Game;
import game.ghost.Ghost;

import java.util.ArrayList;

public class Upgrade_FreezeGhosts extends Upgrade_Basic
{
    public Upgrade_FreezeGhosts(Game game)
    {
        super(game);
        title = "FREEZER";
    }
    @Override
    void applyUpgrade()
    {
        ArrayList<Ghost> ghosts = game.getGhosts();

        for(Ghost ghost : ghosts )
            ghost.getModel().setAsFrozen(true);

        try{
            Thread.sleep(5000);
        } catch ( InterruptedException e )
        {
            return;
        }

        for(Ghost ghost : ghosts )
            ghost.getModel().setAsFrozen(false);

    }
}

