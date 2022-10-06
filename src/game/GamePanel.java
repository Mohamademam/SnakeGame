package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener {
    //set screen size
    static final int ScreenWidth = 600;
    static final int ScreenHeight = 600;
    static final int UnitSize = 25;
    static final int GameUnit = (ScreenWidth * ScreenHeight) / UnitSize;
    static final int Delay = 75;
    final int x[] = new int[GameUnit];
    final int y[] = new int[GameUnit];
    int bodyPart = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(ScreenWidth,ScreenHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(Delay,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running){
            g.setColor(Color.red);
            g.fillOval(appleX,appleY,UnitSize,UnitSize);
            for(int i=0; i< bodyPart;i++){
                if(i==0){
                    g.setColor(Color.green);
                    g.fillOval(x[i],y[i],UnitSize,UnitSize);
                }
                else{
                    g.setColor(new Color(2,120,41));
                    g.fillRect(x[i],y[i],UnitSize,UnitSize);
                }
                g.setColor(Color.magenta);
                g.setFont(new Font("Ink Free",Font.BOLD,40));

                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score: " + applesEaten ,(metrics.stringWidth("Score:" + applesEaten))/2,g.getFont().getSize());
            }
        }
        else{
            gameOver(g);
        }
    }
    public void move(){
        for(int i=bodyPart;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UnitSize;
                break;
            case 'D':
                y[0] = y[0] + UnitSize;
                break;
            case 'L':
                x[0] = x[0] - UnitSize;
                break;
            case 'R':
                x[0] = x[0] + UnitSize;
                break;
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(ScreenWidth/UnitSize))*UnitSize;
        appleY = random.nextInt((int)(ScreenHeight/UnitSize))*UnitSize;
    }
    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            bodyPart++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions(){
        //check if head collides with body
        for(int i=bodyPart;i>0;i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
            //check if head touches the left border, it will appear in the opposite border
            if (x[0] < 0) {
                running = true;
                x[0] = ScreenWidth;
            }
            //check if head touches the right border, it will appear in the opposite border
            if (x[0] > ScreenWidth) {
                running = true;
                x[0] = 0;
            }
            //check if head touches top border, it will appear in the opposite border
            if (y[0] < 0) {
                running = true;
                y[0] = ScreenHeight;
            }
            //check if head touches bottom border, it will appear in the opposite border
            if (y[0] > ScreenHeight) {
                running = true;
                y[0] = 0;
            }
            if (!running)
                timer.stop();
        }
    }
    JButton button1, button2;
    public void gameOver(Graphics g){
        //Game over Text
        g.setColor(Color.MAGENTA);
        g.setFont(new Font("Times New Roman",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(ScreenWidth - metrics.stringWidth("Game Over"))/2,(ScreenHeight/3));
        g.drawString("Score:" + applesEaten,(ScreenWidth - metrics.stringWidth("Score" + applesEaten))/2,ScreenHeight/2);

        button1 = new JButton("TRY AGAIN");
        button1.setBounds(200,350,200,55);
        button1.setFocusable(false);
        button1.addActionListener(this);
        button1.setBackground(Color.white);
        button1.setFont(new Font("Times New Roman",Font.BOLD,28));
        button1.setForeground(Color.BLACK);
        this.add(button1);

        button2 = new JButton("EXIT");
        button2.setBounds(200,420,200,52);
        button2.setFocusable(false);
        button2.addActionListener(this);
        button2.setBackground(Color.white);
        button2.setFont(new Font("Times New Roman",Font.BOLD,28));
        button2.setForeground(Color.BLACK);
        this.add(button2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
        if(e.getSource() == button1)
            new GameFrame();

        if(e.getSource() == button2)
            System.exit(0);
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R')
                        direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L')
                        direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D')
                        direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U')
                        direction = 'D';
                    break;
            }
        }
    }
}