import java.util.LinkedList;
import java.util.Random;

public class World
{
    public static void main(String[] args)
    {
        Random generator = new Random(16);
        for (int i = 0; i < 32; i++)
        {
            System.out.printf("%3d", i);
        }
        System.out.println();
        for (int i = 0; i < 32; i++)
        {
            System.out.printf("%3d", (generator.nextInt(8) + 1));
        }
        System.out.println();
        System.out.println(generator.nextInt(8));
        for (int i = 0; i < 4; i++)
        {
            System.out.print(generator.nextInt(32) + ", ");
        }
    }
}
