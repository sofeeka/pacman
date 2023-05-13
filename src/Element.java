import java.util.Random;

public enum Element {
    PACMAN,
    RED_GHOST,
    BLUE_GHOST,
    YELLOW_GHOST,
    PINK_GHOST,
    WALL,
    POINT,
    FOOD;

    private static final Random RAND = new Random();

    public static Element randomFieldElement()  {
        Element[] elements = values();
        return elements[RAND.nextInt(elements.length)];
    }

}
