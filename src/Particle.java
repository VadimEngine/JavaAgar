import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Particle {
	
	
	private int x;
	private int y;
	private int width;
	private Color color;
	private static Random random = new Random();

	public Particle(int x, int y, int width, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.color = color;
	}

	public Particle(int x , int y) {
		this(x, y, 10, new Color(random.nextInt(255),
				random.nextInt(255), random.nextInt(255)));
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(5));
		g.setColor(Color.BLACK);
		g.drawOval(x, y, width, width);
		g.setColor(color);
		g.fillOval(x, y, width, width);
	}

}
