package electronics;

import electronics.parts.ANDGate;
import electronics.parts.Battery;
import electronics.parts.Button;
import electronics.parts.ConnectionPort;
import electronics.parts.Lamp;
import electronics.parts.ORGate;
import electronics.parts.Part;
import electronics.parts.Splitter;
import electronics.parts.Transistor;
import electronics.parts.XORGate;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class ObjectHandler {
	private ArrayList<Part> parts = new ArrayList<>();
	
	private Part attached = null;
	private Point clickStartLocation = null;
	
	private ConnectionPort connecting = null;
	private Point connectingLocation = null;
	
	private boolean wasDragged = false;
	
	public ObjectHandler() {
		Battery battery = new Battery(new Point(100, 200));
		Splitter splitter = new Splitter(new Point(200, 100));
		Button button = new Button(new Point(250, 200));
		Transistor transistor = new Transistor(new Point(300, 100));
		Lamp lamp = new Lamp(new Point(400, 200));
		
		parts.add(battery);
		parts.add(splitter);
		parts.add(button);
		parts.add(transistor);
		parts.add(lamp);
		
		battery.first.connect(splitter.ports.get(0));
		
		splitter.ports.get(1).connect(transistor.left);
		splitter.ports.get(2).connect(button.left);
		
		button.right.connect(transistor.input);
		
		transistor.right.connect(lamp.right);
		
		lamp.left.connect(battery.second);
	}
	
	public void addPart(Part part) {
		parts.add(part);
	}
	
	public void update() {
		
	}
	
	public void render(Graphics2D g) {
		for (int i = 0; i < parts.size(); i++) {
			Part part = parts.get(i);
			part.render(g);
		}
		
		if (connecting != null) {
			Point p1 = connecting.getCenter();
			g.drawLine(p1.x, p1.y, connectingLocation.x, connectingLocation.y);
		}
		
		resetPartsRender();
	}
	
	private void resetPartsRender() {
		for (Part part : parts) {
			part.resetRender();
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (connecting != null) {
				mouseReleased(e);
			}
			deletePartAtLocation(e.getPoint());
			return;
		}
		
		clickStartLocation = new Point(e.getX(), e.getY());
		for (Part part : parts) {
			if ((connecting = part.isInsideConnectionPort(e.getX(), e.getY())) != null) { //this should be first, so it grabs the port first
				connectingLocation = new Point(e.getX(), e.getY());
				return;
			} else if (part.isInside(e.getX(), e.getY())) {
				attached = part;
				return;
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if (attached != null && !wasDragged) {
			if (attached instanceof Button) {
				((Button) attached).toggle();
			}
		}
		
		//trying to connect two ports
		if (connecting != null && wasDragged) {
			ConnectionPort temp = null;
			for (Part part : parts) {
				if ((temp = part.isInsideConnectionPort(e.getX(), e.getY())) != null) {
					if (temp.getParent() == connecting.getParent()) {
						continue;
					}
					temp.deleteConnections();
					connecting.deleteConnections();
					connecting.connect(temp);
					break;
				}
			}
			
			if (temp == null && HelperFunctions.distance(clickStartLocation.x, clickStartLocation.y, e.getX(), e.getY()) > 20) {
				Part splitter = new Splitter(new Point(e.getX(), e.getY()), true);
				temp = splitter.isInsideConnectionPort(e.getX(), e.getY());
				if (temp != null) {
					parts.add(splitter);
					connecting.deleteConnections();
					temp.connect(connecting);
				}
			}
		}
		
		attached = null;
		connecting = null;
		connectingLocation = null;
		wasDragged = false;
	}
	
	public void mouseDragged(MouseEvent e) {
		if (!wasDragged && HelperFunctions.distance(clickStartLocation.x, clickStartLocation.y, e.getX(), e.getY()) <= 3) {
			return;
		}
		if (attached != null) {
			attached.setCenter(e.getX(), e.getY());
		} else if (connecting != null) {
			connectingLocation = new Point(e.getX(), e.getY());
		} else {
			for (Part part : parts) {
				if (part.removeConnectionWithDistance(e.getX(), e.getY())) {
					break;
				}
			}
		}
		wasDragged = true;
	}
	
	public void mouseMoved(MouseEvent e) {
		
	}
	
	private void deletePart(Part part) {
		if (part == null) return;
		parts.remove(part);
		part.deleteConnections();
	}
	
	public void deletePartAtLocation(Point p) {
		for (Part part : parts) {
			if (part.isInside(p.x, p.y)) {
				deletePart(part);
				return;
			}
		}
	}

	public void createPart(String part, Point point) {
		Part p;
		switch (part) {
			case "P":
				p = new Battery(point, true);
				break;
			case "L":
				p = new Lamp(point, true);
				break;
			case "S":
				p = new Splitter(point, true);
				break;
			case "T":
				p = new Transistor(point, true);
				break;
			case "B":
				p = new Button(point, true);
				break;
			case "O":
				p = new ORGate(point, true);
				break;
			case "A":
				p = new ANDGate(point, true);
				break;
			case "X":
				p = new XORGate(point, true);
				break;
			default:
				return;
		}
		parts.add(p);
	}
}
