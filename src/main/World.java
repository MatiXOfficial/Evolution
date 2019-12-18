import java.util.LinkedList;
import java.util.Random;

public class World
{
    public static void main(String[] args)
    {
        Random generator = new Random(15);
        /*for (int i = 0; i < 32; i++)
        {
            System.out.printf("%3d", i);
        }
        System.out.println();
        for (int i = 0; i < 32; i++)
        {
            System.out.printf("%3d", (generator.nextInt(8) + 1));
        }
        System.out.println();
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

        int i = generator.nextInt(32 + 1);
        int j = generator.nextInt(32 + 1 - i) + i;
        System.out.println(i);
        System.out.println(j);

        System.out.println(generator.nextInt(2));
        System.out.println(generator.nextInt(2));

        System.out.println(generator.nextInt(32));*/
        for (int i = 0; i<  20; i++)
            System.out.println(generator.nextInt(2));
    }
}
