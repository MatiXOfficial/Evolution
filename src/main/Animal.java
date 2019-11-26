import java.util.Random;

public class Animal extends AbstractMapElement
{
    private static int genesNumber = 32;
    private static int minBreedEnergy = 4;
    private Orientation orientation;
    private int energy;
    private int[] genes;

    public Animal(Vector2d position, int energy, int[] genes)
    {
        super(position);
        if (genes.length != genesNumber)
        {
            throw new IllegalArgumentException("Zla liczba genow");
        }
        this.energy = energy;
        this.genes = genes;
        this.orientation = Orientation.N;
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

        int i = new Random().nextInt(genesNumber + 1);
        int j = new Random().nextInt(genesNumber + 1 - i) + i;
        int [] childGenes = createChildGenes(this.genes, that.genes, i, j);

        return new Animal(this.position, childEnergy, childGenes);
    }

    private void moveForward()
    {
        this.position = this.position.add(this.orientation.toVector());
    }

    private void randomRotate()
    {
        orientation = orientation.rotate(getRandomRotation());
    }

    private int getRandomRotation()
    {
        return this.genes[new Random().nextInt(genes.length)];
    }

    private int[] createChildGenes(int[] genes1, int[] genes2, int i, int j)
    {
        int[] childGenes = new int[32];
        int num1 = 0, num2 = 0, which;
        int [] pos = new int[] {0, i, j, genesNumber};

        for (int k = 0; k < 3; k++)
        {
            if (num1 == 2)
                which = 1;
            else if (num2 == 2)
                which = 0;
            else
                which = new Random().nextInt(2);
            if (which == 0)
            {
                num1++;
                System.arraycopy(childGenes, pos[k], genes1, pos[k], pos[k + 1]);
            }
            else
            {
                num2++;
                System.arraycopy(childGenes, pos[k], genes2, pos[k], pos[k + 1]);
            }
        }

        int[] genesNumbers = new int[8];
        for (int k = 0; k < genesNumber; k++)
            genesNumbers[childGenes[k]]++;
        for (int k = 0; k < 8; k++)
            if (genesNumbers[k] == 0)
                while(true)
                {
                    int l = new Random().nextInt(genesNumber);
                    if (genesNumbers[childGenes[l]] > 1)
                    {
                        genesNumbers[childGenes[k]] ++;
                        genesNumbers[childGenes[l]] --;
                        childGenes[l] = k;
                        break;
                    }
                }
        return childGenes;
    }
}
