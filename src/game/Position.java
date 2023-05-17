package game;

public class Position {
    private int x;
    private int y;

    public Position(){}
    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setXY( int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean isSame( Position p )
    {
        return( this.x == p.getX() && this.y == p.getY() );
    }

    public boolean isSame( int x, int y )
    {
        return (this.x == x && this.y == y);
    }
}
