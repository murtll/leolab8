package lab7.client.Entity;

/**
 * Coordinates
 *
 * @author Nagorny Leonid
 */

public class Coordinates {
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
        return "[" + x + ',' + y +  ']';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
