import javax.swing.JFrame;
public class GameFrame extends JFrame
{
    GameFrame()
    {
        this.add(new GamePanel());
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        //this.setLocationRelativeTo(null);
        this.setTitle("Snake Game");
        this.pack();
    }   
}
