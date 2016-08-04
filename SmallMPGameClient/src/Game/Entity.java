package Game;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Entity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 100L;
	int x,y,r,vx,vy;
	int px,py;
	private boolean collider = true;
	int id = -1;
	boolean toBeRemoved = false;
	
	public void draw(Graphics g){
		g.setColor(Color.BLUE);
		g.drawString("Basic Entity", x, y);
	}
	
	public boolean tick(World world){
		px = x;
		py = y;
		x += vx;
		y += vy;	
		return (vx != 0 || vy != 0);
	}
	
	public void unTick(){
		x = px;
		y = py;
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;	
	}
	public void setVelocity(int vx, int vy){
		this.vx = vx;
		this.vy = vy;	
	}
	public int getx(){
		return x;
	}
	public int gety(){
		return y;
	}
	public int getVelx(){
		return vx;
	}
	public int getVely(){
		return vy;
	}
	
	public boolean checkCollision(Entity otherEntity){
		double dist = Math.sqrt( Math.pow(x - otherEntity.x, 2) + Math.pow(y - otherEntity.y, 2));
		return (dist < r + otherEntity.r);		
	}
	
	public boolean isCollider(){
		return collider;
	}
	
	public void setCollidable(boolean collidable){
		collider = collidable;
	}
	
	public void setID(int id){
		this.id = id;
	}
}
