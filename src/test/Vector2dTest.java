import org.junit.Assert;
import org.junit.Test;

import java.util.Vector;

public class Vector2dTest
{
    @Test
    public void testUpperRight()
    {
        Assert.assertEquals(new Vector2d(5, 15), new Vector2d(5, 3).upperRight(new Vector2d(-1, 15)));
        Assert.assertEquals(new Vector2d(123, -7), new Vector2d(2, -7).upperRight(new Vector2d(123, -23)));
    }

    @Test public void testLowerLeft()
    {
        Assert.assertEquals(new Vector2d(-1, 3), new Vector2d(5, 3).lowerLeft(new Vector2d(-1, 15)));
        Assert.assertEquals(new Vector2d(2, -23), new Vector2d(2, -7).lowerLeft(new Vector2d(123, -23)));
    }

    @Test public void testAdd()
    {
        Assert.assertEquals(new Vector2d(12, 7), new Vector2d(-2, 17).add(new Vector2d(14, -10)));
    }

    @Test public void testSubtract()
    {
        Assert.assertEquals(new Vector2d(5, -3), new Vector2d(2, 4).subtract(new Vector2d(-3, 7)));
    }

    @Test public void testEquals()
    {
        Vector2d obj = new Vector2d(1, 10);
        Vector2d obj2 = obj;
        Assert.assertTrue(obj.equals(obj2));
        Assert.assertFalse(obj.equals(new Object()));
        Assert.assertTrue(obj.equals(new Vector2d(1, 10)));
        Assert.assertFalse(obj.equals(new Vector2d(2, 9)));
    }

    @Test public void testPrecedes()
    {
        Assert.assertTrue(new Vector2d(3, 4).precedes(new Vector2d(3, 4)));
        Assert.assertTrue(new Vector2d(2, 7).precedes(new Vector2d(8, 19)));
        Assert.assertFalse(new Vector2d(-5, 16).precedes(new Vector2d(-6, 17)));
        Assert.assertFalse(new Vector2d(2, 5).precedes(new Vector2d(1, 3)));
    }

    @Test public void testFollows()
    {
        Assert.assertTrue(new Vector2d(3, 4).follows(new Vector2d(3, 4)));
        Assert.assertFalse(new Vector2d(2, 7).follows(new Vector2d(8, 19)));
        Assert.assertFalse(new Vector2d(-5, 16).follows(new Vector2d(-6, 17)));
        Assert.assertTrue(new Vector2d(2, 5).follows(new Vector2d(1, 3)));
    }

    @Test public void testOpposite()
    {
        Assert.assertEquals(new Vector2d(0, 0), new Vector2d(0, 0).opposite());
        Assert.assertEquals(new Vector2d(-1, -1), new Vector2d(1, 1).opposite());
    }
}
