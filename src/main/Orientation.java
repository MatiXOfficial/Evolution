import java.util.LinkedList;
import java.util.Random;

public enum Orientation
{
    N, NE, E, SE, S, SW, W, NW;

    private static Orientation[] values = values();

    public Orientation rotate(int i)
    {
        return values[(this.ordinal() + i) % values.length];
    }

    public Vector2d toVector()
    {
        int [] xTab = new int[] {0, 1, 1, 1, 0, -1, -1, -1};
        int [] yTab = new int[] {1, 1, 0, -1, -1, -1, 0, 1};
        return new Vector2d(xTab[this.ordinal()], yTab[this.ordinal()]);
    }

    public static Orientation randomOrientation(Random generator)
    {
        return values[generator.nextInt(8)];
    }
}
