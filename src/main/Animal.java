import java.util.Random;

public class Animal
{
    private static int moveEnergy = 1;
    private static int minStartEnergy = 1;

    private Vector2d position;
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
        this.position = position;
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

    public Vector2d getPosition()
    {
        return this.position;
    }

    public int getEnergy()
    {
        return this.energy;
    }

    public boolean isDead()
    {
        return this.energy <= 0;
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

    public void changePosition(Vector2d newPosition)
    {
        this.position = newPosition;
    }

    public void loseEnergy()
    {
        this.energy -= moveEnergy;
    }

    public Animal breed(Animal that, Vector2d childPosition)
    {
        if (this.position != that.position)
            throw new IllegalArgumentException("Animals do not have the same position!");

        if (this.energy < this.minBreedEnergy || that.energy < that.minBreedEnergy)
            return null;

        int childEnergy = this.energy / 4 + that.energy / 4;
        if (childEnergy < minStartEnergy)
            return null;

        this.energy -= this.energy / 4;
        that.energy -= that.energy / 4;

        return new Animal(childPosition, childEnergy, genes.createChildGenes(that.genes), this.map, generator);
    }

    private void moveForward()
    {
        this.position = this.position.add(this.orientation.toVector());
    }

    private void randomRotate()
    {
        orientation = orientation.rotate(genes.getRandomRotation());
    }
}
