package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class EntityBullet extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 101L;
	
	int life;
	
	
	public EntityBullet(int x, int y,int vx, int vy, int r) {
		this.r = r;
		this.vx = vx;
		this.vy = vy;
		this.x = x;
		this.y = y;
		life = 20000;
		setCollidable(false);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(x, y, r / 2, r / 2);		
	}
	
	@Override
	public boolean tick(World world) {
		life--;
		List<Entity> entities = world.getColliders(this);
		for(Entity e : entities){
			if(e instanceof EntityEnemy){
				world.removeEntity(e.id);
			}
			life -= 100;
		}
		if(life <= 0){
			world.removeEntity(id);
		}
		return super.tick(world);
	}

}
