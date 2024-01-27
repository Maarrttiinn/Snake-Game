import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener
{
    final static int WIDTH = 800;
    final static int HEIGHT = 600;
    final static int UNIT = 25;
    final static int GAME_UNIT = (WIDTH*HEIGHT)/UNIT;
    int[] x =new int[GAME_UNIT];
    int[] y = new int[GAME_UNIT];
    int body = 5;
    int score=0;
    int appleX;
    int appleY;
    boolean moving = false;
    char direction = 'R';
    int delay = 100;
    Timer timer;
    Random random;
   
    GamePanel()
    {
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setFocusable(true); //can detect keyboard event/input
        this.addKeyListener(new myKeyAdapter());
        this.startGame(); //can use this. but not required

    }

    public void startGame()
    {
        timer = new Timer(delay,this); //automatically triggers event at specified intervals | can use this as this class implements ActionListener
        this.addApple();
        moving = true;
        timer.start();
    }
    public void endGame(Graphics g)
    {
        g.setColor(Color.RED);

        g.setFont(new Font("Times New Roman",Font.BOLD,30));
        FontMetrics metrics1 = g.getFontMetrics(g.getFont());// must use FontMetrics with g.getFontMetrics()
        g.drawString("Score: "+score,(WIDTH-metrics1.stringWidth("Score: "+score))/2,g.getFont().getSize());

        g.setFont(new Font("Times New Roman",Font.BOLD,80));
        FontMetrics metrics2 = g.getFontMetrics(g.getFont());
        g.drawString("Game Over",(WIDTH-metrics2.stringWidth("Game Over"))/2,HEIGHT/2);
    }
    public void addApple()
    {
        random = new Random();
        appleX = random.nextInt((int)WIDTH/UNIT)*UNIT;
        appleY = random.nextInt((int)HEIGHT/UNIT)*UNIT;

    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        if(moving)
        {
            g.setColor(Color.RED);
            g.fillOval(appleX,appleY,UNIT,UNIT);

            g.setColor(Color.GREEN);
            for(int i=1;i<body;i++)
            {
                g.fillRect(x[i],y[i],UNIT,UNIT);
            }
            g.setColor(new Color(45,180,1));
            g.fillRect(x[0],y[0],UNIT,UNIT);

            g.setColor(Color.red);
            g.setFont(new Font("Times New Roman",Font.BOLD,30));
            FontMetrics metrics = g.getFontMetrics(g.getFont()); // must use FontMetrics with g.getFontMetrics()
            g.drawString("Score: "+score,(WIDTH-metrics.stringWidth("Score: "+score))/2,g.getFont().getSize()); // both give pixels as return value
        }
        else
        {
            endGame(g);
        }
    }
    public void move()
    {
        for(int i =body-1;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction)
        {
            case 'R':
            x[0]=x[0]+UNIT;
            break;
            case 'L':
            x[0]=x[0]-UNIT;
            break;
            case 'U':
            y[0]=y[0]-UNIT;
            break;
            case 'D':
            y[0]=y[0]+UNIT;
            break;

        }
    }
    public void checkApple()
    {
        if(x[0]==appleX&&y[0]==appleY)
        {
            score++;
            body++;
            x[body-1]=x[body-2];
            y[body-1]=y[body-2];
            addApple();
        }
    }
    public void checkCollision()
    {
        for(int i=1;i<body;i++)
        {
            if(x[0]==x[i]&&y[0]==y[i])
            {
                moving = false;
            }
        }
        if(x[0]<0||x[0]>WIDTH)
        {
            moving =false;
        }
        if(y[0]<0||y[0]>HEIGHT)
        {
            moving=false;
        }
        if(!moving)
        {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(moving)
        {
            move();
            checkApple();
            checkCollision();
        }
        repaint(); //inherently calls paintComponent()

    }
    public class myKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch(e.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                if(direction!='R')
                {
                    direction = 'L';
                }
                break;
                case KeyEvent.VK_RIGHT:
                if(direction!='L')
                {
                    direction='R';
                }
                break;
                case KeyEvent.VK_UP:
                if(direction!='D')
                {
                    direction ='U';
                }
                break;
                case KeyEvent.VK_DOWN:
                if(direction!='U')
                {
                    direction = 'D';
                }
                break;
            }
        }
    }
}
