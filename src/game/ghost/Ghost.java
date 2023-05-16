package game.ghost;

import game.Game;

public class Ghost{
    Game game;
    GameModel_Ghost model;
    GameView_Ghost view;
    GameController_Ghost controller;
    public Ghost(Game game)
    {
        this.game = game;

        model = new GameModel_Ghost(this);
        controller = new GameController_Ghost(this);
        view = new GameView_Ghost(this);
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

    public GameModel_Ghost getModel() {
        return model;
    }

    public GameView_Ghost getView() {
        return view;
    }

    public GameController_Ghost getController() {
        return controller;
    }
}
