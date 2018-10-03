import javax.swing.*;

public class Alient extends Fly implements Constans, Runnable {
    Bomb bomb;
    private int dis = -1;
    public Alient(){

    }

    public Alient(int x, int y){
        this.x = x;
        this.y = y;
        ImageIcon ic = new ImageIcon("DrawImage/alien.png");
        this.setImage(ic.getImage());
        bomb = new Bomb(x, y);
    }

    public void go(){
        this.x += dis;
        if (x <= 0){
            dis = -1*dis;
            this.setY(this.getY()+15);
        }
        if (x >= BOARD_WIDTH-12){
            dis = -1*dis;
            this.setY(this.getY()+15);
        }



    }

    public int getDis() {
        return dis;
    }

    public void setDis(int dis) {
        this.dis = dis;
    }

    public Bomb getBomb(){
        return bomb;
    }

    @Override
    public void run() {
        while (Board.ingame) {
            go();

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }



}
