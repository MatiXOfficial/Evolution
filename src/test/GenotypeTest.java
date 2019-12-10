import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class GenotypeTest
{
    @Test
    public void constructorTest()
    {
        Assert.assertArrayEquals(new int[] {6, 6, 4, 2, 6, 4, 1, 8, 2, 3, 1, 5, 4, 4, 6, 8, 3, 2, 8, 3, 2, 5, 6, 8, 2, 3, 4, 1, 5, 6, 5, 6}, new Genotype(new Random(11)).getGenes());
    }

    @Test
    public void getRandomRotationTest()
    {
        Assert.assertEquals(6, new Genotype(new Random(11)).getRandomRotation());
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
        generator = new Random(323); // Przypadek z brakującym genem
        Assert.assertArrayEquals(new int[] {8, 8, 1, 4, 6, 4, 6, 7, 1, 6, 1, 4, 7, 7, 2, 1, 8, 4, 3, 8, 6, 3, 8, 6, 6, 5, 7, 4, 6, 6, 4, 5},
                (new Genotype(generator).createChildGenes(new Genotype(generator))).getGenes());
    }
}
