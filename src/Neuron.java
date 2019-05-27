import java.awt.*;

public class Neuron {
    private int X, Y;
    public int Radius;
    public int ShadeColor;
    public Neuron(int x, int y, int radius, int shade ) {
        X = x; Y = y; Radius = radius; ShadeColor = shade;
    }
    public void DrawParticle(Graphics g) {
        g.setColor(new Color(255- ShadeColor, 255- ShadeColor,255- ShadeColor)); // black
        g.fillOval(X, Y, Radius, Radius);
    }

}