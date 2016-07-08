package Game;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener{

	boolean[] keys = new boolean[256];
	
	
	@Override
	public void keyPressed(KeyEvent e) {	
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;		
	}
	
	public boolean isKeyDown(int keyCode){
		return keys[keyCode];
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
}
