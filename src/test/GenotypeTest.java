import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class GenotypeTest
{
    @Test
    public void constructorTest()
    {
        Assert.assertArrayEquals(new int[] {6, 6, 4, 2, 6, 4, 1, 8, 2, 3, 1, 5, 4, 4, 6, 8, 3, 2, 8, 3, 2, 5, 6, 8, 2, 3, 4, 1, 5, 6, 5, 7}, new Genotype(new Random(11)).getGenes());
    }

    @Test
    public void getRandomRotationTest()
    {
        Assert.assertEquals(6, new Genotype(new Random(68)).getRandomRotation());
        Assert.assertEquals(2, new Genotype(new Random(23)).getRandomRotation());
    }

    @Test
    public void createChildGenesTest()
    {
        Random generator = new Random(33);
        Assert.assertArrayEquals(new int[] {4, 3, 2, 8, 3, 1, 7, 7, 2, 7, 8, 4, 3, 6, 7, 5, 4, 7, 7, 8, 1, 3, 2, 4, 1, 5, 8, 4, 3, 5, 5, 2},
                                (new Genotype(generator).createChildGenes(new Genotype(generator))).getGenes());
        generator = new Random(162);
        Assert.assertArrayEquals(new int[] {6, 4, 5, 6, 5, 3, 2, 5, 8, 1, 3, 4, 4, 5, 3, 2, 1, 2, 2, 7, 3, 4, 6, 7, 6, 6, 6, 4, 7, 1, 8, 5},
                (new Genotype(generator).createChildGenes(new Genotype(generator))).getGenes());
        generator = new Random(861);    // Przypadek z początkowym brakiem genu '6' u dziecka
        Assert.assertArrayEquals(new int[] {2, 4, 2, 1, 6, 1, 8, 1, 2, 2, 3, 3, 7, 2, 2, 3, 5, 4, 8, 8, 3, 7, 4, 2, 8, 2, 2, 5, 8, 1, 4, 1},
                (new Genotype(generator).createChildGenes(new Genotype(generator))).getGenes());
    }
}
