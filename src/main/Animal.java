public class Animal extends AbstractMapElement
{
    private Orientation orientation;
    private int energy;
    private int[] genes;

    public Animal(Vector2d position, int energy, int[] genes)
    {
        super(position);
        this.energy = energy;
        this.genes = genes;
    }

    public void rotate(int i)
    {
        orientation.rotate(i);
    }

    public void moveForward()
    {
        this.position = this.position.add(this.orientation.toVector());
    }
}
