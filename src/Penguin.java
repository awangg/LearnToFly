import java.awt.Point;
import java.awt.geom.Point2D;

public class Penguin extends Sprite {
    double x, y;
    double vy = 1;
    double GRAVITY = 0.1;
    int speed;
    boolean rocket;

    public Penguin(int x, int y) {
        super(x, y, 90);
        setPic("penguin (Glider, Rocket).png", 90);
    }

    public void changePicture(int r, int c) {
        setPic(r + "," + c + ".png", 90);
    }

    public void update() {
        if(rocket) {
        }else {
            if (getDir() < 90) {
                vy += GRAVITY;
                GRAVITY += 0.01;
                Main.changingPhase = true;
            } else if (getDir() > 90) {
                vy -= GRAVITY;
                GRAVITY -= 0.1;
            } else if (getDir() == 90) {
                if (Main.changingPhase) {
                    vy -= Main.glider;
                    Main.changingPhase = false;
                }
                GRAVITY = 0.1;
            }
            add(vy);
        }
    }

    public int getSpeed() {
        return speed;
    }
}