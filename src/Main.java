import javax.swing.*;

public class Main extends JPanel {
    final static int FRAMEWIDTH = 1200; final static int FRAMEHEIGHT = 600;
    public static void main(String[] args) throws Exception {
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
