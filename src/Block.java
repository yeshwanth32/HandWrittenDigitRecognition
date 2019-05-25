import java.awt.Color;
import java.awt.Graphics;


public class Block{
    public int X, Y;
    public int ShadeColor;
    public int Width = 15;
    public int colorType;
    public Block(){
        X = 10; Y = 10;
        ShadeColor = 0;
        colorType = 7;
    }
    public void DrawBlock(Graphics g)  {
        switch(colorType) {
        case 1:
        	g.setColor(new Color(255- ShadeColor, 255,255)); // cyan
        	break;
        case 2:
        	g.setColor(new Color(255, 255- ShadeColor,255)); // magenta
        	break;
        case 3:
        	g.setColor(new Color(255, 255,255- ShadeColor)); // yellow
        	break;
        case 4:
        	g.setColor(new Color(255- ShadeColor, 255- ShadeColor,255)); // dark blue
        	break;
        case 5:
        	g.setColor(new Color(255, 255- ShadeColor,255- ShadeColor)); // red
        	break;
        case 6:
        	g.setColor(new Color(255- ShadeColor, 255,255- ShadeColor)); // green
        	break;
        case 7:
        	g.setColor(new Color(255- ShadeColor, 255- ShadeColor,255- ShadeColor)); // black
        	break;
        }
        g.fillRect(X, Y, Width, Width);
        // Test for Intellij
    }

}