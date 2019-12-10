import java.util.Random;

public class Animal extends AbstractMapElement
{
    private static int moveEnergy = 1;
    private static int minStartEnergy = 4;

    private Orientation orientation;
    private int energy;
    private final int minBreedEnergy;
    private Genotype genes;

    private WorldMap map;
    private Random generator;

    public static void setMoveEnergy(int energy)
    {
        moveEnergy = energy;
    }

    private Animal(Vector2d position, int energy, Genotype genes, WorldMap map, Random generator)
    {
        super(position);
        if (energy < minStartEnergy)
            throw new IllegalArgumentException("Energy cannot be less than minStartEnergy - " + minStartEnergy);
        this.orientation = Orientation.randomOrientation(generator);
        this.energy = energy;
        this.minBreedEnergy = energy / 2;
        this.genes = genes;
        this.map = map;
        this.generator = generator;
    }

    public Animal(Vector2d position, int energy, WorldMap map, Random generator)
    {
        this(position, energy, new Genotype(generator), map, generator);
    }

    public Animal(Vector2d position, int energy, Random generator)
    {
        this(position, energy, new Genotype(generator), null, generator);
    }

    public Animal(Vector2d position, int energy, WorldMap map)
    {
        this(position, energy, map, new Random());
    }

    public void eat(int energy)
    {
        this.energy += energy;
    }

    public void move()
    {
        randomRotate();
        moveForward();
    }

    public void loseEnergy()
    {
        this.energy -= moveEnergy;
    }

    public Animal breed(Animal that)
    {
        if (this.energy < this.minBreedEnergy || that.energy < that.minBreedEnergy)
            return null;

        int childEnergy = (this.energy + 3) / 4 + (that.energy + 3) / 4; // Zaokrąglenie w górę
        if (childEnergy < minStartEnergy)
            return null;

        this.energy -= this.energy / 4;
        that.energy -= that.energy / 4;

        Vector2d childPosition;
        if (map == null)
            childPosition = new Vector2d(this.getPosition().x, this.getPosition().y);
        else
            childPosition = map.findChildPosition(this.position);

        return new Animal(childPosition, childEnergy, genes.createChildGenes(that.genes), this.map, generator);
    }

    private void moveForward()
    {
        if (this.map == null)
            this.position = this.position.add(this.orientation.toVector());
        else
        {
            Vector2d oldPosition = position;
            this.position = map.findPosition(this.position.add(this.orientation.toVector()));
            map.updatePosition(this, oldPosition);
        }
    }

    private void randomRotate()
    {
        orientation = orientation.rotate(genes.getRandomRotation());
    }
}
