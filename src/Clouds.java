package src;

/**
 * Created by daniel_nee on 6/7/17.
 */
public class Clouds {
    int width, Y, x;
    public Clouds(int ex){
        Y = (int) (Math.random() * 500 + 50);
        width = (int) (Math.random() * 50 + 100);
        x = ex;
    }



    public int getWidth(){
        return width;
    }
    public int getY(){
        return Y;
    }
    public int getX(){
        return x;
    }
}//hi
