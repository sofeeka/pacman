package game.upgrader;

import game.Game;

public class Upgrader {
    private final Game game;
    private final UpgraderModel model;
    private final UpgraderView view;
    private final UpgraderController controller;

    public Upgrader(Game game)
    {
        this.game = game;

        model = new UpgraderModel(this);
        controller = new UpgraderController(this);
        view = new UpgraderView(this);
    }

    public void shutDown()
    {
        controller.shutDown();
        view.shutDown();
        model.shutDown();
    }

    public Game getGame() {
        return game;
    }
    public UpgraderModel getModel() {
        return model;
    }
    public UpgraderView getView() {
        return view;
    }
    public UpgraderController getController() {
        return controller;
    }
}
