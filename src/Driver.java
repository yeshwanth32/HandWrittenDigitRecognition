import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.nio.file.Paths;
import java.util.List;
import java.nio.file.Files;
import java.util.Random;


public class Driver {
    private static int Action;
    private static boolean ExitTrain;
    private static List<String> TrainLines;
    private static List<String> TestLines;
    private static int[] NetworkMiddle;
    private static int EpochSize, Loops, OuterLoopSize;
    private static double LearningRate;
    private static boolean SaveToTrain;
    //public static String location = "C:\\Users\\bommareddyy\\Desktop\\HandwrittenDigitTrainingFiles";
    public static String location = "C:\\Users\\yeshw\\Desktop\\HandwrittenDigitTrainingFiles";
    public static void Initializations(ColorPanel P, JFrame myFrame){
        //ExitTrain = false;
        EpochSize = 150;
        Loops = 45;
        LearningRate = 0.3;
        Action = 2;
        NetworkMiddle = new int[]{70,35};
        SaveToTrain = true;
        OuterLoopSize = 600;
        myFrame.setTitle("GraphicsLab");
        myFrame.setSize(600,500);
        myFrame.setResizable(false);
        /*myFrame.setUndecorated(true);
        myFrame.setBackground(new Color(0,0,0,0)); This makes the frame transparent */
        JButton b1 = new JButton("Submit");
        JButton b2 = new JButton("Train");
        JButton b3 = new JButton("Clear");
        JButton b4 = new JButton("Exit");
        JButton b5 = new JButton("Save Network");
        JButton b6 = new JButton ("#");
        JButton b7 = new JButton ("$");
        JButton b8 = new JButton ("Test");
        JTextField t = new JTextField(16);
        b1.setBounds(450, 230, 120, 30);
        b2.setFont(new Font("Aurora Cn BT", Font.PLAIN, 10));
        b2.setBounds(450, 270, 60, 30);
        b4.setFont(new Font("Aurora Cn BT", Font.PLAIN, 10));
        b4.setBounds(510, 270, 60, 30);
        b3.setBounds(450, 350, 120, 30);
        b5.setBounds(450, 390, 120, 30);
        b8.setBounds(450, 310, 120, 30);
        t.setBounds(450, 150, 120, 30);
        b6.setBounds(450,190,60,30);
        b7.setBounds(510,190,60,30);
        AddListenersToButtons(b1,b2,b3,b4,b5,b6,b7,b8,t,P);
        //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        myFrame.add(t);
        myFrame.add(b1);
        myFrame.add(b2);
        myFrame.add(b3);
        myFrame.add(b4);
        myFrame.add(b5);
        myFrame.add(b6);
        myFrame.add(b7);
        myFrame.add(b8);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.getContentPane().add(P);
        myFrame.setVisible(true);
        myFrame.repaint();
    }
    private static void AddListenersToButtons(
            JButton b1, JButton b2,JButton b3,JButton b4,JButton b5,JButton b6, JButton b7,JButton b8,
            JTextField t, ColorPanel P){

        b3.setActionCommand("clear");
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Action = 4;
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                P.InitializeDrawBox();
            }
        });
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ExitTrain = !ExitTrain;
                /*try {
                    System.out.println("Test1");
                    P.ArrayToDrawBox( GetCSVtoArray(((int)(Math.random()*1000)), true));
                    P.DisplayBoxes();
                    P.repaint();
                }catch (Exception e){

                }*/
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("TestButton2");
                if (Action == 1) { Action = 2;}
                if (Action == 2) { Action = 1; P.InitializeDrawBox();}
            }
        });
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Action = 3;
            }
        });
        b6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Action = 6;
            }
        });
        b7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Action = 7;
            }
        });
        b8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Action = 8;
            }
        });
        t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                WriteInTrainData(P.DrawBoxToCSV(),Integer.parseInt(t.getText()),SaveToTrain);
                System.out.println(P.DrawBoxToCSV());
                Action = 2;
            }
        });
    }
    public static void main(String[] arg) throws FileNotFoundException, InterruptedException, IOException {
        ColorPanel P = new ColorPanel();
        JFrame myFrame = new JFrame();
        Initializations(P,myFrame);
        //ColorPanel P2 = new ColorPanel();
        /*JFrame myFrame2 = new JFrame();
        Initializations(P2,myFrame2);*/
        JFrame SettingsFrame2 = new JFrame();
        JFrame DisplayFrame = new JFrame();
        NeuralNetwork Test = new NeuralNetwork(NetworkMiddle);
        myFrame.repaint();
        PopulateTestAndTrainArray();
        //delay
        boolean switcheroo = true;
        while (true) {
            //PrintWriter writer = new PrintWriter("Test.txt", "UTF-8");
            if (Action == 1) {
                boolean TrainMNIST = true;
                TrainMNIST = Test.ReadNetwork();
                System.out.println(TrainMNIST);
                if (!TrainMNIST){
                    PopulateTestAndTrainArray();
                    System.out.println("Training " + EpochSize);
                    long startTime = System.nanoTime();
                    Train(Test,DisplayFrame,myFrame,P);
                    long endTime = System.nanoTime();
                    System.out.println(ConsoleColors.BLUE_BRIGHT + (endTime - startTime) / (6e+10));
                    System.out.println("Training Done");
                    TestNetwork(Test);
                }
                else{
                    System.out.println("Trained using save file");
                }
                Action = 2;
            }
            else if (Action == 2) {
                P.SetMouseOn(true);
            }
            else if (Action == 3){
                Test.setValueI(P.GetDrawBoxValues());
                //P.DisplayBoxes();
                Test.DisplayInput();
                Test.Output(Test.ValueI,P);
                /*DisplayFrame.setVisible(false);
                DisplayFrame = new JFrame();
                InitializeDisplayFrame(DisplayFrame,myFrame,P,Test);*/
                Action = 2;
            }
            else if (Action == 4){
                Test.SaveNetwork();
                System.out.println("Saved");
                Action = 2;
            }
            else if (Action == 5) {
                Test.DisplayMiddleSizes();
                Test = new NeuralNetwork(NetworkMiddle);
                Test.DisplayMiddleSizes();
                Action = 2;
            }
            else if (Action == 6){
                if (!DisplayFrame.isActive()){
                    DisplayFrame.setVisible(false);
                    DisplayFrame = new JFrame();
                    InitializeDisplayFrame(DisplayFrame,myFrame,P,Test);
                    Action = 2;
                }
            }
            else if ((Action ==7 )){
                if (!SettingsFrame2.isActive()) {
                	InitializeSettingsFrame(SettingsFrame2, myFrame, P);
                	System.out.println("blah");
                }
            }
            else if (Action == 8){
                TestNetwork(Test);
                Action = 2;
            }
            myFrame.repaint(); 
        }

    }
    public static void InitializeDisplayFrame(JFrame DisplayFrame, JFrame myFrame, ColorPanel P, NeuralNetwork Test) {
        DisplayFrame.setSize(700,750);
        DisplayFrame.setBackground(Color.GRAY);
        DisplayNetwork F1 = new DisplayNetwork(Test,700,750);
        DisplayFrame.add(F1);
        DisplayFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) { Action = 2; }
        });
        DisplayFrame.repaint();
        DisplayFrame.setLocation(myFrame.getX()+myFrame.getWidth()+10,myFrame.getY());
        DisplayFrame.setResizable(false);
        DisplayFrame.setVisible(true);
    }
    public static void InitializeSettingsFrame(JFrame SettingsFrame2, JFrame myFrame, ColorPanel P) {
    	NetworkSettings Settings1 = new NetworkSettings();
        SettingsFrame2.setLocation(myFrame.getX()+myFrame.getWidth()+10,myFrame.getY());
        SettingsFrame2.setSize(500,350);
        SettingsFrame2.setResizable(false);
        Settings1.setBounds(SettingsFrame2.getX(), SettingsFrame2.getY(), SettingsFrame2.getWidth(), SettingsFrame2.getHeight());
        JTextField t1 = new JTextField();
        t1.setBounds(150,5,200,20);
        String Temp = "";
        for (int i = 0; i < NetworkMiddle.length; i++) {
        	Temp += NetworkMiddle[i] + ",";
        }
        t1.setText(Temp);
        JTextField t2 = new JTextField();
        t2.setText(Double.toString(LearningRate));
        JTextField t3 = new JTextField();
        t3.setText(Integer.toString(EpochSize));
        JTextField t4 = new JTextField();
        t4.setText(Integer.toString(Loops));
        JTextField t5 = new JTextField();
        t5.setText(Integer.toString(OuterLoopSize));
        t1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println(t1.getText());
                NetworkMiddle = StringToArr(t1.getText());
                Action = 5;
                SettingsFrame2.dispatchEvent(new WindowEvent(SettingsFrame2, WindowEvent.WINDOW_CLOSING));
                
            }
        });
        t2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println(t2.getText());
                LearningRate = Double.parseDouble(t2.getText());
            }
        });
        t3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println(t3.getText());
                EpochSize = Integer.parseInt(t3.getText());
            }
        });
        t4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println(t4.getText());
                Loops = Integer.parseInt(t4.getText());
            }
        });
        t5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                OuterLoopSize = Integer.parseInt(t5.getText());
            }
        });
        Settings1.setLayout(new BoxLayout(Settings1, BoxLayout.PAGE_AXIS));
        Border blackline = BorderFactory.createLineBorder(Color.black);
        InitializeTextBox("Middle Layers (end with a ',')", blackline, t1);
        InitializeTextBox("Learning Rate ( between 0.0 and 1.0)", blackline, t2);
        InitializeTextBox("Epoch size", blackline, t3);
        InitializeTextBox("Number of Loops", blackline, t4);
        InitializeTextBox("Number of Outer Loops", blackline, t5);
        Settings1.add(t1);
        Settings1.add(t2);
        Settings1.add(t3);
        Settings1.add(t4);
        Settings1.add(t5);
        String s1[] = { "Test", "Train" };
        JComboBox c1 = new JComboBox(s1);
        if (SaveToTrain) {c1.setSelectedIndex(1);}
        else {c1.setSelectedIndex(0);}
        String s2[] = { "Cyan_1", "Magenta_2", "Yellow_3", "Dark Blue_4",  "Red_5" , "Green_6", "Black_7"};
        JComboBox c2 = new JComboBox(s2);
        c2.setSelectedIndex(P.GetPenColor()-1);
        c1.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selection = (String)c1.getSelectedItem();
                System.out.println("Selected: "+selection ) ;
                if (selection.equals("Train")) {SaveToTrain = true;}
                if (selection.equals("Test")) {SaveToTrain = false;}
                System.out.println(SaveToTrain);
            }
          }
        });
        c2.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selection = (String)c2.getSelectedItem();
                System.out.println("Selected: "+selection ) ;
                P.ChangePenColor(Integer.parseInt(selection.substring(selection.length()-1)));
                System.out.println(SaveToTrain);
            }
          }
        });
        InitializeTextBox("Save scenarios to", blackline, c1);
        InitializeTextBox("Pen color", blackline, c2);
        Settings1.add(c1);
        Settings1.add(c2);
        SettingsFrame2.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                Action = 2;
            }
        });
        SettingsFrame2.getContentPane().add(Settings1);
        SettingsFrame2.add(Settings1, BorderLayout.PAGE_START);
        SettingsFrame2.repaint();
        SettingsFrame2.setVisible(true);
    }
    public static void InitializeTextBox(String Title, Border blackline, JComponent t1 ) {
        TitledBorder title1 = BorderFactory.createTitledBorder(
                blackline, Title);
        title1.setTitleJustification(TitledBorder.CENTER);
        t1.setBorder(title1);
    }
    public static int[] StringToArr(String str) {
        int counter = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i, i+1).equals(",")) {
                counter++;
            }
        }
        int[] arr = new int[counter];
        int i = 0;
        while (str.length() > 0) {
            arr[i] = Integer.parseInt(str.substring(0, str.indexOf(",")));
            str = str.substring(str.indexOf(",")+1);
            i++;
        }
        return arr;
    }
    public static void TestNetwork(NeuralNetwork Test) throws FileNotFoundException{
        System.out.println("Testing " + TestLines.size());
        int correct = 0;
        int size = TestLines.size();
        int[] NumberCorrect = new int[10];
        int[] TotalDigits = new int[10];
        for (int i = 0; i < size; i++) {
            double[] arrTest = GetCSVtoArray(i, false);
            int x = GetLabel(i, false);
            Test.Output(arrTest);
            TotalDigits[x]++;
            if (Test.Output == x) {
                correct++;
                NumberCorrect[x]++;
            }
            //if (ExitTrain) {System.out.println("Exit train is on");Action = 3; break;}
        }
        System.out.println("Correct count " + (correct / (double) size));
    }
    public static void Train(NeuralNetwork Test, JFrame DisplayFrame, JFrame myFrame, ColorPanel P) throws FileNotFoundException, InterruptedException{
        int index = 0;
        int[] TotalNumberCount = new int[10];
        for (int i = 0; i < OuterLoopSize ; i++){
            double[][] Epoch = new double[EpochSize][];
            int[] EpochLabel = new int[EpochSize];
            System.out.println("\nSetting up batch" + i);
            RandomIndexes(0,TrainLines.size()-1,EpochSize,EpochLabel,Epoch);
            System.out.println("Set up batch" + i);
            System.out.print(ConsoleColors.RESET);
            for (int k = 0; k < Loops; k++){
                for (int l = 0; l < EpochSize; l++){
                    double[] arrTest = Epoch[l];
                    int x = EpochLabel[l];
                    Test.Train(arrTest, x, LearningRate);
                    Test.Output(arrTest);
                    if (Test.Output == x){
                        System.out.print(ConsoleColors.GREEN_BACKGROUND + ConsoleColors.BLACK + l + " , ");
                        System.out.print(ConsoleColors.RESET);
                    }
                    else{
                        System.out.print(ConsoleColors.RED_BACKGROUND + ConsoleColors.BLACK + l + " , ");
                        System.out.print(ConsoleColors.RESET);
                    }
                }
                System.out.println();
            }
            /*DisplayFrame.setVisible(false);
            DisplayFrame = new JFrame();
            InitializeDisplayFrame(DisplayFrame,myFrame,P,Test);*/
            System.out.println(ConsoleColors.YELLOW_BACKGROUND_BRIGHT + Loops + ConsoleColors.RESET);
            if (ExitTrain){System.out.println("Exit Train on"); break;}
        }
        System.out.print(ConsoleColors.RESET);
        /*int index = 0;
        int[] TotalNumberCount = new int[10];
        for (int i = 0; i < 600; i++){
            double[][] Epoch = new double[EpochSize][];
            int[] EpochLabel = new int[EpochSize];
            int[] Indexes = RandomIndexes(0,59999,EpochSize);
            System.out.print(ConsoleColors.BLUE_BOLD);
            System.out.println("\nSetting up batch" + i);
            for (int j = 0; j < Epoch.length ; j++){
                Epoch[j] = GetCSVtoArray(Indexes[j], true);
                EpochLabel[j] = GetLabel(Indexes[j], true);
                TotalNumberCount[EpochLabel[j]]++;
                index++;
            }
            System.out.println("Set up batch" + i);
            System.out.print(ConsoleColors.RESET);
            for (int k = 0; k < Loops; k++){
                for (int l = 0; l < Epoch.length; l++){
                    double[] arrTest = Epoch[l];
                    int x = EpochLabel[l];
                    Test.Train(arrTest, x, 0.3);
                    Test.Output(arrTest);
                    if (Test.Output == x){
                        System.out.print(ConsoleColors.GREEN_BACKGROUND + ConsoleColors.BLACK + l + " , ");
                        System.out.print(ConsoleColors.RESET);
                    }
                    else{
                        System.out.print(ConsoleColors.RED_BACKGROUND + ConsoleColors.BLACK + l + " , ");
                        System.out.print(ConsoleColors.RESET);
                    }
                }
                System.out.println();
            }
            if (ExitTrain){System.out.println("Exit Train on"); break;}
        }
        System.out.print(ConsoleColors.PURPLE_BOLD_BRIGHT);
        System.out.println(Arrays.toString(TotalNumberCount));
        System.out.print(ConsoleColors.RESET);*/
    }
    static void Shuffle(List<String> array) {
        int n = array.size();
        Random random = new Random();
        for (int i = 0; i < array.size(); i++) {
            int randomValue = i + random.nextInt(n - i);
            String randomElement = array.get(randomValue); //[randomValue];
            array.set(randomValue,array.get(i));
            array.set(i,randomElement);
        }
    }
    public static void readCSV(int LineNum, JFrame myFrame, ColorPanel P, boolean Train) throws FileNotFoundException, InterruptedException {
        String Line;
        if (Train)
            Line = TrainLines.get(LineNum);
        else
            Line = TestLines.get(LineNum);
        int Num = 1;
        for (int i = 0; i < P.DrawBoxes.length; i++) { //  P.DrawBoxes.length
            for (int j = 0; j < P.DrawBoxes.length; j++) { // P.DrawBoxes[i].length
                P.DrawBoxes[i][j].ShadeColor = FindElementInLine(Num,Line);
                Num++;
            }
        }
        myFrame.repaint();
    }
    public static void PopulateTestAndTrainArray() throws FileNotFoundException, IOException {
        TrainLines = Files.readAllLines(Paths.get(location + "\\mnist_train.csv"));
        try {
            List<String> TrainLine2 = Files.readAllLines(Paths.get(location + "\\mnist_train_user.csv"));
            for (int i  = 0; i < TrainLine2.size(); i++) {
                TrainLines.add(TrainLine2.get(i));
            }
        }catch (Exception e) {
        }
        TestLines = Files.readAllLines(Paths.get(location + "\\mnist_test.csv"));
        System.out.println(TestLines.size());
        try {
            List<String> TestLine2 = Files.readAllLines(Paths.get(location + "\\mnist_test_user.csv"));
            for (int i  = 0; i < TestLine2.size(); i++) {
                TestLines.add(TestLine2.get(i));
            }
        }catch (Exception e) {
        }
    }
    public static double[] GetCSVtoArray(int LineNum, boolean Train) throws FileNotFoundException {
        String Line;
        if (Train)
            Line = TrainLines.get(LineNum);
        else
            Line = TestLines.get(LineNum);
        double[] Return = new double[28*28];
        for (int i = 0; i < 784; i++){
            Return[i] = FindElementInLine(i+1,Line)/255.0;
        }
        return  Return;
    }

    public static int GetLabel(int LineNum, boolean Train) {
        String Line;
        if (Train)
            Line = TrainLines.get(LineNum);
        else
            Line = TestLines.get(LineNum);
        return Integer.parseInt(Line.substring(0, Line.indexOf(",")));
    }
    public static int[] RandomIndexes(int UpperBound, int LowerBound, int Size){
        int[] Indexes = new int[Size];
        for (int i= 0; i < Indexes.length; i++){
            Indexes[i] = -1;
        }
        for (int i = 0; i < Indexes.length; i++){
            int n = (int)(Math.random() * (UpperBound-LowerBound)+LowerBound);
            while (NumberExists(Indexes,n)){
                n = (int)(Math.random() * (UpperBound-LowerBound)+LowerBound);
            }
            Indexes[i] = n;
        }
        return Indexes;
    }
    public static void RandomIndexes(int UpperBound, int LowerBound, int Size, int[] EpochLabel, double[][] Epoch) throws FileNotFoundException{
        int[] Indexes = new int[Size];
        for (int i= 0; i < Indexes.length; i++){
            Indexes[i] = -1;
        }
        for (int i = 0; i < Indexes.length; i++){
            int n = (int)(Math.random() * (UpperBound-LowerBound)+LowerBound);
            while (NumberExists(Indexes,n)){
                n = (int)(Math.random() * (UpperBound-LowerBound)+LowerBound);
            }
            Indexes[i] = n;
            Epoch[i] = GetCSVtoArray(n, true);
            EpochLabel[i] = GetLabel(n, true);
        }
    }
    public static boolean NumberExists(int[] arr, int num){
        for (int i: arr){
            if (i == num){return true;}
        }
        return false;
    }
    public static int FindElementInLine(int Number, String Line) {
        if (Number == 0) {
            return Integer.parseInt(Line.substring(0, Line.indexOf(",")));
        }
        else {
            int CharStart = 0;
            int Element = -1;
            for (int i = 0; i < Number; i++) {
                int Index1 = Line.indexOf(",", CharStart);
                int Index2 = 0;
                if (Index1+1  < Line.length()-1) {
                    Index2 = Line.indexOf(",", Index1+1);
                    Element = Integer.parseInt(Line.substring(Index1+1, Index2));
                }
                else {
                    Index2 = Line.length();
                    Element = Integer.parseInt(Line.substring(Index1+1, Index2));
                }
                CharStart = Index2;
            }
            return Element;
        }
    }
    public static void WriteInTrainData(String PixelValues, int Label, boolean Train) {
    	if (Train) {
	        try {
	            FileWriter fw= new FileWriter(location + "\\mnist_train_user.csv",true);
	            PrintWriter printWriter = new PrintWriter(fw);
	            printWriter.println(Label + PixelValues);
	            fw.close();
	        } catch (IOException e1) {
	        	File file = new File(location + "\\mnist_train_user.csv");
	        	try {
					System.out.println(file.createNewFile());
				} catch (IOException e) {
					System.out.println("File Exists");
				}
	        	
	        	//WriteInTrainData(PixelValues,Label, Train);
	        }
    	}
    	else {
    		try {
	            FileWriter fw= new FileWriter(location + "\\mnist_test_user.csv",true);
	            PrintWriter printWriter = new PrintWriter(fw);
	            printWriter.println(Label + PixelValues);
	            fw.close();
	        } catch (IOException e1) {
	        	File file = new File(location + "\\mnist_test_user.csv");
	        	try {
					file.createNewFile();
				} catch (IOException e) {
					System.out.println("File Exists");
				}
	        	WriteInTrainData(PixelValues,Label, Train);
	        }
    	}
    		
    }
}