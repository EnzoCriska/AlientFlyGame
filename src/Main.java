import javax.swing.*;

public class Main extends JFrame implements Constans {

    public Main(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(BOARD_HEIGHT, BOARD_WIDTH);
        setResizable(false);
        add(new Board());
    }
    public static void main(String[] args) {
        new Main();
    }
}
