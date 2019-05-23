import java.awt.Color;
import java.awt.Graphics;


public class Block{
    public int X, Y;
    public int ShadeColor;
    public int Width = 15;
    public Block(){
        X = 10; Y = 10;
        ShadeColor = 0;
    }
    public void DrawBlock(Graphics g)  {
        g.setColor(Color.BLACK);
        double Percentage = (255- ShadeColor)/255.0;
        //g.setColor(new Color(255- ShadeColor, 255,255)); // cyan
        //g.setColor(new Color(255, 255- ShadeColor,255)); // magenta
        //g.setColor(new Color(255, 255,255- ShadeColor)); // yellow
        //g.setColor(new Color(255- ShadeColor, 255- ShadeColor,255)); // dark blue
        //g.setColor(new Color(255, 255- ShadeColor,255- ShadeColor)); // red
        g.setColor(new Color(255- ShadeColor, 255,255- ShadeColor)); // green
        //g.setColor(new Color(255- ShadeColor, 255- ShadeColor,255- ShadeColor)); // black
        // use a color pallete, make the user select the color example (12,235,126). 
        //This would be the base case, the color of the block can't go below ths combination or RGB
        g.fillRect(X, Y, Width, Width);
        
    }

}