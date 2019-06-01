import java.awt.*;
import java.util.ArrayList;

// class description
// This is a part of the DisplayNetwork object. Each neron is a circle and represents a neuron
// in the network. It has an ArrayList of weights, a radius, x and y coordinates, activation value
// and a shade color.

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
        // drawing the neuron
        g.setColor(new Color(255- ShadeColor, 255- ShadeColor,255- ShadeColor)); // black
        g.fillOval(X, Y, Radius, Radius);
        // drawing the weights
        for (int i = 0; i < Weights.size(); i++){
            Weights.get(i).DrawLine(g);
        }
        // drawing the activation value inside some of the neurons.
        if (Radius > 15 && Activation > 0.099) {
        	Font font = new Font ("Courier New", 1, Radius/2); //Initializes the font
            g.setFont(font);
            g.setColor(Color.GREEN);
            int width = g.getFontMetrics().stringWidth(Double.toString(Round(Activation,1)));
            g.drawString(Double.toString(Round(Activation,1)), X + (Radius/2) - width/2, Y + (Radius/2));
        }
    }
    // this is used to round the activation values.
    private double Round(double x, int places) {
    	double New = Math.round(x* Math.pow(10, places)) / Math.pow(10, places);
    	return New;
    }

}