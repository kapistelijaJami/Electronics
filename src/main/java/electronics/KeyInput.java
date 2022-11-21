package electronics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.event.MouseInputListener;

public class KeyInput implements MouseInputListener, MouseWheelListener, KeyListener {
	private Game game;
	
	public KeyInput(Game game) {
		this.game = game;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		game.mousePressed(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		game.mouseReleased(e);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		game.mouseDragged(e);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		game.mouseMoved(e);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		//game.MouseWheelMoved(e);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {                 
			case KeyEvent.VK_DELETE:
				game.deletePart();
				break;
			case KeyEvent.VK_P:
				game.createPart("P");
				break;
			case KeyEvent.VK_L:
				game.createPart("L");
				break;
			case KeyEvent.VK_S:
				game.createPart("S");
				break;
			case KeyEvent.VK_T:
				game.createPart("T");
				break;
			case KeyEvent.VK_B:
				game.createPart("B");
				break;
			case KeyEvent.VK_O:
				game.createPart("O");
				break;
			case KeyEvent.VK_A:
				game.createPart("A");
				break;
			case KeyEvent.VK_X:
				game.createPart("X");
				break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		/*int key = e.getKeyCode();
		switch (key) {
			case KeyEvent.VK_SPACE:
				
				break;
		}*/
	}
}
