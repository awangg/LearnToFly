package src;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Penguin extends Sprite {
    double x, y;
    double vy = 1;
    double GRAVITY = 0.1;
    int speed = 1;
    boolean rocket;
    
    int r, g, p, s;

    int fuel = 100;

    public Penguin(int x, int y) {
        super(x, y, 90);
        setPic("penguin (Glider, Rocket).png", 90);
    }

    public void changePicture(int r, int c) {
        setPic(r + "," + c + ".png", 90);
    }

    public void update() {
    	if(Main.gameStart) {
    		r = Main.rocket;
    		g = Main.glider;
    		p = Main.payload;
    		s = Main.sled;
    	}
        if(rocket) {
            if(fuel > 0 && speed < r) {
                speed += 1;
                fuel--;
            }
            add(-2);
            GRAVITY = 0.1;
            vy = 1;
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
                    vy -= g;
                    if(g-2 > 0) {
                    	g-=4;
                    }
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