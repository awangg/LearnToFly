import save.SaveGetter;

import javax.swing.*;
import java.io.File;

public class Main extends JPanel {
    final static int FRAMEWIDTH = 1200, FRAMEHEIGHT = 600;
    private static SaveGetter saveGetter;
    public static void main(String[] args) throws Exception {
        saveGetter = new SaveGetter();

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
