import java.awt.Point;
import java.awt.geom.Point2D;

public class Penguin extends Sprite {
    double x, y;
    double vy = 1;
    double GRAVITY = 0.1;
    int speed;

    public Penguin(int x, int y) {
        super(x, y, 90);
        setPic("penguin.png", 90);
    }

    public void update() {
        if(getDir() < 90) {
            vy += GRAVITY;
            GRAVITY += 0.01;
            Main.changingPhase = true;
        }else if(getDir() > 90) {
            vy -= GRAVITY;
            GRAVITY -= 0.1;
        }else if(getDir() == 90) {
            if(Main.changingPhase) {
                vy -= 20;
                Main.changingPhase = false;
            }
            GRAVITY = 0.1;
        }
        add(vy);
    }

    public int getSpeed() {
        return speed;
    }
}