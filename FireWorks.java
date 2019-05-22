import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
@SuppressWarnings("unused")
public class FireWorks extends JPanel{
	public Particle[] particles;
	public FireWorks() {
		particles = new Particle[10];
		int x = 120;
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new Particle();
			particles[i].X = x;
			particles[i].Y = 340;
			x+= 40;
		}
	}
    public void paintComponent(Graphics g) {
        for (int i = 0; i < particles.length; i++) {
        	particles[i].DrawParticle(g);
        }
    }
   
}