package electronics.parts;

import electronics.HelperFunctions;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class ConnectionPort {
	private int width = 10;
	private int height = 20;
	
	private Part parent;
	private Point location; //relative to part
	private ConnectionPort connected;
	
	public ConnectionPort(Part parent, Point location) {
		this.parent = parent;
		this.location = location;
	}
	
	public boolean getsPower(boolean askParent) {
		if (askParent) {
			return parent.getsPower(this);
		} else {
			if (connected == null) return false;
			return connected.getsPower(true);
		}
	}
	
	public void connect(ConnectionPort other) {
		connected = other;
		other.connected = this;
	}
	
	public void render(Graphics2D g, boolean render) {
		render(g, render, Color.GRAY);
	}
	
	public void render(Graphics2D g, boolean render, Color c) {
		g.setColor(c);
		if (render) {
			g.fillRect(parent.location.x + location.x, parent.location.y + location.y, width, height);
		}
		
		if (connected != null) {
			g.setColor(Color.WHITE);
			Point p1 = getCenter();
			Point p2 = connected.getCenter();
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}
	
	public Point getCenter() {
		return new Point(parent.location.x + location.x + width / 2, parent.location.y + location.y + height / 2);
	}
	
	public void deleteConnections() {
		if (connected != null) {
			connected.connected = null;
		}
		connected = null;
	}
	
	public boolean isConnected() {
		return connected != null;
	}
	
	public boolean isInside(int x, int y) {
		return x >= parent.location.x + location.x && x < parent.location.x + location.x + width && y >= parent.location.y + location.y && y < parent.location.y + location.y + height;
	}
	
	public double distanceToConnection(int x, int y) {
		if (connected == null) {
			return -1;
		}
		Point p1 = getCenter();
		Point p2 = connected.getCenter();
		return HelperFunctions.distanceToLineSegment(p1.x, p1.y, p2.x, p2.y, x, y);
	}
	
	public Part getParent() {
		return parent;
	}
	
	public Point getLocation() {
		return location;
	}
}
