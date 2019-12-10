import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class AnimalTest
{
    @Test
    public void moveTest()
    {
        Animal animal = new Animal(new Vector2d(2, 5), 13, new Random(3));
        animal.move();  animal.move();  animal.move();
        Assert.assertEquals(new Vector2d(-1, 5), animal.getPosition());

        animal = new Animal(new Vector2d(-3, 12), 10, new Random(16));
        animal.move();  animal.move();  animal.move();  animal.move();
        Assert.assertEquals(new Vector2d(-2, 11), animal.getPosition());
    }
}
