
//import save.CreateSave;
//import save.PenguinSave;
//import save.SaveGetter;

import save.CreateSave;
import save.PenguinSave;
import save.SaveGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.*;
import java.util.ArrayList;

public class Main extends JPanel {
	final static int FRAMEWIDTH = 1200;
	final static int FRAMEHEIGHT = 600;
	Timer timer;
	public static boolean changingPhase = false;
	static int state = 2;
	static boolean[] keys;
	static Penguin player;
	private static SaveGetter saveGetter;
	private static CreateSave cs;
	private static PenguinSave ps;
	int opaq = 0;

	boolean cont1, cont2, cont3, cont4;
	Ramp r = new Ramp();

	int maxDistance = 0;

	static double meters;
	static int money;

	static int ground = 550;

	static final Rectangle[] buttons = new Rectangle[] { new Rectangle(1000, 450, 200, 100),
			new Rectangle(300, 100, 120, 120), new Rectangle(300, 300, 120, 120), new Rectangle(600, 100, 120, 120),
			new Rectangle(600, 300, 120, 120) };

	static final Rectangle reset = new Rectangle(500, 375, 200, 50);

	static Rectangle pointer = new Rectangle(-100, -100, 12, 20);

	static int rlevel, glevel, plevel, slevel;
	static int rcost = 10, gcost = 5, pcost = 7, scost = 2;
	public static int payload, sled;
	public static double glider, rocket;

	public static boolean gameStart = false;

	public static Sprite part1, part2, studio;

	ArrayList<Clouds> clouds = new ArrayList<>();

