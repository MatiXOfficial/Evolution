import java.util.Random;

abstract public class AbstractMapElement implements IMapElement
{
    protected Vector2d position;
    protected Random generator;

    public AbstractMapElement(Vector2d position)
    {
        this.position = position;
        this.generator = new Random();
    }

    public AbstractMapElement(Vector2d position, Random generator)
    {
        this.position = position;
        this.generator = generator;
    }

    public Vector2d getPosition()
    {
        return this.position;
    }
}
