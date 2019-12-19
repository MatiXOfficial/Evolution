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

    private HashSet<Vector2d> steppeFreePositions;
    private HashSet<Vector2d> jungleFreePositions;

    private Random generator;

    private WorldMap(int startEnergy, int plantEnergy, int moveEnergy, Vector2d bottomLeft, Vector2d topRight, Vector2d jungleBottomLeft, Vector2d jungleTopRight, Random generator)
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

        steppeFreePositions = new HashSet<>();
        jungleFreePositions = new HashSet<>();
        for (int x = bottomLeft.x; x <= topRight.x; x++)
        {
            for (int y = bottomLeft.y; y <= topRight.y; y++)
            {
                Vector2d position = new Vector2d(x, y);
                if (isInJungle(position))
                    jungleFreePositions.add(position);
                else
                    steppeFreePositions.add(position);
            }
        }

        this.generator = generator;
    }

    public WorldMap(int startEnergy, int plantEnergy, int moveEnergy, Vector2d bottomLeft, Vector2d topRight, Vector2d jungleBottomLeft, Vector2d jungleTopRight, Random generator, int firstAnimals)
    {
        this(startEnergy, plantEnergy, moveEnergy, bottomLeft, topRight, jungleBottomLeft, jungleTopRight, generator);
        for (int i = 0; i < firstAnimals; i++)
            this.addAnimal(new Animal(this.findAvailablePosition(), startEnergy, this, generator));
    }

    private void daySimulation()
    {
        clearPhase();
        movePhase();
        eatPhase();
        breedPhase();
        newPlantsPhase();
    }

    public void daysSimulation(int n)
    {
        for (int i = 0; i < n; i++)
            daySimulation();
    }

    private void clearPhase()
    {
        LinkedList<Animal> animals = animalsHashMap.getAllAnimals();
        for (Animal animal : animals)
        {
            if (animal.isDead())
                this.removeAnimal(animal);
        }
    }

    private void movePhase()
    {
        LinkedList<Animal> animals = animalsHashMap.getAllAnimals();
        for (Animal animal : animals)
        {
            this.removeAnimal(animal);
            animal.move();
            animal.updatePositionWithBoundaries(bottomLeft, topRight);
            this.addAnimal(animal);
        }
    }

    private void eatPhase()
    {
        LinkedList<Vector2d> positions = new LinkedList<>();
        for (Vector2d position : plantsSet)
            positions.add(position);

        for (Vector2d position : positions)
        {
            LinkedList<Animal> animalsToEat = animalsHashMap.getAnimalsToEat(position);
            if (animalsToEat != null)
                this.eat(animalsToEat);
        }
    }

    private void breedPhase()
    {
        LinkedList<LinkedList<Animal>> animalsToBreed = new LinkedList<>();
        for (Vector2d position : animalsHashMap.getAllPositions())
        {
            LinkedList<Animal> animals = animalsHashMap.getAnimalsToBreed(position);
            if (animals != null)
                animalsToBreed.add(animals);
        }
        for (LinkedList<Animal> animals : animalsToBreed)
            breed(animals.get(0), animals.get(1));
    }

    private void newPlantsPhase()
    {
        if (!jungleFreePositions.isEmpty())
        {
            int i = generator.nextInt(jungleFreePositions.size());
            Vector2d position = (Vector2d) jungleFreePositions.toArray()[i];
            plantsSet.add(position);
            jungleFreePositions.remove(position);
        }
        if (!steppeFreePositions.isEmpty())
        {
            int i = generator.nextInt(steppeFreePositions.size());
            Vector2d position = (Vector2d) steppeFreePositions.toArray()[i];
            plantsSet.add(position);
            steppeFreePositions.remove(position);
        }
    }

    private void addAnimal(Animal animal)
    {
        if (animal == null)
            throw new IllegalArgumentException("Cannot add null");
        animalsHashMap.addAnimal(animal);
        steppeFreePositions.remove(animal.getPosition());
        jungleFreePositions.remove(animal.getPosition());
    }

    private void removeAnimal(Animal animal)
    {
        Vector2d position = animal.getPosition();
        animalsHashMap.removeAnimal(animal);
        if (animalsHashMap.isFree(position) && !plantsSet.contains(position))
        {
            if (isInJungle(position))
                jungleFreePositions.add(position);
            else
                steppeFreePositions.add(position);
        }
    }

    private Vector2d findAvailablePosition()
    {
        Vector2d position = Vector2d.randomWithBoundaries(bottomLeft, topRight, generator);
        while(!this.isFree(position))
            position = Vector2d.randomWithBoundaries(bottomLeft, topRight, generator);
        return position;
    }

    private boolean isFree(Vector2d position)
    {
        return animalsHashMap.isFree(position) && !plantsSet.contains(position);
    }

    private void eat(LinkedList<Animal> animalsToEat)
    {
        int energy = plantEnergy / animalsToEat.size();
        for (Animal animal : animalsToEat)
        {
            animal.increaseEnergy(energy);
        }
        this.plantsSet.remove(animalsToEat.get(0).getPosition());
    }

    private void breed(Animal father, Animal mother)
    {
        Vector2d parentsPosition = father.getPosition();
        Vector2d childPosition = findBreedPosition(parentsPosition);
        Animal child = father.breed(mother, childPosition);
        if (child != null)
            this.addAnimal(child);
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

    private boolean isInJungle(Vector2d position)
    {
        return position.follows(jungleBottomLeft) && position.precedes(jungleTopRight);
    }
}
