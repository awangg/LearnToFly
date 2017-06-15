package src;

import java.awt.Graphics2D;
import java.awt.Color;

public class Clouds {
    int width, y, column;
    int[] component = new int[3];
    public Clouds(int col){
        y = (int) (Math.random() * 300 + 50);
        width = (int) (Math.random() * 50 + 100);
        column = col;
        
        component[0] = 50 + 250 * col;
        component[1] = 20 + 250 * col;
        component[2] = 80 + 250 * col;
    }
    
    public void draw(Graphics2D g2) {
    	g2.setColor(Color.WHITE);
    	g2.fillOval(component[0], y, 100, 100);
    	g2.fillOval(component[1], y, width, 40);
    	g2.fillOval(component[2], y, width, 40);
    }
    
    public void move() {
    	for(int i = 0; i < component.length; i++) {
			component[i] -= Main.player.getSpeed() * 100;
			if(component[i] <= 0) {
				component[i] = 1200;
			}
		}
    }

    public int getWidth(){
        return width;
    }
    
    public int getY(){
        return y;
    }
}
