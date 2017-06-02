import java.awt.Point;

public class Penguin extends Sprite {
    float vy = 0;
    double GRAVITY = 10;
    public Penguin(int x, int y) {
        super(x, y, 90);
        setPic("penguin.png", 90);
    }

    public void update() {
//        System.out.println(GRAVITY);
        if(getDir() == 90) {
            if(Main.movingUp) {
                vy = 0;
            }
            GRAVITY = .1;
            vy += GRAVITY;
        }else if(getDir() < 90 && getDir() >= 0) {
            vy += GRAVITY;
            GRAVITY += 0.01;
        }else if(getDir() > 90 && getDir() <= 180) {
            System.out.println("up");
            vy -= GRAVITY;
            GRAVITY -= 0.01;
        }
        setLoc(new Point(getLoc().x + 2, (int)(getLoc().y + vy)));
    }
}
