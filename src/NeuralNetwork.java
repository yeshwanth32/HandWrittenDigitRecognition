import java.io.*;
import java.util.Arrays;

@SuppressWarnings("unused")


public class NeuralNetwork implements NeuralNetDisplay{

    public double[] ValueI, ValueO;
    public double[] ErrorO;
    public double[][][] Weights;
    public double[][] BiasesM;
    public double[][] ValueM;
    public double[][] ErrorM;
    public int[] MiddleSizes;
    public int Output;
    //public double[] BiasesO;
    public NeuralNetwork(int[] middleSizes){
        ValueI = new double[28*28];
        MiddleSizes = middleSizes;
        ValueM = new double[MiddleSizes.length][];
        ValueO = new double[10];
        //BiasesO = new double[10];
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
                Weights[i] = new double[MiddleSizes[i-1]][MiddleSizes[i]]; // possible source of error
            }
            else{
                Weights[i] = new double[MiddleSizes[i-1]][ValueO.length]; // possible source of error
            }

        }
        RandomizeWeights();
    }
    public void DisplayMiddleSizes() { System.out.println(Arrays.toString(MiddleSizes));}
    public double[] getValueO(){return ValueO;}
    public void ErrorValues(double[] Expected) throws InterruptedException{
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
    public void ChangeWeights(double learningRate) throws InterruptedException{
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
        /*for (int i = 0; i < BiasesO.length; i++){
            BiasesO[i] += -learningRate*ErrorO[i];
        }*/
    }
    public boolean Train(double[] Input, int ExpectedDigit, double LearningRate) throws InterruptedException{
        Output(Input);
        ErrorValues(ConvertDigitToOutputVector(ExpectedDigit));
        ChangeWeights(LearningRate);
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
    private double Sigmoid(double X){ return 1.0/(1+Math.exp(-X)); }
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
        /*for (int i = 0; i < BiasesO.length; i++){
            BiasesO[i] = Rand(-0.5,0.7);
        }*/
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
            //ValueO[i] = Sigmoid(Value + BiasesO[i]);
            ValueO[i] = Sigmoid(Value);
        }
        Output = GetFinalDigitOutput();
    }
    public void Output(double[] Valuei, ColorPanel p){
        Output(Valuei);
        p.Predicted = GetFinalDigitOutput();
        //System.out.println(p.Predicted);
    }
    public double[] ConvertDigitToOutputVector(int n){
        double[] CorrectOutput = new double[10];
        for (int i = 0; i < CorrectOutput.length; i++){
            CorrectOutput[i] = 0.0;
            if (i == n){ CorrectOutput[i] = 1.0;}
        }
        return  CorrectOutput;
    }
    public int GetFinalDigitOutput(){
        int MostConfidentIndex = 0;
        for (int i = 0; i < ValueO.length; i++){
            if (ValueO[i] > ValueO[MostConfidentIndex]){ MostConfidentIndex = i;}
        }
        return MostConfidentIndex;
    }
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
    public boolean ReadNetwork() throws FileNotFoundException, IOException {
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
            //BiasesO = StringToArray(br.readLine());
	        System.out.println("Done");
	        return true;
    	}catch (Exception e) {
    		System.out.println(e.toString());
    		return false;
    	}    	
    }
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

        //System.out.println(Arrays.toString(arr));
        return arr;
    }
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
        //fw.write(Arrays.toString(BiasesO));
        fw.close();
        ReadNetwork();
    }

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