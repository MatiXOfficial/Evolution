import java.util.*;

public class WorldMap
{
    private int startEnergy;
    private int plantEnergy;
    private int moveEnergy;

    private Vector2d bottomLeft;
    private Vector2d topRight;
    private Vector2d jungleBottomLeft;
    private Vector2d jungleTopRight;

    private AnimalsHashMap animalsHashMap;
    private HashSet<Vector2d> plantsSet;

    private Random generator;

    public WorldMap(int startEnergy, int plantEnergy, int moveEnergy, Vector2d bottomLeft, Vector2d topRight, Vector2d jungleBottomLeft, Vector2d jungleTopRight, Random generator)
    {
        this.startEnergy = startEnergy;
        this.plantEnergy = plantEnergy;
        this.moveEnergy = moveEnergy;

        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
        this.jungleBottomLeft = jungleBottomLeft;
        this.jungleTopRight = jungleTopRight;

        this.animalsHashMap = new AnimalsHashMap(generator);
        this.plantsSet = new HashSet<>();

        this.generator = generator;
    }

    public WorldMap(int startEnergy, int plantEnergy, int moveEnergy, Vector2d bottomLeft, Vector2d topRight, Vector2d jungleBottomLeft, Vector2d jungleTopRight, Random generator, int firstAnimals)
    {
        this(startEnergy, plantEnergy, moveEnergy, bottomLeft, topRight, jungleBottomLeft, jungleTopRight, generator);
        for (int i = 0; i < firstAnimals; i++)
            animalsHashMap.addAnimal(new Animal(this.findAvailablePosition(), startEnergy, this, generator));
    }

    public void daySimulation()
    {
        clearPhase();
        movePhase();
        eatPhase();
        breedPhase();
        newPlantsPhase();
    }

    private void clearPhase()
    {
        LinkedList<Animal> animals = animalsHashMap.getAllAnimals();
        for (Animal animal : animals)
        {
            if (animal.isDead())
                animalsHashMap.removeAnimal(animal);
        }
    }

    private void movePhase()
    {
        LinkedList<Animal> animals = animalsHashMap.getAllAnimals();
        for (Animal animal : animals)
        {
            animal.move();
            animal.updatePositionWithBoundaries(bottomLeft, topRight);
        }
    }

    private void eatPhase()
    {
        for (Vector2d position : plantsSet)
        {
            LinkedList<Animal> animalsToEat = animalsHashMap.getAnimalsToEat(position);
            if (animalsToEat != null)
                this.eat(animalsToEat);
        }
    }

    private void breedPhase()
    {
        for (Vector2d position : animalsHashMap.getAllPositions())
        {
            LinkedList<Animal> animalsToBreed = animalsHashMap.getAnimalsToBreed(position);
            if (animalsToBreed != null)
                this.breed(animalsToBreed.get(0), animalsToBreed.get(1));
        }
    }

    private void newPlantsPhase()
    {

    }

    private Vector2d findAvailablePosition()
    {
        Vector2d position = Vector2d.randomWithBoundaries(bottomLeft, topRight, generator);
        while(!this.isFree(position))
            position = Vector2d.randomWithBoundaries(bottomLeft, topRight, generator);
        return position;
    }

    private Vector2d findAvailablePosition(int n)
    {
        Vector2d position;
        for (int i = 0; i < n; i++)
        {
            position = Vector2d.randomWithBoundaries(bottomLeft, topRight, generator);
            if (this.isFree(position))
                return position;
        }
        return null;
    }

    private boolean isFree(Vector2d position)
    {
        return animalsHashMap.isFree(position) && plantsSet.contains(position);
    }

    private void eat(LinkedList<Animal> animalsToEat)
    {
        int energy = plantEnergy / animalsToEat.size();
        for (Animal animal : animalsToEat)
        {
            animal.increaseEnergy(energy);
        }
    }

    private void breed(Animal father, Animal mother)
    {
        Vector2d parentsPosition = father.getPosition();
        Vector2d childPosition = findBreedPosition(parentsPosition);
        animalsHashMap.addAnimal(father.breed(mother, childPosition));
    }

    private Vector2d findBreedPosition(Vector2d parentsPosition)
    {
        LinkedList<Vector2d> dirs = new LinkedList<>();
        for (Orientation orientation : Orientation.values())
            dirs.add(orientation.toVector());

        while(!dirs.isEmpty())
        {
            int i = generator.nextInt(dirs.size());
            Vector2d childPosition = dirs.get(i);
            if (isFree(childPosition))
                return childPosition;
            dirs.remove(i);
        }

        for (Orientation orientation : Orientation.values())
            dirs.add(orientation.toVector());
        return dirs.get(generator.nextInt(dirs.size()));
    }
}
