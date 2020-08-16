import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 400;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private Image head;
    private Image headleft;
    private Image headright;
    private Image headdown;
    private Image headup;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;


    public GameField(){
        setBackground(Color.black);
        //loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++){
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250,this);
        timer.start();
        createApple();
    }

    public void createApple(){
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void loadImages(String str){
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
        if(str == "left"){
            ImageIcon h = new ImageIcon("headleft.png");
            headleft = h.getImage();
            head = headleft;
        }
        if(str == "right"){
            ImageIcon h = new ImageIcon("headright.png");
            headright = h.getImage();
            head = headright;
        }
        if(str == "down"){
            ImageIcon h = new ImageIcon("headdown.png");
            headdown = h.getImage();
            head = headdown;
        }
        if(str == "up"){
            ImageIcon h = new ImageIcon("headup.png");
            headup = h.getImage();
            head = headup;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(head,x[0],y[0],this);
            g.drawImage(apple,appleX,appleY,this);
            for(int i = 1; i < dots; i++){
                g.drawImage(dot,x[i],y[i],this);
            }
        }
        else{
            String str = "Game Over";
            //Font f = new Font("Arial",14,Font.BOLD);
            g.setColor(Color.white);
            //g.setFont(f);
            g.drawString(str,210,SIZE/2);
        }
    }

    public void move(){
        for(int i = dots; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        }
        if(up){
            y[0] -= DOT_SIZE;
        }
        if(down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }

    public void checkCollisions(){
        for (int i = dots; i > 0; i--){
            if (i>4 && x[0]== x[i] && y[0] == y[i]){
                inGame = false;
            }
        }
        if(x[0] < 0){
            x[0] = SIZE - DOT_SIZE;
        }
        else if(x[0] >= SIZE){
            x[0] = 0;
        }
        if(y[0] < 0){
            y[0] = SIZE - DOT_SIZE;
        }
        else if(y[0] >= SIZE){
            y[0] = 0;
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(inGame){
            checkApple();
            checkCollisions();
            move();

        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && ! right){
                left = true;
                up = false;
                down = false;
                String str = "left";
                loadImages(str);
            }
            if(key == KeyEvent.VK_RIGHT && ! left){
                right = true;
                up = false;
                down = false;
                String str = "right";
                loadImages(str);
            }
            if(key == KeyEvent.VK_UP && ! down){
                right = false;
                up = true;
                left = false;
                String str = "up";
                loadImages(str);
            }
            if(key == KeyEvent.VK_DOWN && ! up){
                right = false;
                down = true;
                left = false;
                String str = "down";
                loadImages(str);
            }
        }
    }
}