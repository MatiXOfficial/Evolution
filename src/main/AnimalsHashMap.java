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
        return !animals.containsKey(position);
    }

    public LinkedList<Animal> getAllAnimals()
    {
        LinkedList<Animal> result = new LinkedList<>();
        for (LinkedList<Animal> animalsList : this.animals.values())
        {
            result.addAll(animalsList);
        }
        return result;
    }

    public Set<Vector2d> getAllPositions()
    {
        return this.animals.keySet();
    }

    public void addAnimal(Animal animal)
    {
        if (!this.animals.containsKey(animal.getPosition()))
            this.animals.put(animal.getPosition(), new LinkedList<>());
        this.animals.get(animal.getPosition()).add(animal);
    }

    public void removeAnimal(Animal animal)
    {
        if (this.animals.containsKey(animal.getPosition()))
        {
            this.animals.get(animal.getPosition()).remove(animal);
            if (this.animals.get(animal.getPosition()).size() == 0)
                this.animals.remove(animal.getPosition());
        }
    }

    public LinkedList<Animal> getAnimalsToEat(Vector2d position)
    {
        if (!this.animals.containsKey(position))
            return null;

        LinkedList<Animal> animalsList = this.animals.get(position);
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
        if (!this.animals.containsKey(position))
            return null;

        LinkedList<Animal> animalsList = this.animals.get(position);
        if (animalsList.size() == 1)
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
        }

        LinkedList<Animal> result = new LinkedList<>();
        result.add(animalsList.get(maxIdx1));
        result.add(animalsList.get(maxIdx2));
        return result;
    }

    public void updatePosition(Vector2d oldPosition, Animal animal)
    {
        if (!this.animals.containsKey(oldPosition))
            return;
        this.animals.get(oldPosition).remove(animal);
        if (this.animals.get(oldPosition).size() == 0)
            this.animals.remove(oldPosition);
        this.addAnimal(animal);
    }

    public LinkedList<Animal> getAnimalsToVisualization()
    {
        LinkedList<Animal> result = new LinkedList<>();
        for (LinkedList<Animal> animalsList : this.animals.values())
        {
            int maxIdx = 0;
            for (int i = 1; i < animalsList.size(); i++)
                if (animalsList.get(i).getEnergy() > animalsList.get(maxIdx).getEnergy())
                    maxIdx = i;
            result.add(animalsList.get(maxIdx));
        }
        return result;
    }
}
