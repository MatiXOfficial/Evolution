import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class AnimalsHashMapTest
{
    @Test
    public void animalsHashMapTest()
    {
        AnimalsHashMap animalsHashMap = new AnimalsHashMap(new Random(15));
        Random generator = new Random(13);

        LinkedList<Animal> animals = new LinkedList<> (Arrays.asList(new Animal(new Vector2d(3, 1), 14, generator), new Animal(new Vector2d(3, 1), 1, generator),
                new Animal(new Vector2d(3, 1), 18, generator), new Animal(new Vector2d(4, 0), 93, generator), new Animal(new Vector2d(3, 1), 7, generator),
                new Animal(new Vector2d(3, 1), 5, generator), new Animal(new Vector2d(3, 1), 17, generator)));

        for (Animal animal : animals)
            animalsHashMap.addAnimal(animal);

        Assert.assertEquals(new LinkedList<Animal>(Arrays.asList(animals.get(2))), animalsHashMap.getAnimalsToEat(new Vector2d(3, 1)));
        Assert.assertNull(animalsHashMap.getAnimalsToEat(new Vector2d(8, 10)));

        //Assert.assertEquals(new LinkedList<Animal>(Arrays.asList(animals.get(2), animals.get(6))), animalsHashMap.getAnimalsToBreed(new Vector2d(3, 1)));
        Assert.assertNull(animalsHashMap.getAnimalsToBreed(new Vector2d(8, 15)));

        animals.add(new Animal(new Vector2d(3, 1), 18, generator));
        animals.add(new Animal(new Vector2d(3, 1), 18, generator));

        for (Animal animal : animals)
            animalsHashMap.removeAnimal(animal);

        for (Animal animal : animals)
            animalsHashMap.addAnimal(animal);

        Assert.assertEquals(new LinkedList<Animal>(Arrays.asList(animals.get(2), animals.get(7), animals.get(8))), animalsHashMap.getAnimalsToEat(new Vector2d(3, 1)));

        Assert.assertEquals(new LinkedList<Animal>(Arrays.asList(animals.get(2), animals.get(8))), animalsHashMap.getAnimalsToBreed(new Vector2d(3, 1)));

        animals.get(3).changePosition(new Vector2d(3, 1));
        animalsHashMap.updatePosition(new Vector2d(4, 0), animals.get(3));

        Assert.assertEquals(new LinkedList<Animal>(Arrays.asList(animals.get(3))), animalsHashMap.getAnimalsToEat(new Vector2d(3, 1)));
        Assert.assertNull(animalsHashMap.getAnimalsToEat(new Vector2d(4, 0)));

        Assert.assertEquals(new LinkedList<Animal>(Arrays.asList(animals.get(3), animals.get(7))), animalsHashMap.getAnimalsToBreed(new Vector2d(3, 1)));
        Assert.assertNull(animalsHashMap.getAnimalsToBreed(new Vector2d(4, 0)));
    }
}
