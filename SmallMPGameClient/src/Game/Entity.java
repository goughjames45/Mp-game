package Game;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Entity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;
	int x,y,r,vx,vy;
	
	public void draw(Graphics g){
		g.setColor(Color.BLUE);
		g.drawString("Basic Entity", x, y);
	}
	
	public void tick(){
		x += vx;
		y += vy;
	}
	
}
