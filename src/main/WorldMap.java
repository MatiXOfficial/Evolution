import java.util.*;

public class WorldMap
{
    private final int startEnergy;
    private final int plantEnergy;

    private final Vector2d bottomLeft;
    private final Vector2d topRight;
    private final Vector2d jungleBottomLeft;
    private final Vector2d jungleTopRight;

    private AnimalsHashMap animalsHashMap;
    private HashSet<Vector2d> plantsSet;

    private HashSet<Vector2d> steppeFreePositions;
    private HashSet<Vector2d> jungleFreePositions;

    private final Random generator;

    private WorldMap(int startEnergy, int plantEnergy, int moveEnergy, Vector2d bottomLeft, Vector2d topRight, Vector2d jungleBottomLeft, Vector2d jungleTopRight, Random generator)
    {
        if (startEnergy < 1 || plantEnergy < 1 || moveEnergy < 0)
            throw new IllegalArgumentException("To small energy");
        this.startEnergy = startEnergy;
        this.plantEnergy = plantEnergy;
        Animal.setMoveEnergy(moveEnergy);

        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
        this.jungleBottomLeft = jungleBottomLeft;
        this.jungleTopRight = jungleTopRight;
        if (!bottomLeft.precedes(topRight) || !jungleBottomLeft.precedes(jungleTopRight))
            throw new IllegalArgumentException("Wrong corners");

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

    private WorldMap(int startEnergy, int plantEnergy, int moveEnergy, Vector2d bottomLeft, Vector2d topRight, Vector2d jungleBottomLeft, Vector2d jungleTopRight, Random generator, int firstAnimals)
    {
        this(startEnergy, plantEnergy, moveEnergy, bottomLeft, topRight, jungleBottomLeft, jungleTopRight, generator);
        if (firstAnimals > (getWidth()) * (getHeight()))
            throw new IllegalArgumentException("Too many startAnimals");
        for (int i = 0; i < firstAnimals; i++)
            this.addAnimal(new Animal(this.findAvailablePosition(), startEnergy, this, generator));
    }

    public WorldMap(int width, int height, int startEnergy, int moveEnergy, int plantEnergy, double jungleRatio, int firstAnimals)
    {
        this(startEnergy, plantEnergy, moveEnergy, new Vector2d(0, 0), new Vector2d(width - 1, height - 1),
                new Vector2d((int)(width - width * jungleRatio)/2, (int)(height - height * jungleRatio)/2), new Vector2d((int)(width + width * jungleRatio)/2, (int)(height + height * jungleRatio)/2),
                new Random(), firstAnimals);
        if (width < 1 || height < 1)
            throw new IllegalArgumentException("Width and Height have to be greater than 1!");
        if (firstAnimals < 0)
            throw new IllegalArgumentException("First animals number can not be less than 0");
        if (jungleRatio > 1 || jungleRatio < 0)
            throw new IllegalArgumentException("Jungle ratio has to be between 0 and 1");
    }

    public Vector2d getBottomLeft()
    {
        return bottomLeft;
    }

    public Vector2d getJungleBottomLeft()
    {
        return jungleBottomLeft;
    }

    public int getWidth()
    {
        return topRight.x - bottomLeft.x + 1;
    }

    public int getHeight()
    {
        return topRight.y - bottomLeft.y + 1;
    }

    public int getJungleWidth()
    {
        return jungleTopRight.x - jungleBottomLeft.x + 1;
    }

    public int getJungleHeight()
    {
        return jungleTopRight.y - jungleBottomLeft.y + 1;
    }

    public int getStartEnergy()
    {
        return startEnergy;
    }

    public int getPlantEnergy()
    {
        return plantEnergy;
    }

    public void daySimulation()
    {
        clearPhase();
        movePhase();
        eatPhase();
        breedPhase();
        newPlantsPhase();
    }

    public LinkedList<Animal> getAnimalsToVisualization()
    {
        return animalsHashMap.getAnimalsToVisualization();
    }

    public LinkedList<Vector2d> getPlantsToVisualization()
    {
        return new LinkedList<>(plantsSet);
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
        LinkedList<Vector2d> positions = new LinkedList<>(plantsSet);

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
        LinkedList<Vector2d> positions = new LinkedList<>();
        for (Orientation orientation : Orientation.values())
            positions.add(parentsPosition.add(orientation.toVector()));

        while(!positions.isEmpty())
        {
            int i = generator.nextInt(positions.size());
            Vector2d childPosition = positions.get(i).updateWithBoundaries(bottomLeft, topRight);
            if (isFree(childPosition))
                return childPosition;
            positions.remove(i);
        }

        for (Orientation orientation : Orientation.values())
            positions.add(parentsPosition.add(orientation.toVector()));
        return positions.get(generator.nextInt(positions.size())).updateWithBoundaries(bottomLeft, topRight);
    }

    private boolean isInJungle(Vector2d position)
    {
        return position.follows(jungleBottomLeft) && position.precedes(jungleTopRight);
    }
}
