package src;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Animation {
	Timer t;
	
	public void play(Graphics2D g2) {
//		g2.setColor(Color.RED);
//		g2.fillRect(0, 0, Main.FRAMEWIDTH, Main.FRAMEHEIGHT);
		t = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.part1.draw(g2);
				Main.part2.draw(g2);
				
				Main.part1.getLoc().x--;
				Main.part2.getLoc().x++;
				
			}
			
		});
		t.start();
		
	}

}
