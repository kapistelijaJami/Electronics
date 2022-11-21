package electronics.parts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class Battery extends Part {
	public ConnectionPort first, second;
	
	public Battery(Point location) {
		this(location, false);
	}
	
	public Battery(Point location, boolean center) {
		super(location, 50, 70, center);
		
		first = new ConnectionPort(this, new Point(Part.getPortXOffset(width, 5, 0, 2), height - 10));
		second = new ConnectionPort(this, new Point(Part.getPortXOffset(width, 5, 1, 2), height - 10));
	}
	
	@Override
	public boolean getsPower(ConnectionPort from) {
		return true;
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(139, 0, 0));
		g.fillRect(location.x, location.y, width, height);
		first.render(g, true);
		second.render(g, true);
	}
	
	@Override
	public ArrayList<ConnectionPort> getConnections() {
		return new ArrayList<>(Arrays.asList(first, second));
	}
}
