import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class ColorPanel extends JPanel{
    public Block[][] DrawBoxes;
    public int Predicted;
    private int Width = 15;
    private boolean MouseOn;
    @SuppressWarnings("unused")
    public ColorPanel() {
        InitializeDrawBox();
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent me) {
                if (MouseOn)
                    DrawBoxWorkingTest(me.getX(), me.getY());
            }
        });
        Predicted = 1;
    }
    public double[] GetDrawBoxValues(){
        if (DrawBoxes == null){ return null;}
        double[] Values = new double[DrawBoxes.length*DrawBoxes[0].length];
        int k = 0;
        for (int i = 0; i < DrawBoxes.length; i++){
            for (int j = 0; j < DrawBoxes[i].length; j++){
                Values[k] = DrawBoxes[i][j].ShadeColor/255.0;
                k++;
            }
        }
        return Values;
    }
    public void ArrayToDrawBox(double[] values){
        int k = 0;
        for (int i = 0; i < DrawBoxes.length; i++){
            for (int j = 0; j < DrawBoxes[i].length; j++){
                DrawBoxes[i][j].ShadeColor = (int)(values[k]*255);
                //System.out.println(values[k]*255);
                k++;
            }
        }
    }
    public void DisplayBoxes() {
        System.out.println("Console Display");
        int RoundToValue = 10000;
        for (int i = 0; i < DrawBoxes.length; i++){
            for (int j = 0; j < DrawBoxes[i].length; j++) {
                if (((Math.floor((DrawBoxes[i][j].ShadeColor/255.0)*RoundToValue))/RoundToValue) != 0.0)
                    System.out.print(" " +  ConsoleColors.RED +((Math.floor((DrawBoxes[i][j].ShadeColor/255.0)*RoundToValue))/RoundToValue) + ConsoleColors.RESET);
                else
                    System.out.print(" " + ((Math.floor((DrawBoxes[i][j].ShadeColor/255.0)*RoundToValue))/RoundToValue) );
            }
            System.out.println();
        }
        System.out.println("Console Display end");
        // Test2
    }
    public void SetMouseOn(boolean on) {MouseOn = on;}
    public void DrawBoxWorkingTest(int x, int y) {
        for (int i = 0; i < DrawBoxes.length; i++) {
            for (int j = 0; j < DrawBoxes[i].length; j++) {
                if (x >= DrawBoxes[i][j].X && x <= DrawBoxes[i][j].X + DrawBoxes[i][j].Width) {
                    if (y >= DrawBoxes[i][j].Y && y <= DrawBoxes[i][j].Y + DrawBoxes[i][j].Width) {
                        DrawBoxes[i][j].ShadeColor = 255;
                        int Rand = (int)((Math.random()*150) + 10);
                        if (j > 0 && DrawBoxes[i][j-1].ShadeColor >= 0 && (DrawBoxes[i][j-1].ShadeColor + Rand) < 255) {
                            DrawBoxes[i][j-1].ShadeColor += Rand;
                        }
                        if (j < DrawBoxes[i].length-1 && DrawBoxes[i][j+1].ShadeColor >= 0 && (DrawBoxes[i][j+1].ShadeColor + Rand) < 255) {
                            DrawBoxes[i][j+1].ShadeColor += Rand;
                        }
                        if (i < DrawBoxes.length-1 && DrawBoxes[i+1][j].ShadeColor == 0 && (DrawBoxes[i+1][j].ShadeColor + Rand) < 255) {
                            DrawBoxes[i+1][j].ShadeColor += Rand;
                        }
                        if (i > 0 && DrawBoxes[i-1][j].ShadeColor == 0 && (DrawBoxes[i-1][j].ShadeColor + Rand) < 255) {
                            DrawBoxes[i-1][j].ShadeColor += Rand;
                        }
                    }
                }
            }
        }
    }
    public void ChangePenColor(int Color) {
    	for (int i = 0; i < DrawBoxes.length; i++) {
    		for (int j = 0; j < DrawBoxes[i].length; j++) {
    			DrawBoxes[i][j].colorType = Color;
    		}
    	}
    }
    public int GetPenColor() {
    	return DrawBoxes[0][0].colorType;
    }
    public void paintComponent(Graphics g) {
        DrawBox(g);
        DrawResult(g,Predicted);
    }
    public void InitializeDrawBox() {
        DrawBoxes = new Block[28][28];
        int x = 10, y = 10;
        for (int i = 0; i < DrawBoxes.length; i++) {
            for (int j = 0; j < DrawBoxes[i].length; j++) {
                DrawBoxes[i][j] = new Block();
                DrawBoxes[i][j].X = x;
                DrawBoxes[i][j].Y = y;
                DrawBoxes[i][j].ShadeColor = 0;
                x+=Width;
            }
            x = 10;
            y+=Width;
        }
    }
    public void DrawBox(Graphics g) {
        for (int i = 0; i < DrawBoxes.length; i++) {
            for (int j = 0; j < DrawBoxes[i].length; j++) {
                DrawBoxes[i][j].DrawBlock(g);
            }
        }
    }
    public void DrawResult(Graphics g, int Number) {
        g.drawRect(450, 15, 120, 120);
        g.setColor(Color.RED);
        g.setFont(getFont());
        g.setFont(new Font("Comic Sans MS", Font.PLAIN, 42));
        g.drawString(Integer.toString(Number), 490, 90);
    }
    public String DrawBoxToCSV(){
        String str = "";
        for (Block[] i: DrawBoxes){
            for (Block j: i){
                str += "," + j.ShadeColor ;
            }
        }
        return str;
    }
}