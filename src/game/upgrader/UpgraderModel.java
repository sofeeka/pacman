package game.upgrader;

import game.Position;

public class UpgraderModel {
    private final Upgrader upgrader;

    boolean isThrown;
    private Position pos;

    public UpgraderModel(Upgrader upgrader) {
        this.upgrader = upgrader;
        isThrown = false;
    }

    Position getPos()
    {
        return this.pos;
    }

    boolean isThrown()
    {
        return this.isThrown;
    }
    public void setAsThrown()
    {
        pos = upgrader.getGame().getModel().getRandromPointPosition();
        isThrown = true;

        modelChanged();
    }

    public void modelChanged()
    {
        upgrader.getGame().getModel().modelChanged();
    }

    public void shutDown()
    {}
}
