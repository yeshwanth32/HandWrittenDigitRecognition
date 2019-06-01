import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
@SuppressWarnings("unused")

// class description
// This is the actual graphical part of the NetworkDiagram. it takes in an object that implements
// NeuralNetDisplay interface and draws a diagram of it. This doesn't display all the weights and
// biases to make sure the diagram is not cluttered. A more detailed working of this class is
// provided in the project write up.
public class DisplayNetwork extends JPanel{
	public Neuron[][] neurons;
	public DisplayNetwork(NeuralNetDisplay Network, int Width, int Height) {
		 neurons = new Neuron[Network.NeuronValues().length][];
		 for (int i = 0; i < neurons.length; i++){
		 	neurons[i] = new Neuron[Network.NeuronValues()[i].length];
		 }
		 int x = 0;
		 int y = 0;
		 int gap = 5;
		 // Creating and formatting the radius and coordinates of the neurons to make them fit on the screen
		 int VerticalSegmentWidth = (Width-gap*neurons.length)/neurons.length;
		 for (int i = 0; i < neurons.length; i++){
		 	int HorizontalSegmentsHeight = Height/neurons[i].length;
		 	if (HorizontalSegmentsHeight <1){HorizontalSegmentsHeight = 1;}
		 	for (int j = 0; j < neurons[i].length; j++){
		 		int radius = Width/(neurons.length+neurons[i].length);
		 		if (radius < 5){ radius = 5; gap = 0;}
		 		neurons[i][j] = new Neuron(x,y,radius,(int) (Network.NeuronValues()[i][j]*255),Network.NeuronValues()[i][j],new ArrayList<Line>());
				y += HorizontalSegmentsHeight + gap;
			}
		 	y = 0;
		 	x += VerticalSegmentWidth;
		 }
		 // creating the weights and adding them to all the neurons except the ones in the last layer and
         // the ones that don't have a activation greater than 0.9 .
		 double[][][] Weights = Network.WeightValues();
		 for (int i = 0; i < neurons.length-1; i++){
		 	for (int j = 0; j < neurons[i].length; j++){
		 		for (int k = 0; k < neurons[i+1].length; k++){
					if (neurons[i+1][k].Activation > 0.9 && neurons[i][j].Activation > 0.9 ) {
						Neuron Z = neurons[i][j];
						int Weight = (int) ((Weights[i][j][k] * 255));
						if (Weight > 255){ Weight = 255;}
						if (Weight < -255){ Weight = -255;}
						neurons[i][j].Add(new Line(Z.X + Z.Radius + 1, Z.Y, neurons[i + 1][k].X - 1,
								neurons[i + 1][k].Y,
								Weight)); //(int)(Math.random()*255) //Network.WeightValues()[i-1][j][k]c]
					}
				}
			}
		 }
	}
    public void paintComponent(Graphics g) {
	    // drawing the diagram
    	super.paintComponent(g);
		for (int i = 0; i < neurons.length; i++){
			for (int j = 0; j < neurons[i].length; j++){
				neurons[i][j].DrawNeuron(g);
			}
			
		}
    }
}
