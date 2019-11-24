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

    public boolean equals(Object other)
    {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
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
}