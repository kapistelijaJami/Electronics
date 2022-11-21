package electronics.parts;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Part {
	protected Point location;
	protected int width;
	protected int height;
	
	private boolean cycleLock = false;
	
	public Part(Point location, int width, int height, boolean center) {
		this.location = location;
		this.width = width;
		this.height = height;
		
		if (center) {
			setCenter(location.x, location.y);
		}
	}
	
	public abstract boolean getsPower(ConnectionPort from);
	
	protected boolean cycleLockIsOn() {
		if (cycleLock) {
			return true;
		}
		cycleLock = true;
		return false;
	}
	
	protected void cycleLockOff() {
		cycleLock = false;
	}
	
	public abstract void render(Graphics2D g);
	
	public static int getPortXOffset(int width, int space, int i, int total) {
		if (total == 1) {
			return width / 2 - 5;
		}
		return (width - space * 2 - 10) / (total - 1) * i + 5;
	}
	
	public void resetRender() {}

	public boolean isInside(int x, int y) {
		return x >= location.x && x < location.x + width && y >= location.y && y < location.y + height;
	}
	
	public ConnectionPort isInsideConnectionPort(int x, int y) {
		for (ConnectionPort connection : getConnections()) {
			if (connection.isInside(x, y)) {
				return connection;
			}
		}
		
		return null;
	}
	
	public void setCenter(int x, int y) {
		location.x = x - width / 2;
		location.y = y - height / 2;
	}
	
	public abstract ArrayList<ConnectionPort> getConnections();

	public void deleteConnections() {
		for (ConnectionPort connection : getConnections()) {
			connection.deleteConnections();
		}
	}

	public boolean removeConnectionWithDistance(int x, int y) {
		for (ConnectionPort connection : getConnections()) {
			double dist = connection.distanceToConnection(x, y);
			if (dist == -1) continue;
			
			if (dist <= 5) {
				connection.deleteConnections();
				return true;
			}
		}
		
		return false;
	}
}
