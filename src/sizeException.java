public class sizeException extends Exception
{
    sizeException(int x, int y)
    {
        super("Size " + x + " x " + y + " is not supported");
    }
}
