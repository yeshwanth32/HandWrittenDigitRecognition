import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class NetworkSettings extends JPanel{
    int test1 = 0;
    public NetworkSettings(int Test1){
        test1 = Test1;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        g.drawString("Network Middle Layers", 10,15);
        g.drawString("Learning Rate", 10,55);
        g.drawString("Epoch Size", 10,95);
        g.drawString("Number of loops", 10,135);
        g.drawString("Number of Loops", 10,175);
        g.drawOval(100, 10, 10,10);

    }
}