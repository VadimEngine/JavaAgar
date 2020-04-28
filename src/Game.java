import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1156557125908448321L;

	private boolean running;
	private Thread thread;

	private int FPS;

	private Player player;
	//player x & y & zoom change on growth


	private ArrayList<Particle> pl = new ArrayList<>();

	Random random = new Random();

	public Game() {
		Dimension size = new Dimension(640, 480);
		setSize(size);
		setPreferredSize(size);
		setFocusable(true);
		addKeyListener(this);
		player = new Player(getWidth() / 2, getHeight() / 2);
	}


	public synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long lastTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;

		requestFocus();

		while (running) {
			long now = System.nanoTime();
			long passedTime = now - lastTime;
			lastTime = now;
			if (passedTime < 0) {
				passedTime = 0;
			}
			if (passedTime > 100000000) {
				passedTime = 100000000;
			}

			unprocessedSeconds += passedTime / 1000000000.0;

			boolean ticked = false;
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;

				tickCount++;
				if (tickCount % 60 == 0) {
					//System.out.println(frames + " fps");
					FPS = frames;
					lastTime += 1000;
					frames = 0;
				}
			}

			if (ticked) {
				render();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}

	public void tick() {
		player.tick();

		if (random.nextInt(50) == 0) {
			pl.add(new Particle(random.nextInt(640 * 10),
					random.nextInt(480 * 10)));
		} 

		for (int i = 0; i < pl.size(); i ++) {
			if (colid(player, pl.get(i))) {
				player.setWidth(player.getWidth() + 1);
				pl.remove(i);
				break;
			}
		}

	}

	public void render() {//zooming?
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}


		Graphics g = bs.getDrawGraphics();
		g.setFont(new Font("TimesRoman", 1, 16));

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);

		Graphics2D g2d = (Graphics2D)g;

		g2d.translate(-player.getX() + getWidth() / 2, -player.getY() + getHeight() / 2);

		for (int i = 0; i < 10 * getHeight(); i++) {//GAME DIMENSION, AI's, colliing percentagte of their width
			g.drawLine(0, i * getHeight() / 10, getWidth()* 10, i * getHeight() / 10);
//			g.drawLine(i * getWidth() / 10, 0, i * getWidth() / 10, getHeight() * 10);
		}

		for (int i = 0; i < 10 * getWidth(); i++) {//GAME DIMENSION, AI's, colliing percentagte of their width
			g.drawLine(i * getWidth() / 10, 0, i * getWidth() / 10, getHeight() * 10);

		}

		g.drawString("FPS: " + FPS, 16 + player.getX() - getWidth() / 2,
				18 + player.getY() - getHeight() / 2);
		for (int i = 0; i < pl.size(); i++) {
			pl.get(i).render(g);
		}
		player.render(g);
		g.dispose();
		bs.show();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		player.takePress(e);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		player.takeRelease(e);
	}

	public static void main(String args[]) {
		Game game = new Game();

		JFrame frame = new JFrame();
		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();

		game.start();
	}

	private boolean colid(Player p1, Particle p2) {
		int x1 = p1.getX() - (p1.getWidth() / 2);
		int x2 = p2.getX();
		int width1 = p1.getWidth();
		int y1 = p1.getY() - (p1.getWidth() / 2);
		int y2 = p2.getY();
		int width2 = p2.getWidth();

		if((x2 > x1 && x2 < x1 + width1) && (y2 > y1 && y2 < y1 + width1)) {
			return true;
		}

		if((x1 > x2 && x1 < x2 + width2) && (y1 > y2 && y1 < y2 + width2)) {
			return true;
		}

		return false;
	}

}
