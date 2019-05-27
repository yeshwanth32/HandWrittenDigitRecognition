import java.awt.Graphics;
import javax.swing.JPanel;
@SuppressWarnings("unused")
public class DisplayNetwork extends JPanel{
	public Neuron[][] neurons;
	public DisplayNetwork(NeuralNetDisplay Network) {
		 neurons = new Neuron[Network.NeuronValues().length][];
		 for (int i = 0; i < neurons.length; i++){
		 	neurons[i] = new Neuron[Network.NeuronValues()[i].length];
		 }
		 int x = 100;
		 int y = 40;
		 for (int i = 0; i < neurons.length; i++){
		 	for (int j = 0; j < neurons[i].length; j++){
				neurons[i][j] = new Neuron(x,y,10,(int)(Math.random()*256));
				y += 25;
			}
		 	y = 40;
		 	x += 25;
		 }
	}
    public void paintComponent(Graphics g) {
		for (int i = 0; i < neurons.length; i++){
			for (int j = 0; j < neurons[i].length; j++){
				neurons[i][j].DrawParticle(g);
			}
		}
		System.out.println("Drawing Complete " + neurons.length);
    }
}
