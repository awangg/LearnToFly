package src;

public class Obstacles extends Sprite {
    double health;

    public Obstacles(int x, int y, int dir, int h) {
        super(x, y, dir);
        health = h;
        setPic("blank.png", dir);
    }
    public boolean isDestroyed(Penguin p){
        if (p.intersects(this)&& Main.payload * p.getSpeed() >= health){
            return true;
        }
        return false;
    }
}
