package src; 

//import save.CreateSave;
//import save.PenguinSave;
//import save.SaveGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.*;

public class Main extends JPanel {
    final static int FRAMEWIDTH = 1200; final static int FRAMEHEIGHT = 600;
    Timer timer;
    public static boolean changingPhase = false;
    static int state = 2;
    static boolean[] keys;
    static Penguin player;
//    private static SaveGetter saveGetter;
    int opaq = 0;
    
    boolean cont1, cont2 = false;

    static double meters;
    static int money = 999;

    static int ground = 550;

    static final Rectangle[] buttons = new Rectangle[]{new Rectangle(1000, 450, 200, 100), new Rectangle(300, 100, 120, 120), new Rectangle(300, 300, 120, 120),
    new Rectangle(600, 100, 120, 120), new Rectangle(600, 300, 120, 120)};

    static Rectangle pointer = new Rectangle(-100, -100, 12, 20);

    static int rlevel, glevel, plevel, slevel;
    static int rcost = 100, gcost = 50, pcost = 75, scost = 25;
    public static int payload = 0, sled = 0;
    public static double glider = 0.1, rocket = 0;
    
    public static boolean gameStart = false;
    
    public static Sprite part1, part2, studio;

    int x = 500, y = 50;

    public Main() {
        keys = new boolean[512];
        player = new Penguin(600, 50);
        
        initAnimation();
        
        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controls();
                if(state == 1) {
                    player.update();
                    gameStart = false;
                    
                    meters+=player.getSpeed();
                    if(hitGround()) {
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
//                System.out.println("yeet");
                if(state == 2) {
                    if(pointer.intersects(buttons[0])) {
                        if(!(rlevel == 0 && glevel == 0)) {
                            player.changePicture(glevel, rlevel);
                        }
                        meters = 0;
                        gameStart = true;
                        state = 1;
                    }
                    if(pointer.intersects(buttons[1]) && money > rcost && rlevel < 3) {
                        rlevel++;
                        money -= rcost;
                        rcost *= 2;

                        rocket += 0.1;
                    }
                    if(pointer.intersects(buttons[2]) && money > gcost && glevel < 3) {
                        glevel++;
                        money -= gcost;
                        gcost *= 2;

                        glider += 0.23;
                    }
                    if(pointer.intersects(buttons[3]) && money > pcost && plevel < 3) {
                        plevel++;
                        money -= pcost;
                        pcost *= 2;

                        payload += 100;
                    }
                    if(pointer.intersects(buttons[4]) && money > scost && slevel < 3) {
                        slevel++;
                        money -= scost;
                        scost *= 2;

                        sled += 10;
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
    
    public void initAnimation() {
    	part1 = new Sprite(350, 0, 90);
        part2 = new Sprite(659, 0, 90);
        studio = new Sprite(0, 0, 90);
        part1.setPic("Part1.png", 90);
        part2.setPic("Part2.png", 90);
        studio.setPic("Studio.png", 90);
    }

    public void controls() {
        if(keys[KeyEvent.VK_A] && player.getDir() < 180) {
            player.rotateBy(5);
        }else if(keys[KeyEvent.VK_D] && player.getDir() > 0) {
            player.rotateBy(-5);
        }else if(keys[KeyEvent.VK_ESCAPE]) {
            System.exit(0);
        }else if(keys[KeyEvent.VK_SPACE] && rlevel > 0) {
            player.rocket = true;
        }else if(!keys[KeyEvent.VK_SPACE] && rlevel > 0) {
            player.rocket = false;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(state == 1) {
            g2.setColor(Color.CYAN);
            g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);
            player.draw(g2);

            g2.setColor(Color.BLACK);
            g2.drawString("Meters: " + (int)meters, 1050, 50);
            if(player.fuel < 0) {
            	g2.drawString("Fuel: EMPTY", 1000, 65);
            }else {
            	g2.drawString("Fuel Left: " + player.fuel, 1000, 65);
            }

            g2.setColor(Color.WHITE);
            g2.fillRect(0, ground, FRAMEWIDTH, 100);
        }else if(state == 2) {
            try {
                g2.drawImage(ImageIO.read(new File("res/sky.jpg")), 0, 0, FRAMEWIDTH, FRAMEHEIGHT, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(pointer.intersects(buttons[1]) && money > rcost && rlevel < 3) {
                g2.setColor(Color.GREEN);
                g2.fill(buttons[1]);
            }else if(pointer.intersects(buttons[1]) && (money < rcost || rlevel >= 3)) {
                g2.setColor(Color.RED);
                g2.fill(buttons[1]);
            }else if(pointer.intersects(buttons[2]) && money > gcost && glevel < 3) {
                g2.setColor(Color.GREEN);
                g2.fill(buttons[2]);
            }else if(pointer.intersects(buttons[2]) && (money < gcost || glevel >= 3)) {
                g2.setColor(Color.RED);
                g2.fill(buttons[2]);
            }else if(pointer.intersects(buttons[3]) && money > pcost && plevel < 3) {
                g2.setColor(Color.GREEN);
                g2.fill(buttons[3]);
            }else if(pointer.intersects(buttons[3]) && (money < pcost || plevel >= 3)) {
                g2.setColor(Color.RED);
                g2.fill(buttons[3]);
            }else if(pointer.intersects(buttons[4]) && money > scost && slevel < 3) {
                g2.setColor(Color.GREEN);
                g2.fill(buttons[4]);
            }else if(pointer.intersects(buttons[4]) && (money < scost || slevel >= 3)) {
                g2.setColor(Color.RED);
                g2.fill(buttons[4]);
            }

            g2.setColor(Color.BLACK);
            for(int i = 1; i < buttons.length; i++) {
                Rectangle r = buttons[i];
                g2.draw(r);
            }


            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
            g2.drawString(Integer.toString(rlevel), (int)buttons[1].getX() + 55, (int)(buttons[1].getY() + 30));
            g2.drawString("Rocket", (int)buttons[1].getX() + 35, (int)(buttons[1].getY() + 55));
            g2.drawString(Integer.toString(rcost), (int)buttons[1].getX() + xpos(rcost), (int)(buttons[1].getY() + 70));

            g2.drawString(Integer.toString(glevel), (int)buttons[2].getX() + 55, (int)(buttons[2].getY() + 30));
            g2.drawString("Glider", (int)buttons[2].getX() + 40, (int)(buttons[2].getY() + 55));
            g2.drawString(Integer.toString(gcost), (int)buttons[2].getX() + xpos(gcost), (int)(buttons[2].getY() + 70));

            g2.drawString(Integer.toString(plevel), (int)buttons[3].getX() + 55, (int)(buttons[3].getY() + 30));
            g2.drawString("Payload", (int)buttons[3].getX() + 35, (int)(buttons[3].getY() + 55));
            g2.drawString(Integer.toString(pcost), (int)buttons[3].getX() + xpos(pcost), (int)(buttons[3].getY() + 70));

            g2.drawString(Integer.toString(slevel), (int)buttons[4].getX() + 55, (int)(buttons[4].getY() + 30));
            g2.drawString("Vaseline", (int)buttons[4].getX() + 35, (int)(buttons[4].getY() + 55));
            g2.drawString(Integer.toString(scost), (int)buttons[4].getX() + xpos(scost), (int)(buttons[4].getY() + 70));

            if(pointer.intersects(buttons[0])) {
                g2.setColor(Color.GREEN);
                g2.fill(buttons[0]);
            }else {
                g2.setColor(Color.BLACK);
                g2.draw(buttons[0]);
            }

            g2.setColor(Color.BLACK);
            g2.drawString("Money: " + money, 1050, 50);
        }else if(state == 3) {
        	g2.setColor(Color.RED);
        	g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);
        }else if(state == 5) {
    		studio.draw(g2);
        	part1.draw(g2);
    		part2.draw(g2);
    		
    		if(part1.getLoc().x > 0) {
    			part1.getLoc().x-=3;
    		}else {
    			cont1 = true;
    		}
    		if(part2.getLoc().x < 900) {
    			part2.getLoc().x+=2;
    		}else {
    			cont2 = true;
    		}
    		
    		g2.setColor(new Color(0, 0, 0, opaq));
    		if(cont1 && cont2) {
    			if(opaq + 4 <= 252) {
    				opaq+=4;
    			}
    			g2.setColor(new Color(0, 0, 0, opaq));
    		}
    		g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);
    		if(opaq >= 252) {
    			state = 2;
    		}
        }
    }

    static boolean hitGround() {
        if(player.getBoundingRectangle().intersects(new Rectangle(0, ground, FRAMEWIDTH, 600 - ground))) {
            return true;
        }
        return false;
    }

    static int xpos(int cost) {
        if(cost < 100) {
            return 50;
        }else {
            return 45;
        }
    }

    public static void main(String[] args) throws Exception {
//        saveGetter = new SaveGetter();
//        CreateSave cs = new CreateSave();
//        File save = new File(System.getProperty("user.home") + "/learntofly/penguin.yml");
//        if(!save.exists())
//            cs.create();
//        PenguinSave ps = saveGetter.getPenguin();
//        rocket = ps.rocket;
//        glider = ps.glider;
//        payload = ps.payload;
//        sled = ps.vaseline;

        JFrame window = new JFrame("Learn To Fly!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, FRAMEWIDTH, FRAMEHEIGHT + 22); //(x, y, w, h) 22 due to title bar.

        Main panel = new Main();
        panel.setSize(FRAMEWIDTH, FRAMEHEIGHT);

        panel.setFocusable(true);
        panel.grabFocus();

        window.add(panel);
        window.setVisible(true);
        window.setResizable(false);
    }
}
