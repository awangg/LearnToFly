package src;

import java.awt.Point;

public class Penguin extends Sprite {
    double x, y;
    double vy = 5;
    double GRAVITY = 0.1;
    double speed = 0.25;
    boolean rocket;
    double g, r;
    
    int fuel = 100;

    public Penguin(int x, int y) {
        super(x, y, 90);
        setPic("penguin (Glider, Rocket).png", 90);
    }
    
    public void update() {
    	if(Main.gameStart) {
    		r = Main.rocket;
    		g = Main.glider;
    	}
    	Point loc = getLoc();
    	if(getDir() < 90) {
    		if(rocket && fuel >= 0) {
    			vy = r * 10;
    			if(speed < 10*r) {
    				speed += r;
    			}
    			fuel--;
    		}else {
    			speed = 0.25;
    			vy += GRAVITY;
    			GRAVITY += 0.1;
    		}
    		Main.changingPhase = true;
    	}else if(getDir() > 90) {
    		if(rocket && fuel >= 0) {
    			vy = -r;
    			if(speed < 10*r) {
    				speed += r;
    			}
    			fuel--;
    		}else {
    			speed = 0.25;
    			vy -= GRAVITY;
    			GRAVITY -= 0.1;
    		}
    	}else if(getDir() == 90) {
    		if(rocket && fuel >= 0) {
    			vy = 0;
    			if(speed < 10*r) {
    				speed += r;
    			}
    			fuel--;
    		}else {
    			speed = 0.25;
	    		if(Main.changingPhase) {
	    			vy -= g * vy;
	    			if(g >= 5) g -= 5;
	    			Main.changingPhase = false;
	    		}
    		}
    		GRAVITY = 0.1;
    		vy += GRAVITY;
    	}
    	loc.y += vy;
    }

    public void changePicture(int r, int c) {
        setPic(r + "," + c + ".png", 90);
    }

    public double getSpeed() {
        return speed;
    }
}