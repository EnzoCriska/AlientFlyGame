import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel implements Runnable, Constans {
    Thread thread, thread2, thread3, thread4;
    Thread[] listThread = new Thread[24];
    Player player;
    Alient alient;
    Shot shot;
    Bomb bomb;
    public static boolean  ingame = true;
    int death = 0;
    String notic = "Game ez for you!";
    ArrayList<Alient> list = new ArrayList<>();


    public Board(){
        addKeyListener( new AdapteKey());
        setFocusable(true);
        setSize(BOARD_HEIGHT,BOARD_WIDTH);
        setBackground(Color.BLACK);


    }

    public void boardInit(){
        player = new Player();
        shot = new Shot();

        for (int i = 0; i <4; i++){
            for (int j = 0; j <6; j++){
                alient = new Alient(170 + j*18, 30+ i*18);

                listThread[i+j] = new Thread(alient);
                listThread[i+j].start();
                list.add(alient);
            }
        }

        thread = new Thread(this);
        thread2 = new Thread(player);

        thread4 = new Thread(shot);
        thread.start();
        thread2.start();

        thread4.start();
    }

    public void addNotify(){
        super.addNotify();
        boardInit();
    }

    public void paint(Graphics g){
        super.paint(g);



        g.setColor(Color.GREEN);
        g.drawLine(0, 300, BOARD_WIDTH, 300);
        if (player.isVisible){
            g.drawImage(player.getImage(), player.getX(), player.getY(), null);
        }
        if (player.isDying()){
            player.die();

        }
        for (Alient ali : list){
            if (ali.isVisible){
                g.drawImage(ali.getImage(), ali.getX(), ali.getY(), null);
            }
            if (ali.isDying()){
                ali.die();
            }
        }

        for (Alient ali : list){
            bomb = ali.getBomb();
            if (!bomb.isDestroy()){
                g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), null);
            }
        }



        if (shot.isVisible){
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), null);
        }
    }

    public void gameStart(){
        if (death == NUM_FOR_WIN){
            ingame = false;
            notic = "Win win win";

        }
        if (shot.isVisible) {
            for (Alient ali : list) {
                int sx = shot.getX();
                int sy = shot.getY();
                if (ali.isVisible && shot.isVisible) {
                    if (sx >= ali.getX() && sx <= ali.getX() + ALIENT_WIDTH && sy >= ali.getY() && sy <= ali.getY() + ALIENT_HEIGHT) {
                        ImageIcon ic = new ImageIcon("DrawImage/explosion.png");
                        ali.setImage(ic.getImage());
                        ali.setDying(true);
                        death++;
                        shot.die();

                    }

                    if (sy <= 0) {
                        shot.die();
                    }
                }
            }
        }

        Random random = new Random();
        for (Alient ali : list){
            Bomb bomb = ali.getBomb();
            int a = random.nextInt(50);
            if (a == 5 && ali.isVisible && bomb.isDestroy()) {

                bomb.setX(ali.getX());
                bomb.setY(ali.getY());
                bomb.setDestroy(false);

            }
                int x = bomb.getX();
                int y = bomb.getY();
                int playerx = player.getX();
                int playery = player.getY();

            if (x >= playerx && x <= playerx + PLAYER_WIDTH &&
                        y >= playery && y <= playery + PLAYER_HEIGHT){
                    player.setDying(true);
                    ingame = false;
                    notic = "Game Over";
                    System.out.println(playerx+ ":" + (playerx + PLAYER_WIDTH));
                    System.out.println(playery+ ":" + (playery + PLAYER_HEIGHT));
                    System.out.println(x + ":" + y);
                    ImageIcon ic = new ImageIcon("DrawImage/explosion.png");
                    bomb.setImage(ic.getImage());
//                    bomb.setDestroy(true);
            }

            if (x == shot.getX() && y == shot.getY()){
                bomb.setDestroy(true);
                shot.die();
            }

            if (!bomb.isDestroy()){
                bomb.setY(bomb.getY()+1);
                if (bomb.getY() >= 305){
                    bomb.setDestroy(true);
                }
            }

        }
    }

    public void gameOver(){
        Graphics g = this.getGraphics();
        g.setColor(Color.RED);
        g.drawRect(80, 110, 180, 80);

        g.setColor(Color.GREEN);
        g.drawString(notic, BOARD_WIDTH/3,160);
    }



    @Override
    public void run() {
        while (ingame){
            gameStart();
            repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(notic);
        gameOver();
    }

    private class AdapteKey extends KeyAdapter {

        public void keyPressed(KeyEvent e){
            System.out.println(e.getKeyChar());
            player.pressed(e);


            if (e.getKeyChar() == KeyEvent.VK_SPACE){
                if (!shot.isVisible && player.isVisible){
                    shot = new Shot(player.getX() + Constans.PLAYER_WIDTH/2, player.getY());
                    thread4 = new Thread(shot);
                    thread4.start();
                }
            }
        }

        public void keyReleased(KeyEvent e){
            player.released(e);
        }
    }
}
