import java.util.Random;

enum Direction
{
    STILL,
    UP,
    DOWN,
    LEFT,
    RIGHT;

    private static final Random RAND = new Random();

    public static Direction getRandomDirection()  {
        Direction[] directions = values();
        return directions[RAND.nextInt(directions.length)];
    }
}
