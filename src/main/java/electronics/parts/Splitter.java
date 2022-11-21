package electronics.parts;

import electronics.HelperFunctions;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Splitter extends Part {
	public ArrayList<ConnectionPort> ports = new ArrayList<>();
	
	public Splitter(Point location) {
		this(location, false);
	}
	
	public Splitter(Point location, boolean center) {
		super(location, 20, 20, center);
		
		ports.add(new ConnectionPort(this, new Point(width / 2 - 5, height / 2 - 10)));
		ports.add(new ConnectionPort(this, new Point(width / 2 - 5, height / 2 - 10)));
		ports.add(new ConnectionPort(this, new Point(width / 2 - 5, height / 2 - 10)));
	}
	
	@Override
	public boolean getsPower(ConnectionPort from) {
		if (cycleLockIsOn()) {
			return false;
		}
		
		boolean ret = false;
		
		for (ConnectionPort port : ports) {
			if (port != from) {
				ret |= port.getsPower(false);
			}
		}
		
		cycleLockOff();
		
		return ret;
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillOval(location.x, location.y, width, height);
		g.setColor(Color.GRAY);
		g.fillOval(location.x + width / 2 - 5, location.y + height / 2 - 5, 10, 10);
		
		for (ConnectionPort port : ports) {
			port.render(g, false);
		}
	}
	
	@Override
	public ArrayList<ConnectionPort> getConnections() {
		return ports;
	}
	
	@Override
	public ConnectionPort isInsideConnectionPort(int x, int y) {
		if (HelperFunctions.distance(ports.get(0).getCenter().x, ports.get(0).getCenter().y, x, y) > 5) {
			return null;
		}
		
		for (ConnectionPort port : ports) {
			if (!port.isConnected()) {
				return port;
			}
		}
		
		ports.add(new ConnectionPort(this, ports.get(0).getLocation()));
		return ports.get(ports.size() - 1);
	}
}
