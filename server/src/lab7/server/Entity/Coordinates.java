package lab7.server.Entity;

/**
 * Coordinates
 *
 * @author Nagorny Leonid
 */

public class Coordinates implements Comparable<Coordinates> {
    private final int x;
    private final int y;

    public Coordinates() {
        this.x = 0;
        this.y = 0;
    }

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Coordinates o) {
        return (int) (Math.sqrt(this.x * this.x + this.y * this.y) - Math.sqrt(o.x * o.x + o.y * o.y));
    }
}