	public Main() {
		keys = new boolean[512];
		player = new Penguin(50, 150);

		initAnimation();

		for (int i = 0; i < 8; i++) {
			clouds.add(new Clouds(i));
		}

		timer = new Timer(40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controls();
				if (state == 1) {
					player.update();
					gameStart = false;
					for (Clouds c : clouds) {
						c.move();
					}
					r.move();

					meters += player.getSpeed();
					if (hitGround()) {
						state = 3;
					}
				}
				repaint();
			}
		});
		timer.start();

		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				keys[e.getKeyCode()] = true;
			}

			@Override
			public void keyReleased(KeyEvent e) {
				keys[e.getKeyCode()] = false;
			}
		});

		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// System.out.println("yeet");
				if (state == 2) {
					if (pointer.intersects(buttons[0])) {
						reset();
						if (!(rlevel == 0 && glevel == 0)) {
							player.changePicture(glevel, rlevel);
						}
						meters = 0;
						gameStart = true;
						state = 0;
					}
					if (pointer.intersects(buttons[1]) && money >= rcost && rlevel < 3) {
						rlevel++;
						money -= rcost;
						rcost *= 4;

						rocket += 0.1;
						player.fuel *= 2;
					}
					if (pointer.intersects(buttons[2]) && money >= gcost && glevel < 3) {
						glevel++;
						money -= gcost;
						gcost *= 4;

						glider += 0.23;
					}
					if (pointer.intersects(buttons[3]) && money >= pcost && plevel < 3) {
						plevel++;
						money -= pcost;
						pcost *= 4;

						payload += 100;
					}
					if (pointer.intersects(buttons[4]) && money >= scost && slevel < 3) {
						slevel++;
						money -= scost;
						scost *= 4;

						sled += 10;
					}
				} else if (state == 3) {
					if (pointer.intersects(reset)) {
						money += (int) (meters * 0.5);
						state = 2;
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				pointer = new Rectangle(e.getX(), e.getY(), 12, 16);
			}
		});
	}

	public void reset() {
		player.setLoc(new Point(50, 150));
		ArrayList<Clouds> clouds = new ArrayList<>();
		r = new Ramp();
		for (int i = 0; i < 8; i++) {
			clouds.add(new Clouds(i));
		}
	}

	public void initAnimation() {
		part1 = new Sprite(350, 0, 90);
		part2 = new Sprite(659, 0, 90);
		studio = new Sprite(0, 0, 90);
		part1.setPic("Part1.png", 90);
		part2.setPic("Part2.png", 90);
		studio.setPic("Studio.png", 90);
	}

	public void controls() {
		if (keys[KeyEvent.VK_A] && player.getDir() < 180) {
			player.rotateBy(5);
		} else if (keys[KeyEvent.VK_D] && player.getDir() > 0) {
			player.rotateBy(-5);
		} else if (keys[KeyEvent.VK_ESCAPE]) {
			System.exit(0);
		} else if (keys[KeyEvent.VK_SPACE] && rlevel > 0) {
			player.rocket = true;
		} else if (!keys[KeyEvent.VK_SPACE] && rlevel > 0) {
			player.rocket = false;
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (state == 0) {
			g2.setColor(Color.CYAN);
			g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);

			g2.setColor(Color.BLACK);
			g2.drawString("Meters: " + (int) meters, 1050, 50);
			if (player.fuel < 0) {
				g2.drawString("Fuel: EMPTY", 1000, 65);
			} else {
				g2.drawString("Fuel Left: " + player.fuel, 1000, 65);
			}

			for (Clouds c : clouds) {
				c.draw(g2);
			}

			g2.setColor(Color.WHITE);
			g2.fillRect(0, ground, FRAMEWIDTH, 100);

//			System.out.println(player.getLoc().x);

			if (player.getLoc().x <= 294) {
				player.getLoc().x += 4;
			} else {
				cont3 = true;
			}
			if (player.getLoc().x < 320 && player.getLoc().x >= 298) {
				player.getLoc().x++;
				player.getLoc().y -= sled;
			} else {
				cont4 = true;
			}
			if (cont3 && cont4) {
				state++;
				cont3 = false;
				cont4 = false;
			}
			r.draw(g2);
			player.draw(g2);
		} else if (state == 1) {
			g2.setColor(Color.CYAN);
			g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);

			g2.setColor(Color.BLACK);
			g2.drawString("Meters: " + (int) meters, 1050, 50);
			if (player.fuel < 0) {
				g2.drawString("Fuel: EMPTY", 1000, 65);
			} else {
				g2.drawString("Fuel Left: " + player.fuel, 1000, 65);
			}

			for (Clouds c : clouds) {
				c.draw(g2);
			}

			g2.setColor(Color.WHITE);
			g2.fillRect(0, ground, FRAMEWIDTH, 100);

			r.draw(g2);
			player.draw(g2);
		} else if (state == 2) {
			try {
				g2.drawImage(ImageIO.read(new File("res/sky.jpg")), 0, 0, FRAMEWIDTH, FRAMEHEIGHT, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (pointer.intersects(buttons[1]) && money >= rcost && rlevel < 3) {
				g2.setColor(Color.GREEN);
				g2.fill(buttons[1]);
			} else if (pointer.intersects(buttons[1]) && (money < rcost || rlevel >= 3)) {
				g2.setColor(Color.RED);
				g2.fill(buttons[1]);
			} else if (pointer.intersects(buttons[2]) && money >= gcost && glevel < 3) {
				g2.setColor(Color.GREEN);
				g2.fill(buttons[2]);
			} else if (pointer.intersects(buttons[2]) && (money < gcost || glevel >= 3)) {
				g2.setColor(Color.RED);
				g2.fill(buttons[2]);
			} else if (pointer.intersects(buttons[3]) && money >= pcost && plevel < 3) {
				g2.setColor(Color.GREEN);
				g2.fill(buttons[3]);
			} else if (pointer.intersects(buttons[3]) && (money < pcost || plevel >= 3)) {
				g2.setColor(Color.RED);
				g2.fill(buttons[3]);
			} else if (pointer.intersects(buttons[4]) && money >= scost && slevel < 3) {
				g2.setColor(Color.GREEN);
				g2.fill(buttons[4]);
			} else if (pointer.intersects(buttons[4]) && (money < scost || slevel >= 3)) {
				g2.setColor(Color.RED);
				g2.fill(buttons[4]);
			}

			g2.setColor(Color.BLACK);
			for (int i = 1; i < buttons.length; i++) {
				Rectangle r = buttons[i];
				g2.draw(r);
			}

			g2.setColor(Color.BLACK);
			g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
			g2.drawString(Integer.toString(rlevel), (int) buttons[1].getX() + 55, (int) (buttons[1].getY() + 30));
			g2.drawString("Rocket", (int) buttons[1].getX() + 35, (int) (buttons[1].getY() + 55));
			g2.drawString(Integer.toString(rcost), (int) buttons[1].getX() + xpos(rcost),
					(int) (buttons[1].getY() + 70));

			g2.drawString(Integer.toString(glevel), (int) buttons[2].getX() + 55, (int) (buttons[2].getY() + 30));
			g2.drawString("Glider", (int) buttons[2].getX() + 40, (int) (buttons[2].getY() + 55));
			g2.drawString(Integer.toString(gcost), (int) buttons[2].getX() + xpos(gcost),
					(int) (buttons[2].getY() + 70));

			g2.drawString(Integer.toString(plevel), (int) buttons[3].getX() + 55, (int) (buttons[3].getY() + 30));
			g2.drawString("Payload", (int) buttons[3].getX() + 35, (int) (buttons[3].getY() + 55));
			g2.drawString(Integer.toString(pcost), (int) buttons[3].getX() + xpos(pcost),
					(int) (buttons[3].getY() + 70));

			g2.drawString(Integer.toString(slevel), (int) buttons[4].getX() + 55, (int) (buttons[4].getY() + 30));
			g2.drawString("Vaseline", (int) buttons[4].getX() + 35, (int) (buttons[4].getY() + 55));
			g2.drawString(Integer.toString(scost), (int) buttons[4].getX() + xpos(scost),
					(int) (buttons[4].getY() + 70));

			if (pointer.intersects(buttons[0])) {
				g2.setColor(Color.GREEN);
				g2.fill(buttons[0]);
			} else {
				g2.setColor(Color.BLACK);
				g2.draw(buttons[0]);
			}

			g2.setColor(Color.BLACK);
			g2.drawString("Money: " + money, 1050, 50);
		} else if (state == 3) {
			g2.setColor(Color.CYAN);
			g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);

			g2.setColor(Color.BLACK);
			g2.drawString("Meters: " + (int) meters, 1050, 50);
			if (player.fuel < 0) {
				g2.drawString("Fuel: EMPTY", 1000, 65);
			} else {
				g2.drawString("Fuel Left: " + player.fuel, 1000, 65);
			}

			for (Clouds c : clouds) {
				c.draw(g2);
			}

			g2.setColor(Color.WHITE);
			g2.fillRect(0, ground, FRAMEWIDTH, 100);

			r.draw(g2);
			player.draw(g2);

			maxDistance = (int) Math.max(maxDistance, meters);
			g2.setColor(Color.BLACK);
			g2.drawRect(200, 300, 800, 50);
			g2.setColor(Color.ORANGE);
			g2.fillRect(200, 300, (int) ((meters / maxDistance) * 800), 50);

			g2.setColor(Color.BLACK);
			g2.drawString("Distance: " + (int) meters, 200, 315);
			g2.drawString("$" + (int) (meters * 0.5), 950, 315);

			g2.draw(reset);
		} else if (state == 5) {
			studio.draw(g2);
			part1.draw(g2);
			part2.draw(g2);

			if (part1.getLoc().x > 0) {
				part1.getLoc().x -= 3;
			} else {
				cont1 = true;
			}
			if (part2.getLoc().x < 900) {
				part2.getLoc().x += 2;
			} else {
				cont2 = true;
			}

			g2.setColor(new Color(0, 0, 0, opaq));
			if (cont1 && cont2) {
				if (opaq + 4 <= 252) {
					opaq += 4;
				}
				g2.setColor(new Color(0, 0, 0, opaq));
			}
			g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);
			if (opaq >= 252) {
				state = 2;
			}
		}
	}

	static boolean hitGround() {
		if (player.getBoundingRectangle().intersects(new Rectangle(0, ground, FRAMEWIDTH, 600 - ground))) {
			return true;
		}
		return false;
	}

	static int xpos(int cost) {
		if (cost < 100) {
			return 50;
		} else {
			return 45;
		}
	}

	public static void main(String[] args) throws Exception {
         saveGetter = new SaveGetter();
         cs = new CreateSave();
         File save = new File(System.getProperty("user.home") +
         "/learntofly/penguin.yml");
         if(!save.exists())
         cs.create(1,0.1,1,1, 0);
         ps = saveGetter.getPenguin();
         rocket = ps.rocket;
         glider = ps.glider;
         payload = ps.payload;
         sled = ps.vaseline;
         money = ps.money;

		JFrame window = new JFrame("Learn To Fly!");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, FRAMEWIDTH, FRAMEHEIGHT + 22); // (x, y, w, h) 22
																// due to title
																// bar.

		Main panel = new Main();
		panel.setSize(FRAMEWIDTH, FRAMEHEIGHT);

		panel.setFocusable(true);
		panel.grabFocus();

		window.add(panel);
		window.setVisible(true);
		window.setResizable(false);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                cs.create(rocket, glider, payload, sled, money);
            }
        });
	}
}
