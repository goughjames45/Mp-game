package Game;

import java.awt.Color;
import java.awt.Graphics;

public class EntityEnemy extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 104L;
	
	
	public EntityEnemy(int x, int y){
		r = 20;
		this.x = x;
		this.y = y;
	}
	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval(x - r, y - r, r*2, r*2);
		g.setColor(Color.RED);
		g.drawLine(x - r, y - r, x + r, y + r);
		g.drawLine(x - r, y + r, x + r, y -r);
	}
}
