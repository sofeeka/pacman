import java.util.Random;

public enum Element {
    EMPTY,
    WALL,
    POINT,
    FOOD;

    private static final Random RAND = new Random();

    public static Element getRandomElement()  {
        Element[] elements = values();
        return elements[RAND.nextInt(elements.length)];
    }

    public String getMessage()
    {
        return name();
    }

}
