import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class World
{
    public static void main(String[] args)
    {
        WorldMap map = new WorldMap(50, 2, 1, new Vector2d(0, 0), new Vector2d(10, 10), new Vector2d(4, 4), new Vector2d(6, 6), new Random(15), 4);
        map.daysSimulation(40000);
    }
}
