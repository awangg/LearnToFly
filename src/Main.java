import save.CreateSave;
import save.SaveGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

public class Main extends JPanel {
    final static int FRAMEWIDTH = 1200; final static int FRAMEHEIGHT = 600;
    Timer timer;
    public static boolean movingUp = false, movingDown = false;
    static int state = 1;
    static boolean[] keys;
    Penguin player;
    private static SaveGetter saveGetter;
    private int numReps;
    private ArrayList<Clouds> clouds = new ArrayList<Clouds>();

    static final Rectangle[] buttons = new Rectangle[]{new Rectangle(1000, 450, 200, 100), new Rectangle(300, 100, 120, 120), new Rectangle(300, 300, 120, 120),
    new Rectangle(600, 100, 120, 120), new Rectangle(600, 300, 120, 120)};

    static Rectangle pointer = new Rectangle(-100, -100, 12, 20);

    public static int rocket, glider, payload, sled = 0;

    int x = 500, y = 50;

    public Main() {
        keys = new boolean[512];
        player = new Penguin(50, 50);
        numReps = 0;
        for(int i = 0; i < 8; i++){
            clouds.add(new Clouds(i));
        }

        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controls();

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
    }
    // HA YEESTI

    public void controls() {
        if(keys[KeyEvent.VK_A]) {
            movingUp = true; movingDown = false;
            player.rotateBy(5);
        }else if(keys[KeyEvent.VK_D]) {
            movingUp = false; movingDown = false;
            player.rotateBy(-5);
        }else if(keys[KeyEvent.VK_ESCAPE]) {
            System.exit(0);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(state == 1) {
                g2.setColor(Color.CYAN);
                g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);
                player.draw(g2);
                g2.setColor(Color.WHITE);
                for (Clouds c: clouds) {
                    g2.fillOval(50 + 250 * c.getX(), c.getY(), 100, 100);
                    g2.fillOval(20 + 250 * c.getX(), c.getY() + 40, c.getWidth(), 50);
                    g2.fillOval(80 + 250 * c.getX(), c.getY() + 40, c.getWidth(), 50);


                }

        }

    }

    public static void main(String[] args) throws Exception {
        saveGetter = new SaveGetter();
        CreateSave cs = new CreateSave();
        File save = new File(System.getProperty("user.home") + "/learntofly/penguin.yml");
        if(!save.exists())
            cs.create();

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
        while(true){
            if(Math.random() == .5){
                System.exit(6942);
            }
        }
    }
}
