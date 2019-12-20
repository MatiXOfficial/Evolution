import java.awt.*;

public class World
{
    public static void main(String[] args)
    {
        WorldMap map = new WorldMap(20, 20, 10, 1, 10000, 0.4, 10);
        //map.daysSimulation(100);
        new VisualizationFrame(map, 50);
    }
}
