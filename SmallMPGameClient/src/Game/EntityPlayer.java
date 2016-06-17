package Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class EntityPlayer extends Entity{

	Input input;
	
	
	public EntityPlayer(Input input){
		this.input = input;
		r = 60;
		x = 100;
		y = 100;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, r/2, r/2);
	}
	
	@Override
	public void tick() {
		vx = 0;
		vy = 0;
		if(input.isKeyDown(KeyEvent.VK_W)){
			vy -= 5;
		}
		if(input.isKeyDown(KeyEvent.VK_S)){
			vy += 5;
		}
		if(input.isKeyDown(KeyEvent.VK_A)){
			vx -= 5;
		}
		if(input.isKeyDown(KeyEvent.VK_D)){
			vx += 5;
		}
		
		super.tick();
	}	
}
