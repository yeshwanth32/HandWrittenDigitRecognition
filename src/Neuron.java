import java.awt.*;
import java.util.ArrayList;

public class Neuron {
    public int X, Y;
    public int Radius;
    public int ShadeColor;
    public ArrayList<Line> Weights;
    public Neuron(int x, int y, int radius, int shade, ArrayList<Line> weights) {
        X = x; Y = y; Radius = radius; ShadeColor = shade; Weights = weights;
    }
    public void Add(Line a){
        Weights.add(a);
    }
    public void DrawParticle(Graphics g) {
        g.setColor(new Color(255- ShadeColor, 255- ShadeColor,255- ShadeColor)); // black
        g.fillOval(X, Y, Radius, Radius);
        for (int i = 0; i < Weights.size(); i++){
            Weights.get(i).DrawLine(g);
            System.out.println(Weights.get(i).x1 + "," + Weights.get(i).y1 + " : "+Weights.get(i).x2 + "," + Weights.get(i).y2);
        }
    }

}