import java.awt.*;

// class description
// this is a part of the Neuron object. Each neuron, except the ones in the last layer,
// has an array list of lines that show the weights. The line is a really simple and easy to
// understand object. The lines with a shade color greater than 50 are drawn to reduce clutter
// in the display.

public class Line {
    public int x1, x2, y1, y2;
    public int ShadeColor;
    public Line(int X1, int Y1, int X2, int Y2, int shade){
        x1 = X1;
        x2 = X2;
        y1 = Y1;
        y2 = Y2;
        ShadeColor = shade;
    }
    public void DrawLine(Graphics g){
        if ((ShadeColor > 50 || ShadeColor < -50)){
            if (ShadeColor < 0){
                ShadeColor *= -1;
                g.setColor(new Color(255, 255- ShadeColor,255- ShadeColor)); // red
            }
            else{
                g.setColor(new Color(255- ShadeColor, 255- ShadeColor,255)); // dark blue
            }
            g.drawLine(x1,y1,x2,y2);
        }
    }
}
