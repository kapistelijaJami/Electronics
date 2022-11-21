package electronics.parts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

public class Button extends Part {
	public ConnectionPort left, right;
	private boolean isPressed = false;

	public Button(Point location) {
		this(location, false);
	}
	
	public Button(Point location, boolean center) {
		super(location, 40, 30, center);
		
		left = new ConnectionPort(this, new Point(Part.getPortXOffset(width, 5, 0, 2), height - 10));
		right = new ConnectionPort(this, new Point(Part.getPortXOffset(width, 5, 1, 2), height - 10));
	}
	
	public void press() {
		isPressed = true;
	}
	
	public void release() {
		isPressed = false;
	}
	
	public void toggle() {
		isPressed = !isPressed;
	}
	
	@Override
	public boolean getsPower(ConnectionPort from) {
		if (!isPressed) return false;
		
		if (cycleLockIsOn()) {
			return false;
		}
		
		boolean res = false;
		if (from == left) {
			res = right.getsPower(false);
		} else if (from == right) {
			res = left.getsPower(false);
		}
		
		cycleLockOff();
		
		return res;
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(location.x, location.y, width, height);
		
		Rectangle currentClipBounds = g.getClipBounds();
		
		g.clipRect(location.x, location.y - width/3, width, width / 2);
		if (isPressed) {
			g.fillOval(location.x + width/2 - width/2 + 1, location.y - width/3 + width/5, width - 2, width);
		} else {
			g.fillOval(location.x + width/2 - width/2 + 1, location.y - width/3, width - 2, width);
		}
		g.setClip(currentClipBounds);
		left.render(g, true);
		right.render(g, true);
	}
	
	@Override
	public ArrayList<ConnectionPort> getConnections() {
		return new ArrayList<>(Arrays.asList(left, right));
	}
}
