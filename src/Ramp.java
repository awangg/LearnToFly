import java.awt.Color;
import java.awt.*;

public class Ramp {
	int rectX = 0;
	public void draw(Graphics2D g2) {
		g2.setColor(Color.BLUE);
    	g2.fillRect(rectX, 200, 300, 350);
	}
	
	public void move() {
		if(rectX > -500) {
			rectX -= Main.player.getSpeed() * 100;
		}
	}
}
