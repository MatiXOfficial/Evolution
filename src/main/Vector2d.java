import java.util.Random;

public class Vector2d
{
    public final int x;
    public final int y;

    public Vector2d(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        return "(" + this.x + "," + this.y + ")";
    }

    public Vector2d upperRight(Vector2d that)
    {
        return new Vector2d(Math.max(this.x, that.x), Math.max(this.y, that.y));
    }

    public Vector2d lowerLeft(Vector2d that)
    {
        return new Vector2d(Math.min(this.x, that.x), Math.min(this.y, that.y));
    }

    public Vector2d add(Vector2d that)
    {
        return new Vector2d(this.x + that.x, this.y + that.y);
    }

    public Vector2d subtract(Vector2d that)
    {
        return new Vector2d(this.x - that.x, this.y - that.y);
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (!(obj instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) obj;
        if (this.x == that.x && this.y == that.y)
            return true;
        else
            return false;
    }

    public boolean precedes(Vector2d that)
    {
        return (this.x <= that.x && this.y <= that.y);
    }

    public boolean follows(Vector2d that)
    {
        return (this.x >= that.x && this.y >= that.y);
    }

    public Vector2d opposite()
    {
        return new Vector2d(-this.x, -this.y);
    }

    public Vector2d updateWithBoundaries(Vector2d bottomLeft, Vector2d topRight)
    {
        int dx = topRight.x - bottomLeft.x + 1, dy = topRight.y - bottomLeft.y + 1;
        int xNew = bottomLeft.x + (this.x - bottomLeft.x + dx) % dx;
        int yNew = bottomLeft.y + (this.y - bottomLeft.y + dy) % dy;
        return new Vector2d(xNew, yNew);
    }

    public static Vector2d randomWithBoundaries(Vector2d bottomLeft, Vector2d topRight, Random generator)
    {
        int dx = topRight.x - bottomLeft.x + 1, dy = topRight.y - bottomLeft.y + 1;
        int x = generator.nextInt(dx) + bottomLeft.x;
        int y = generator.nextInt(dy) + bottomLeft.y;
        return new Vector2d(x, y);
    }

    public int hashCode()
    {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }
}