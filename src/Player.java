import javax.swing.*;
import java.awt.event.KeyEvent;


public class Player extends Fly implements Runnable, Constans {

    private final int START_X = 270;
    private final int START_Y = 290;
    private int wigth;
    public Player(){
        this.setX(START_X);
        this.setY(START_Y);
        ImageIcon ic = new ImageIcon("DrawImage/player.png");
        this.setImage(ic.getImage());
        wigth = ic.getImage().getWidth(null);
    }

    private void go(){
        x+=dx;

        if (x <= 2) x = 2;
        if (x >= BOARD_WIDTH - wigth) x = BOARD_WIDTH - 2*wigth;
    }

    public void pressed(KeyEvent e){
        System.out.println(e);
        if (e.getKeyChar() == KeyEvent.VK_A+32){
            dx = -2;
        }
        if (e.getKeyChar() == KeyEvent.VK_D+32){
            dx = 2;
        }
    }

    public void released(KeyEvent e){
        if (e.getKeyChar() == KeyEvent.VK_A+32){
            dx = 0;
        }
        if (e.getKeyChar() == KeyEvent.VK_D+32){
            dx = 0;
        }
    }

    @Override
    public void run() {
        while (Board.ingame){
            go();
            System.out.println("player going " + dx);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
