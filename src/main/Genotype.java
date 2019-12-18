import java.util.Random;


public class Genotype
{
    private static int genesNumber = 32;
    private int[] genes;
    private Random generator;

    private Genotype(Random generator, boolean generate)
    {
        this.generator = generator;
        this.genes = new int[32];
        if (generate)
        {
            for (int i = 0; i < genesNumber; i++)
                this.genes[i] = generator.nextInt(8) + 1;
            this.mutate();
        }
    }

    public Genotype(Random generator)
    {
        this(generator, true);
    }

    public int[] getGenes()
    {
        return this.genes;
    }

    public int getRandomRotation()
    {
        return this.genes[generator.nextInt(genesNumber)];
    }

    public Genotype createChildGenes(Genotype that)
    {
        int i = generator.nextInt(genesNumber + 1);
        int j = generator.nextInt(genesNumber + 1 - i) + i;
        Genotype childGenes = new Genotype(generator, false);

        int num1 = 0, num2 = 0, which;
        int [] pos = new int[] {0, i, j, genesNumber};

        for (int k = 0; k < 3; k++)
        {
            if (num1 == 2)
                which = 1;
            else if (num2 == 2)
                which = 0;
            else
                which = generator.nextInt(2);
            if (which == 0)
            {
                num1++;
                System.arraycopy(this.genes, pos[k], childGenes.genes, pos[k], pos[k + 1] - pos[k]);
            }
            else
            {
                num2++;
                System.arraycopy(that.genes, pos[k], childGenes.genes, pos[k], pos[k + 1] - pos[k]);
            }
        }
        mutate(childGenes);
        return childGenes;
    }

    private void mutate(Genotype genotype)
    {
        int[] genesCount = new int[8];
        for (int k = 0; k < genesNumber; k++)
            genesCount[genotype.genes[k] - 1]++;

        for (int k = 0; k < 8; k++)
            if (genesCount[k] == 0)
                while(true)
                {
                    int l = generator.nextInt(genesNumber);
                    if (genesCount[genotype.genes[l] - 1] > 1)
                    {
                        genesCount[genotype.genes[k] - 1] ++;
                        genesCount[genotype.genes[l] - 1] --;
                        genotype.genes[l] = k + 1;
                        break;
                    }
                }
    }

    private void mutate()
    {
        mutate(this);
    }
}
