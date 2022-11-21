package electronics.parts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class ORGate extends Part {
	public ConnectionPort leftIn, rightIn, out;
	
	public ORGate(Point location) {
		this(location, false);
	}
	
	public ORGate(Point location, boolean center) {
		super(location, 40, 50, center);
		
		leftIn = new ConnectionPort(this, new Point(Part.getPortXOffset(width, 5, 0, 2), height - 10));
		rightIn = new ConnectionPort(this, new Point(Part.getPortXOffset(width, 5, 1, 2), height - 10));
		
		out = new ConnectionPort(this, new Point(Part.getPortXOffset(width, 0, 0, 1), -10));
	}
	
	@Override
	public boolean getsPower(ConnectionPort from) {
		if (cycleLockIsOn()) {
			return false;
		}
		
		boolean res = false;
		if (from == out) {
			res = leftIn.getsPower(false) || rightIn.getsPower(false);
		}
		
		cycleLockOff();
		
		return res;
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.fillRect(location.x, location.y, width, height);
		leftIn.render(g, true);
		rightIn.render(g, true);
		out.render(g, true);
	}
	
	@Override
	public ArrayList<ConnectionPort> getConnections() {
		return new ArrayList<>(Arrays.asList(leftIn, rightIn, out));
	}
}
