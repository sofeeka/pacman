package game.pacman;

import game.Game;

public class Pacman {
    private final Game game;
    private final GameModel_Pacman model;
    private final GameView_Pacman view;
    private final GameController_Pacman controller;

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
