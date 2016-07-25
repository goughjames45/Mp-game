package Game;

import java.awt.Color;
import java.awt.Graphics;

public class EntityOtherPlayer extends Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 102L;
	
	public EntityOtherPlayer() {
		r = 20;
	}
	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(x - r, y - r, 2* r, 2*r);
	}
	
}
