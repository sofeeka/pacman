public class GameModel {
    private int width; // x
    private int height; // y
    private Element[][] gameBoard;

    GameModel(int x, int y)
    {
        width = x;
        height = y;
        initGameBoard();
    }

    private void initGameBoard()
    {

    }
    public void setDimensions(int x, int y)
    {
        this.width = x;
        this.height = y;
    }

}
