package game.upgrades;

import game.Game;
import game.ghost.Ghost;

import java.util.ArrayList;

public class Upgrade_HideGhosts extends Upgrade_Basic
{
    public Upgrade_HideGhosts(Game game)
    {
        super(game);
        title = "HIDE GHOSTS";
    }
    @Override
    void applyUpgrade()
    {
        ArrayList<Ghost> ghosts = game.getGhosts();

        for(Ghost ghost : ghosts )
            ghost.getModel().setAsHidden(true);

        try{
            Thread.sleep(5000);
        } catch ( InterruptedException e )
        {
            return;
        }

        for(Ghost ghost : ghosts )
            ghost.getModel().setAsHidden(false);

    }
}
