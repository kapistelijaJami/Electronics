package electronics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
	public static int WIDTH;
	public static int HEIGHT;
	public static final double FPS = 120.0;
	private boolean running = true;
	private Window window;
	
	private ObjectHandler objectHandler;
	
	public synchronized void stop() {
		running = false;
		System.exit(0);
	}
	
	public void windowLostFocus() { }
	
	private void init() {
		window = new Window(Constants.WIDTH, Constants.HEIGHT, "Electronics", this, 0, false);
		
		KeyInput input = new KeyInput(this);
		this.addMouseListener(input);
		this.addMouseMotionListener(input);
		this.addMouseWheelListener(input);
		this.addKeyListener(input);
		
		objectHandler = new ObjectHandler();
	}
	
	@Override
	public void run() {
		init();
		
		this.requestFocus();
		
		long now = System.nanoTime();
		long nsBetweenFrames = (long) (1e9 / FPS);
		
		long time = System.currentTimeMillis();
		int frames = 0;
		
		while (running) {
			if (now + nsBetweenFrames <= System.nanoTime()) {
				now += nsBetweenFrames;
				update();
				render();
				frames++;
				
				if (time + 250 < System.currentTimeMillis()) {
					time += 250;
					window.setTitle("Electronics " + (frames * 4) + " fps");
					frames = 0;
				}
			}
		}
		window.close();
	}
	
	private void update() {
		objectHandler.update();
	}
	
	private void render() {
		Graphics2D g = getGraphics2D();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		objectHandler.render(g);
		
		g.dispose();
		this.getBufferStrategy().show();
	}
	
	private Graphics2D getGraphics2D() {
		BufferStrategy bs = this.getBufferStrategy();
		while (bs == null) {
			createBufferStrategy(3);
			bs = this.getBufferStrategy();
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		setGraphicsRenderingHints(g);
		
		return g;
	}
	
	public static void setGraphicsRenderingHints(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR); //for image scaling to look sharp, might not be good for all images
	}
	
	public void mousePressed(MouseEvent e) {
		objectHandler.mousePressed(e);
	}
	
	public void mouseReleased(MouseEvent e) {
		objectHandler.mouseReleased(e);
	}
	
	public void mouseDragged(MouseEvent e) {
		objectHandler.mouseDragged(e);
	}
	
	public void mouseMoved(MouseEvent e) {
		objectHandler.mouseMoved(e);
	}
	
	public void deletePart() {
		Point p = MouseInfo.getPointerInfo().getLocation();
		window.convertFromScreenToFrame(p);
		objectHandler.deletePartAtLocation(p);
	}
	
	public void createPart(String part) {
		Point p = MouseInfo.getPointerInfo().getLocation();
		window.convertFromScreenToFrame(p);
		objectHandler.createPart(part, p);
	}
}
