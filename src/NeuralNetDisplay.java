// This the interface that the NeuralNetworkObject implements it just has 2 methods to convert
// the weights and neuron activation values to easily readable arrays. This display doesn't show
// the bias values even though those are important parts of the network. This is because there is
// no easy way to show the biases without making the diagram highly cluttered.

public interface NeuralNetDisplay {
     double[][] NeuronValues();
     double[][][] WeightValues();
}
