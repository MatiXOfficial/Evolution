import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WorldMap
{
    private Vector2d bottomLeft;
    private Vector2d topRight;
    private Vector2d jungleBottomLeft;
    private Vector2d jungleTopRight;

    private Map<Vector2d, IMapElement> mapElements;
    private Random generator;

    public WorldMap(Vector2d bottomLeft, Vector2d topRight, Vector2d jungleBottomLeft, Vector2d jungleTopRight, Random generator)
    {
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
        this.jungleBottomLeft = jungleBottomLeft;
        this.jungleTopRight = jungleTopRight;

        mapElements = new HashMap<>();
        this.generator = generator;
    }

    public Vector2d findPosition(Vector2d position)
    {
        return position.updateWithBoundaries(bottomLeft, topRight);
    }

    public void updatePosition(Animal animal, Vector2d oldPosition)
    {

    }

    public Vector2d findChildPosition(Vector2d position)
    {
        return position;
    }
}
