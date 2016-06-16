package Game;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class World {
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public synchronized void render(Graphics g){
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()){
			it.next().draw(g);
		}
	}
	
	public synchronized void tick(){
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()){
			it.next().tick();
		}
	}
	
	public synchronized void addEntity(Entity e){
		entities.add(e);
	}
	
	public synchronized void removeEntity(Entity e){
		entities.remove(e);
	}
	
	public synchronized void updateEntity(int index, int x, int y){
		entities.get(index).x = x;
		entities.get(index).y = y; 
	}
}
