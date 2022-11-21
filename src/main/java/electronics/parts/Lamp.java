package electronics.parts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class Lamp extends Part {
	public ConnectionPort left, right;
	private boolean renderOn = false;
	
	public Lamp(Point location) {
		this(location, false);
	}
	
	public Lamp(Point location, boolean center) {
		super(location, 40, 40, center);
		
		left = new ConnectionPort(this, new Point(Part.getPortXOffset(width, 5, 0, 2), height - 10));
		right = new ConnectionPort(this, new Point(Part.getPortXOffset(width, 5, 1, 2), height - 10));
	}
	
	@Override
	public boolean getsPower(ConnectionPort from) {
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
	public void resetRender() {
		renderOn = false;
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.YELLOW);
		if (left.getsPower(false) && right.getsPower(false)) {
			g.fillOval(location.x, location.y, width, height);
			g.drawOval(location.x, location.y, width, height);
		} else {
			g.drawOval(location.x, location.y, width, height);
		}
		left.render(g, true);
		right.render(g, true);
	}
	
	@Override
	public ArrayList<ConnectionPort> getConnections() {
		return new ArrayList<>(Arrays.asList(left, right));
	}
}
