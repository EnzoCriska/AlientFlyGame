import javax.swing.*;

public class Shot extends Fly implements Constans, Runnable {
    public Shot(){

    }

    public Shot(int x, int y){
        this.setX(x);
        this.setY(y);
        ImageIcon ic = new ImageIcon("DrawImage/shot.png");
        this.setImage(ic.getImage());
    }

    @Override
    public void run() {
        while (Board.ingame) {
            this.setY(this.getY() - 2);
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
