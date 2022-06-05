package lab6.server.Smth;

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
        String info = "Здесь находятся координаты\n";
        info += ("Координаты: \n");
        info += (" x:" + x + '\n');
        info += ("y:" + y + '\n');
        return info;
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
