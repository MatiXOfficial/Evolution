import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

public class World
{
    public static void main(String[] args) throws FileNotFoundException
    {
        File file = new File("dane.json");
        Scanner scanner = new Scanner(file);

        HashMap<String, Double> data = new HashMap<>();
        while(scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            if (line.length() > 1)
            line = line.replaceAll("\\s","");
            line = line.replaceAll("\\{","");
            line = line.replaceAll("}","");
            line = line.replaceAll("\"","");
            line = line.replaceAll(",","");

            if(line.length() > 0)
                data.put(line.split(":")[0], Double.valueOf(line.split(":")[1]));
        }
        WorldMap map = new WorldMap((int)(double) data.get("width"), (int)(double) data.get("height"), (int)(double) data.get("startEnergy"), (int)(double) data.get("moveEnergy"),
                                    (int)(double) data.get("plantEnergy"), data.get("jungleRatio"), (int)(double) data.get("startAnimals"));
        new VisualizationFrame(map, 20);
    }
}
