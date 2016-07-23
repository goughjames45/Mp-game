package Game;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Packets.PacketUpdateEntities;
import server.Server;

public class World {
	HashMap<Integer,Entity> entities = new HashMap<Integer,Entity>();
	EntityPlayer player;
	
	public World(EntityPlayer player){
		this.player = player;
	}
	
	public synchronized void render(Graphics g){
		Iterator<Entity> it = entities.values().iterator();
		while(it.hasNext()){
			it.next().draw(g);
		}
	}
	
	public synchronized void tick(){
		Iterator<Entity> it = entities.values().iterator();
		while(it.hasNext()){
			it.next().tick();
		}
	}
	
	public synchronized void addEntity(Entity e, int index){
		entities.put(index,e);
	}
	
	public synchronized void removeEntity(int index){
		entities.remove(index);
	}
	
	public synchronized void updateEntity(int index, int x, int y){
		entities.get(index).x = x;
		entities.get(index).y = y; 
	}
	
	public EntityPlayer getPlayer(){
		return player;
	}
	
	public HashMap<Integer, Entity> getEntities(){
		return entities;
	}
	
	
	public synchronized void onServerTick(Server server){
		
	}	
	
}
