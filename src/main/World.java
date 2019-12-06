import java.util.Random;

public class World
{
    public static void main(String[] args)
    {
        Random generator = new Random(323);
        for (int i = 0; i < 32; i++)
        {
            System.out.print((generator.nextInt(8) + 1) + ", ");
        }
        System.out.println();
        for (int i = 0; i < 32; i++)
        {
            System.out.print((generator.nextInt(8) + 1) + ", ");
        }
        System.out.println();
        int i = generator.nextInt(33);
        int j = generator.nextInt(33 - i) + i;
        System.out.println(i + ", " + j);
        System.out.println(generator.nextInt(2) + ", " + generator.nextInt(2));
        //System.out.println(generator.nextInt(2));
        System.out.println(generator.nextInt(32));
    }
}
