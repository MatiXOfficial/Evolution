import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
    private HashMap<Vector2d, Boolean> plantsMap;

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

        this.animalsHashMap = new AnimalsHashMap();
        this.plantsMap = new HashMap<>();

        this.generator = generator;
    }

    public WorldMap(int startEnergy, int plantEnergy, int moveEnergy, Vector2d bottomLeft, Vector2d topRight, Vector2d jungleBottomLeft, Vector2d jungleTopRight, Random generator, int firstAnimals)
    {
        this(startEnergy, plantEnergy, moveEnergy, bottomLeft, topRight, jungleBottomLeft, jungleTopRight, generator);
        for (int i = 0; i < firstAnimals; i++);
            //animalsHashMap.addAnimal(new Animal(this.findAvailablePosition()));
    }

    public void clearPhase()
    {

    }

    public void movePhase()
    {

    }

    public void eatPhase()
    {

    }

    public void breedPhase()
    {

    }

    public void newPlantsPhase()
    {

    }

    /*private Vector2d findAvailablePosition()
    {

    }*/
}
