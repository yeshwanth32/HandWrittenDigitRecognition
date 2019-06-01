import java.io.*;
import java.util.Arrays;

@SuppressWarnings("unused")


// This is the main neural network algorithm, it implements a NeuralNetDisplay interface,
// which allows it to be shown as a diagram. This neural network object has a fixed number
// of neurons in the first and the last layer, 784 and 10 respectively. The middle layer is
// dynamic and the user can create however many layers he wants and can also chose how many
// neurons are there in each of those layers. This network is exclusively designed to work with
// the MNIST data set because of the fixed number of input neurons and the fixed number of
// output neurons. To understand how a neural network works I recommend watching the following
// video series : https://www.youtube.com/playlist?list=PLZHQObOWTQDNU6R1_67000Dx_ZCJB-3pi

public class NeuralNetwork implements NeuralNetDisplay{

    public double[] ValueI, ValueO;
    public double[] ErrorO;
    public double[][][] Weights;
    public double[][] BiasesM;
    public double[][] ValueM;
    public double[][] ErrorM;
    public int[] MiddleSizes;
    public int Output;
    public NeuralNetwork(int[] middleSizes){
        // initialize the arrays to the right sizes
        ValueI = new double[28*28];
        MiddleSizes = middleSizes;
        ValueM = new double[MiddleSizes.length][];
        ValueO = new double[10];
        for (int i = 0; i < ValueM.length; i++){
            ValueM[i] = new double[MiddleSizes[i]];
        }
        BiasesM = new double[MiddleSizes.length][];
        for (int i = 0; i < ValueM.length; i++){
            BiasesM[i] = new double[MiddleSizes[i]];
        }
        ErrorO = new double[ValueO.length];
        ErrorM = new double[MiddleSizes.length][];
        for (int i = 0; i < ValueM.length; i++){
            ErrorM[i] = new double[MiddleSizes[i]];
        }
        Weights = new double[MiddleSizes.length+1][][];
        Weights[0] = new double[ValueI.length][MiddleSizes[0]];
        for (int i = 1; i < Weights.length; i++){
            if (i != Weights.length-1){
                Weights[i] = new double[MiddleSizes[i-1]][MiddleSizes[i]];
            }
            else{
                Weights[i] = new double[MiddleSizes[i-1]][ValueO.length];
            }

        }
        RandomizeWeights();
    }
    public void DisplayMiddleSizes() { System.out.println(Arrays.toString(MiddleSizes));}
    public double[] getValueO(){return ValueO;}
    // error values are calculated for each neuron. it shows how far the neurons activation is
    // from the expected activation. Refer to the video for more detailed explanation.
    public void ErrorValues(double[] Expected){
        for (int i = 0; i < ValueO.length; i++){
            ErrorO[i] = (ValueO[i] - Expected[i])*(1- ValueO[i])*ValueO[i];
        }
        for (int i = 0; i < ValueM[ValueM.length-1].length; i++){
            double sum = 0;
            for (int j = 0; j < ValueO.length; j++){
                sum += Weights[Weights.length-1][i][j]*ErrorO[j];
            }
            ErrorM[ValueM.length-1][i] = sum* ValueM[ValueM.length-1][i]* (1-ValueM[ValueM.length-1][i]);
        }
        for (int k =ValueM.length-2; k >=0 ; k--){
            for (int i = 0; i < ValueM[k].length; i++){
                double sum = 0;
                double weightPrint = 0 , valueMPrint = 0;
                for (int j = 0; j < ValueM[k+1].length; j++){
                    sum += Weights[k+1][i][j]*ErrorM[k+1][j];

                }
                ErrorM[k][i] = sum*ValueM[k][i]*(1-ValueM[k][i]);

            }
        }
    }
    // updating the weights and biases to improve the accuracy of the network, main component of training.
    public void ChangeWeightsAndBiases(double learningRate){
        for (int k =0; k < Weights.length; k++){
            for (int i = 0; i < Weights[k].length; i++){
                for (int j = 0; j < Weights[k][i].length; j++){
                    double deltaW = 0;
                    if (k == 0){
                        deltaW = -learningRate*ValueI[i]*ErrorM[k][j];
                    }
                    else if (k != Weights.length-1) {
                        deltaW = -learningRate*ValueM[k-1][i]*ErrorM[k][j]; // double check later

                    }
                    else{
                        deltaW = -learningRate*ValueM[k-1][i]*ErrorO[j];
                    }
                    Weights[k][i][j] += deltaW;
                }
                if (k != 0) {
                    double deltaB = -learningRate * ErrorM[k-1][i];
                    BiasesM[k-1][i] += deltaB;
                }
            }
        }
    }
    // the main train function, returns true if the output is guessed correctly.
    public boolean Train(double[] Input, int ExpectedDigit, double LearningRate){
        Output(Input);
        ErrorValues(ConvertDigitToOutputVector(ExpectedDigit));
        ChangeWeightsAndBiases(LearningRate);
        if (Output == ExpectedDigit){
            return true;
        }
        return false;
    }
    public double[] getValueI() {
        return ValueI;
    }
    public void setValueI(double[] valueI) {
        ValueI = valueI;
    }
    // this is the activation function used in the network, there are many different activation
    // functions used in neural networks, but according to my research Sigmoid is the best one
    // for my requirements. Watch the video to learn more about different activation functions.
    private double Sigmoid(double X){ return 1.0/(1+Math.exp(-X)); }
    // randomly initialize weights before training. I used a limit between -1 and 1.0 for the weights
    // to make it easy to train. There are many smart ways to initialize a neural network to make it easy
    // to train, I found this to be the most effective.
    private void RandomizeWeights(){
        for (int k = 0; k < Weights.length; k++){
            for (int j = 0; j < Weights[k].length; j++){
                for (int i = 0; i < Weights[k][j].length; i++){
                    if (k > 0)
                        Weights[k][j][i] =  Rand(-1.0,1.0) * Math.sqrt(2.0/(Weights[k-1].length + Weights[k].length));
                    else
                        Weights[k][j][i] =  Rand(-1.0,1.0);
                }
            }
        }
        for (int i = 0; i < ValueM.length; i++){
            for (int j = 0; j < ValueM[i].length; j++){
                ValueM[i][j] = Math.random();
                BiasesM[i][j] =  Rand(-0.5,0.7);
            }
        }
    }
    public void DisplayMiddleErrors(){
        System.out.print(ConsoleColors.GREEN_BOLD);
        for (int i = 0; i < ErrorM.length; i++){
            for (int j = 0; j < ErrorM[i].length; j++){
                double Value = Math.floor(ErrorM[i][j]*10)/10;
                OutputValueTest(Value);
            }
            System.out.println();
        }
        System.out.print(ConsoleColors.RESET);
    }
    // used for console output in other methods
    private void OutputValueTest(double Value){
        if (Value < 1 && Value > -1){
            System.out.print( Value+ " ,");
        }
        else{
            System.out.print(ConsoleColors.CYAN_BOLD +  Value+ " ," + ConsoleColors.RESET);
        }
    }
    public void DisplayMiddleValues(){
        for (int i = 0; i < ValueM.length; i++){
            for (int j = 0; j < ValueM[i].length; j++){
                double Value = Math.floor(ValueM[i][j]*10)/10;
                OutputValueTest(Value);
            }
            System.out.println();
        }
    }
    public void DisplayMiddleBiases(){
        for (int i = 0; i < BiasesM.length; i++){
            for (int j = 0; j < BiasesM[i].length; j++){
                double Value = Math.floor(BiasesM[i][j]*10)/10;
                OutputValueTest(Value);
            }
            System.out.println();
        }
    }
    public void DisplayWeights(){
        for (double[][] k: Weights){
            for (double[] j: k){
                for (double i: j){
                    double Value = Math.floor(i*10)/10;
                    OutputValueTest(Value);
                }
                System.out.println();
            }
        }
    }
    // calculates the output of the neural network from the given input. This is the function used
    // to get the value predicted by the network. This method could also be called forward propagation
    public void Output(double[] Valuei){
        setValueI(Valuei);
        for (int i = 0; i < ValueM[0].length; i++){
            double Value = 0;
            for (int j = 0; j < ValueI.length; j++){
                Value += Weights[0][j][i]*ValueI[j];
            }
            ValueM[0][i] = Sigmoid(Value+BiasesM[0][i]);
        }
        for (int k = 1; k < ValueM.length; k++){
            for (int i = 0; i < ValueM[k].length; i++){
                double Value = 0;
                for (int j = 0; j < ValueM[k-1].length; j++){
                    Value += Weights[k][j][i]*ValueM[k-1][j];
                }
                ValueM[k][i] = Sigmoid(Value+BiasesM[k][i]);
            }
        }
        for (int i = 0; i < ValueO.length; i++){
            double Value = 0;
            for (int j = 0; j < ValueM[ValueM.length-1].length; j++){
                Value += Weights[Weights.length-1][j][i]*ValueM[ValueM.length-1][j];
            }
            ValueO[i] = Sigmoid(Value);
        }
        Output = GetFinalDigitOutput();
    }
    // this is used to display the guessed number of the screen
    public void Output(double[] Valuei, ColorPanel p){
        Output(Valuei);
        p.Predicted = GetFinalDigitOutput();
    }
    // used to convert the expected digit to a an array of doubles
    // for example, if the excepted digit was 2 the array would look like this:
    // {0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}
    // this is used for training.
    public double[] ConvertDigitToOutputVector(int n){
        double[] CorrectOutput = new double[10];
        for (int i = 0; i < CorrectOutput.length; i++){
            CorrectOutput[i] = 0.0;
            if (i == n){ CorrectOutput[i] = 1.0;}
        }
        return  CorrectOutput;
    }
    // looks at the last layer and gets the most position of the neuron with the highest activation,
    // this method essentially gets what digit the neural network guessed.
    public int GetFinalDigitOutput(){
        int MostConfidentIndex = 0;
        for (int i = 0; i < ValueO.length; i++){
            if (ValueO[i] > ValueO[MostConfidentIndex]){ MostConfidentIndex = i;}
        }
        return MostConfidentIndex;
    }
    // used to display the input on the console
    public void DisplayInput(){
        int RoundToValue = 10000;
        for (int i = 0; i < ValueI.length; i++){
            if (i%28 == 0){
                System.out.println();
            }
            if (((Math.floor(ValueI[i]*RoundToValue))/RoundToValue) != 0.0)
                System.out.print(" " + ConsoleColors.BLUE +((Math.floor(ValueI[i]*RoundToValue))/RoundToValue) + ConsoleColors.RESET);
            else
                System.out.print(" " + ((Math.floor(ValueI[i]*RoundToValue))/RoundToValue) );
        }
        System.out.println();
    }
    private double Rand(double i, double j) {
        return ((j - i) * Math.random()) + i ;
    }
    // reads the saved network file to load the weights and biases already saved.
    public boolean ReadNetwork(){
    	try {
    		System.out.println(Driver.location +"\\Network.txt");
	        FileReader fr = new FileReader(Driver.location + "\\Network.txt");
	        BufferedReader br = new BufferedReader(fr);
	        for (int i = 0; i < ValueM.length; i++){
	            int Size = Integer.parseInt(br.readLine());
	            if (ValueM[i].length != Size){return false;}
	        }
	        ValueI = StringToArray(br.readLine());
	        for (int i = 0; i < ValueM.length; i++){
	            ValueM[i] = StringToArray(br.readLine());
	        }
	        ValueO = StringToArray(br.readLine());
	        for (int i = 0; i < BiasesM.length; i++){
	            BiasesM[i] = StringToArray(br.readLine());
	        }
	        for (int i = 0; i < Weights.length; i++){
	            for (int j = 0; j < Weights[i].length; j++){
	                Weights[i][j] = StringToArray(br.readLine());
	            }
	        }
	        System.out.println("Reading file Done");
	        return true;
    	}catch (Exception e) {
    		System.out.println(e.toString());
    		return false;
    	}    	
    }
    // method used in saving the network
    public double[] StringToArray(String str){
        int counter = 0;
        for (int i = 0; i< str.length(); i++){
            if (str.substring(i,i+1).equals(",")){
                counter++;
            }
        }
        double[] arr = new double[counter+1];
        for (int i = 0; i < arr.length; i++){
            if (str.indexOf(',') != -1)
                arr[i] = Double.parseDouble(str.substring(1, str.indexOf(',')));
            else
                arr[i] = Double.parseDouble(str.substring(1, str.indexOf(']')));
            str = str.substring(str.indexOf(',')+1);
        }
        return arr;
    }
    // saves the network's weights and biases for future use.
    public void SaveNetwork() throws IOException {
        FileWriter fw=new FileWriter(Driver.location +"\\Network.txt");
        for (int i = 0; i < ValueM.length; i++){
            fw.write(ValueM[i].length + "\n");
        }
        fw.write(Arrays.toString(ValueI));
        fw.write("\n");
        for (int i = 0; i < ValueM.length; i++){
            fw.write(Arrays.toString(ValueM[i]));
            fw.write("\n");
        }
        fw.write(Arrays.toString(ValueO));
        fw.write("\n");
        for (int i = 0; i < BiasesM.length; i++){
            fw.write(Arrays.toString(BiasesM[i]));
            fw.write("\n");
        }
        for (int i = 0; i < Weights.length; i++){
            for (int j = 0; j < Weights[i].length; j++){
                fw.write(Arrays.toString(Weights[i][j]));
                fw.write("\n");
            }
        }
        fw.close();
        ReadNetwork();
    }
    // these are the methods for the interface.
    @Override
    public double[][] NeuronValues() {
        double[][] neuronValues = new double[ValueM.length+2][];
        neuronValues[0] = new double[ValueI.length];
        for (int i = 0; i < neuronValues[0].length; i++){
            neuronValues[0][i] = ValueI[i];
        }
        neuronValues[neuronValues.length-1] = new double[ValueO.length];
        for (int i = 0; i < neuronValues[neuronValues.length-1].length; i++){
            neuronValues[neuronValues.length-1][i] = ValueO[i];
        }
        int m = 0;
        for (int j = 1; j < neuronValues.length-1; j++){
            neuronValues[j] = new double[ValueM[m].length];
            for (int i = 0; i < neuronValues[j].length; i++){
                neuronValues[j][i] = ValueM[m][i];
            }
            m++;
        }
        return neuronValues;
    }
    @Override
    public double[][][] WeightValues() {
        return Weights;
    }
}