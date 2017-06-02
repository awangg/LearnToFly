import java.awt.Point;

public class Penguin extends Sprite {
    float vy = 0;
    double GRAVITY = 0.1;
    public Penguin(int x, int y) {
        super(x, y, 90);
        setPic("penguin.png", 90);
    }

    public void update() {
    }
}
