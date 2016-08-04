package Packets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import Game.Entity;
import Game.World;
import server.Server;

public class PacketUpdateEntities extends Packet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6L;
	ArrayList<EntityData> entityData = new ArrayList<EntityData>();

	
	
	public void addEntityData(int id,int x,int  y, int vx, int vy){
		EntityData data = new EntityData();
		data.x = x;
		data.y = y;
		data.vx = vx;
		data.vy = vy;
		data.id = id;
		entityData.add(data);
	}
	
	@Override
	public void onServer(Server server, int cID) {
		// Client sends this to request update form server
		HashMap<Integer, Entity> entities =  null;
		synchronized(server.gameWorld){
			entities = (HashMap<Integer, Entity>) server.gameWorld.getEntities().clone();
		}
		PacketUpdateEntities pue = new PacketUpdateEntities();
		for(Integer i : entities.keySet()){	
			Entity e = entities.get(i);
			pue.addEntityData(i,e.getx(), e.gety(), e.getVelx(), e.getVely());
		}
		server.getClientInstance(cID).sendPacket(pue);
	}

	@Override
	public void onClient(World world) {
		synchronized (world) {	
			HashMap<Integer, Entity> entities = world.getEntities();
			for(EntityData ed : entityData){
				Entity  e = entities.get(ed.id);
				if(e!= null){
					e.setLocation(ed.x,ed.y);
					e.setVelocity(ed.vx,ed.vy);
				}
			}
		}
	}

	private class EntityData implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1000L;
		int id;
		int vx, vy, x, y;
	}
}
