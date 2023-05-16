package game.pacman;

import game.Game;

public class Pacman {
    Game game;
    GameModel_Pacman model;
    GameView_Pacman view;
    GameController_Pacman controller;
    public Pacman(Game game)
    {
        this.game = game;

        model = new GameModel_Pacman(this);
        controller = new GameController_Pacman(this);
        view = new GameView_Pacman(this);
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
    public GameModel_Pacman getModel() {
        return model;
    }

    public GameView_Pacman getView() {
        return view;
    }

    public GameController_Pacman getController() {
        return controller;
    }
}
