public class Snowman extends Obstacles {
    public Snowman(int x, int y, int dir, int h) {
        super(x, y, dir, h);
        setPic("blank.png", dir);
    }
}
