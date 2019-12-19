import java.util.*;

public class AnimalsHashMap
{
    private HashMap<Vector2d, LinkedList<Animal>> animals;
    private Random generator;

    public AnimalsHashMap(Random generator)
    {
        this.animals = new HashMap<>();
        this.generator = generator;
    }

    public boolean isFree(Vector2d position)
    {
        return animals.get(position) == null;
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

    public Set<Vector2d> getAllPositions()
    {
        return this.animals.keySet();
    }

    public LinkedList<Animal> getAnimalsAtPosition(Vector2d position)
    {
        return this.animals.get(position);
    }

    public void addAnimal(Animal animal)
    {
        if (animal == null)
            throw new IllegalArgumentException("Argument cannot be null!");

        if (this.animals.get(animal.getPosition()) == null)
            this.animals.put(animal.getPosition(), new LinkedList<>());
        this.animals.get(animal.getPosition()).add(animal);
    }

    public void removeAnimal(Animal animal)
    {
        if (animal == null)
            throw new IllegalArgumentException("Argument cannot be null!");

        if (this.animals.get(animal.getPosition()) != null)
        {
            this.animals.get(animal.getPosition()).remove(animal);
            if (this.animals.get(animal.getPosition()).size() == 0)
                this.animals.put(animal.getPosition(), null);
        }
    }

    public LinkedList<Animal> getAnimalsToEat(Vector2d position)
    {
        LinkedList<Animal> animalsList = this.animals.get(position);
        if (animalsList == null)
            return null;

        int maxEnergy = 0;
        for (Animal animal : animalsList)
            if (animal.getEnergy() > maxEnergy)
                maxEnergy = animal.getEnergy();

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

        int maxIdx1 = 0, maxIdx2 = 1;
        if (animalsList.get(maxIdx1).getEnergy() < animalsList.get(maxIdx2).getEnergy())
        {
            int tmp = maxIdx1;
            maxIdx1 = maxIdx2;
            maxIdx2 = tmp;
        }

        for (int i = 2; i < animalsList.size(); i++)
        {
            if (animalsList.get(i).getEnergy() > animalsList.get(maxIdx1).getEnergy())
            {
                if (animalsList.get(maxIdx1).getEnergy() > animalsList.get(maxIdx2).getEnergy() || generator.nextInt(2) == 0)
                    maxIdx2 = maxIdx1;
                maxIdx1 = i;
            }
            else if (animalsList.get(i).getEnergy() == animalsList.get(maxIdx1).getEnergy())
            {
                if (generator.nextInt(2) == 0)
                {
                    if (animalsList.get(maxIdx1).getEnergy() > animalsList.get(maxIdx2).getEnergy() || generator.nextInt(2) == 0)
                        maxIdx2 = maxIdx1;
                    maxIdx1 = i;
                }
                else if (animalsList.get(maxIdx1).getEnergy() > animalsList.get(maxIdx2).getEnergy() || generator.nextInt(2) == 0)
                    maxIdx2 = i;
            }
            else if (animalsList.get(i).getEnergy() > animalsList.get(maxIdx2).getEnergy())
                maxIdx2 = i;
            else if (animalsList.get(i).getEnergy() == animalsList.get(maxIdx2).getEnergy() && generator.nextInt(2) == 0)
                maxIdx2 = i;
            System.out.print(i + " ");
            System.out.print(maxIdx1 + " ");
            System.out.println(maxIdx2);
        }

        LinkedList<Animal> result = new LinkedList<>();
        result.add(animalsList.get(maxIdx1));
        result.add(animalsList.get(maxIdx2));
        return result;
    }

    public void updatePosition(Vector2d oldPosition, Animal animal)
    {
        if (animal == null)
            throw new IllegalArgumentException("Argument cannot be null!");

        if (this.animals.get(oldPosition) == null)
            return;
        this.animals.get(oldPosition).remove(animal);
        if (this.animals.get(oldPosition).size() == 0)
            this.animals.put(oldPosition, null);
        this.addAnimal(animal);
    }
}
