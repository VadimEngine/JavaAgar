import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Player {

	private int x;
	private int y;
	private int width;
	private Color color;
	boolean up, down, left, right;
	int speed = 5;//accelerations, particle-array for breaking, mousefollowing

	public Player(int x, int y, int width, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.color = color;
	}

	public Player(int x , int y) {
		this(x, y, 20, Color.YELLOW);
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

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(10));
		g.setColor(Color.BLACK);
		g.drawOval(x-(width / 2), y - (width / 2), width, width);
		g.setColor(color);
		g.fillOval(x-(width / 2), y - (width / 2), width, width);
	}

	public void tick() {
		//speed = width / 4; inversely related
		if (up) {
			y -= speed;
		}

		if (down) {
			y += speed;
		}

		if (left) {
			x -= speed;
		}

		if (right) {
			x += speed;
		}
	}

	public void takePress(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			up = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			down = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			left = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			right = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			width++;
		}
	}

	public void takeRelease(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_W) {
			up = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			down = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			left = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			right = false;
		}
	
	}

}
