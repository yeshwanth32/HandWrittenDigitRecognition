import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class NetworkSettings extends JPanel{
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        g.drawString("Network Middle Layers", 10,15);
        g.drawString("Learning Rate", 10,55);
        g.drawString("Epoch Size", 10,95);
        g.drawString("Number of loops", 10,135);
        g.drawString("Save to Test/Traing", 10, 175);
        
    }
}