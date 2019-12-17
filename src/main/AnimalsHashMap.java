import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class AnimalsHashMap
{
    private HashMap<Vector2d, LinkedList<Animal>> animals;

    public AnimalsHashMap()
    {
        this.animals = new HashMap<>();
    }

    public LinkedList<Animal> getAllAnimals()
    {
        Collection<LinkedList<Animal>> animalsLists = this.animals.values();
        LinkedList<Animal> result = new LinkedList<>();
        for (LinkedList<Animal> animalsList : animalsLists)
        {
            result.addAll(animalsList);
        }
        return result;
    }

    public void addAnimal(Animal animal)
    {
        if (this.animals.get(animal.getPosition()) == null)
            this.animals.put(animal.getPosition(), new LinkedList<Animal>());
        this.animals.get(animal.getPosition()).add(animal);
    }

    public void removeAnimal(Animal animal)
    {
        if (animal == null)
            throw new IllegalArgumentException("Argument cannot be null!");
        if (this.animals.get(animal.getPosition()) != null)
            this.animals.get(animal.getPosition()).remove(animal);
    }

    public LinkedList<Animal> getAnimalsToEat(Vector2d position)
    {
        LinkedList<Animal> animalsList = this.animals.get(position);
        if (animalsList == null)
            return null;

        int maxEnergy = 0;
        for (int i = 0; i < animalsList.size(); i++) {
            if (animalsList.get(i).getEnergy() > maxEnergy)
                maxEnergy = animalsList.get(i).getEnergy();
        }

        LinkedList<Animal> result = new LinkedList<>();
        for (Animal animal : animalsList)
        {
            if (animal.getEnergy() == maxEnergy)
                result.add(animal);
        }
        return result;
    }

    public LinkedList<Animal> getAnimalsToBreed(Vector2d position)
    {
        LinkedList<Animal> animalsList = this.animals.get(position);
        if (animalsList == null || animalsList.size() == 1)
            return null;

        int maxIdx1 = 0, maxIdx2 = 0;
        for (int i = 0; i < animalsList.size(); i++)
        {
            if (animalsList.get(i).getEnergy() > animalsList.get(maxIdx1).getEnergy())
            {
                maxIdx2 = maxIdx1;
                if (animalsList.get(i).getEnergy() > animalsList.get(maxIdx2).getEnergy())
                maxIdx1 = i;
            }
            else if (animalsList.get(i).getEnergy() > animalsList.get(maxIdx2).getEnergy())
            {
                maxIdx2 = i;
            }
        }

        LinkedList<Animal> result = new LinkedList<>();
        result.add(animalsList.get(maxIdx1));
        result.add(animalsList.get(maxIdx2));
        return result;
    }

    public void updatePosition(Vector2d oldPosition, Animal animal)
    {
        this.animals.get(oldPosition).remove(animal);
        this.addAnimal(animal);
    }
}
