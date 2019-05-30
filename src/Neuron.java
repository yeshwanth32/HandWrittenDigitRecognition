import java.awt.*;
import java.util.ArrayList;

public class Neuron {
    public int X, Y;
    public int Radius;
    public int ShadeColor;
    public double Activation;
    public ArrayList<Line> Weights;
    public Neuron(int x, int y, int radius,int shade, double activation,ArrayList<Line> weights) {
    	Activation = activation;
        X = x; Y = y; Radius = radius; ShadeColor = shade;
        Weights = weights;
    }
    public void Add(Line a){
        Weights.add(a);
    }
    public void DrawNeuron(Graphics g) {
    	//System.out.println(Activation*255);
        g.setColor(new Color(255- ShadeColor, 255- ShadeColor,255- ShadeColor)); // black
        g.fillOval(X, Y, Radius, Radius);
        for (int i = 0; i < Weights.size(); i++){
            Weights.get(i).DrawLine(g);
            //System.out.println(Weights.get(i).x1 + "," + Weights.get(i).y1 + " : "+Weights.get(i).x2 + "," + Weights.get(i).y2);
        }
        if (Radius > 15 && Activation > 0.099) {
        	Font font = new Font ("Courier New", 1, Radius/2); //Initializes the font
            g.setFont(font);
            g.setColor(Color.GREEN);
            int width = g.getFontMetrics().stringWidth(Double.toString(Round(Activation,1)));
            g.drawString(Double.toString(Round(Activation,1)), X + (Radius/2) - width/2, Y + (Radius/2));
        }
    }
    private double Round(double x, int places) {
    	double New = Math.round(x* Math.pow(10, places)) / Math.pow(10, places);
    	return New;
    }

}