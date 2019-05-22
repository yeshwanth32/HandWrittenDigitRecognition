import java.awt.Color;
import java.awt.Graphics;


public class Particle{
    public int X, Y;
    public int Interation;
    public double Velocity;
    public double Acceleration;
    public Particle() {
    	Velocity = 5.0;
    	Acceleration = -9.8;
    	Interation = 0;
    }
    public void UpdateParticle() {
    	Y -= Velocity + (0.5*Acceleration);
    	Interation++;
    }
    public void DrawParticle(Graphics g) {
    	UpdateParticle();
        g.setColor(Color.CYAN);
        g.fillOval(X, Y, 5, 5);
    }

}