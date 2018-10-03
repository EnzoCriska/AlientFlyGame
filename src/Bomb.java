import javax.swing.*;

public class Bomb extends Fly{
    private boolean destroy = true;
    public Bomb(){}


    public Bomb(int x, int y){
        setDestroy(true);
        this.x = x;
        this.y = y;

        ImageIcon ic = new ImageIcon("DrawImage/bomb.png");
        this.setImage(ic.getImage());
    }

    public boolean isDestroy() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

}