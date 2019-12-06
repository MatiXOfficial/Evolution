import java.util.Random;

public class Animal extends AbstractMapElement
{
    private static int minBreedEnergy = 4;
    private Orientation orientation;
    private int energy;
    private Genotype genes;

    public Animal(Vector2d position, int energy)
    {
        super(position);
        this.energy = energy;
        this.orientation = Orientation.N;
        this.genes = new Genotype(generator, true);
    }

    public Animal(Vector2d position, int energy, Random generator)
    {
        super(position, generator);
        this.energy = energy;
        this.orientation = Orientation.N;
        this.genes = new Genotype(generator, true);
    }

    private Animal(Vector2d position, int energy, Genotype genes)
    {
        super(position);
        this.energy = energy;
        this.orientation = Orientation.N;
        this.genes = genes;
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

    public Animal breed(Animal that)
    {
        if (this.energy < minBreedEnergy || that.energy < minBreedEnergy)
            return null;

        int childEnergy = this.energy / 4;
        this.energy -= this.energy / 4;
        childEnergy += that.energy / 4;
        that.energy -= that.energy / 4;

        return new Animal(this.position, childEnergy, genes.createChildGenes(that.genes));
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
