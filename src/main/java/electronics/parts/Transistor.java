package electronics.parts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class Transistor extends Part {
	public ConnectionPort left, right, input;
	private boolean isOn = false;
	
	public Transistor(Point location) {
		this(location, false);
	}
	
	public Transistor(Point location, boolean center) {
		super(location, 60, 70, center);
		
		left = new ConnectionPort(this, new Point(Part.getPortXOffset(width, 5, 0, 3), height - 10));
		input = new ConnectionPort(this, new Point(Part.getPortXOffset(width, 5, 1, 3), height - 10));
		right = new ConnectionPort(this, new Point(Part.getPortXOffset(width, 5, 2, 3), height - 10));
	}
	
	@Override
	public boolean getsPower(ConnectionPort from) {
		if (from == input) {
			return false;
		}
		
		if (cycleLockIsOn()) {
			return false;
		}
		
		if (!input.getsPower(false)) {
			cycleLockOff();
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
		g.setColor(Color.BLUE);
		g.fillRect(location.x, location.y, width, height);
		left.render(g, true);
		right.render(g, true);
		input.render(g, true);
	}
	
	@Override
	public ArrayList<ConnectionPort> getConnections() {
		return new ArrayList<>(Arrays.asList(left, right, input));
	}
}
