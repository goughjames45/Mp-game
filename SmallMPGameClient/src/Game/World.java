package Game;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import Packets.PacketUpdateEntity;
import server.Server;

public class World {
	ArrayList<Entity> entities = new ArrayList<Entity>();
	EntityPlayer player;
	
	public World(EntityPlayer player){
		this.player = player;
	}
	
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
	
	public synchronized void addEntity(Entity e, int index){
		entities.add(e);
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
	
	public Entity[] getEntities(){
		Entity[] e = new Entity[entities.size()];
		entities.toArray(e);
		return e;
	}
	
	public synchronized void onServerTick(Server server){
		for(int i = 0; i < entities.size();i++){
			PacketUpdateEntity puppy = new PacketUpdateEntity(i,entities.get(i).getx(),entities.get(i).gety(),entities.get(i).getVelx(),entities.get(i).getVely());
			server.broadCastPacket(puppy);
		}
	}	
}
