package lab5.Smth;

/**
 * Coordinates
 *
 * @author Nagorny Leonid
 */

public class Coordinates {
    private final int x;
    private final int y;

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
}
