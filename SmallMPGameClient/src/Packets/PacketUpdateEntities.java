package Packets;

import java.io.Serializable;
import java.util.ArrayList;

import Game.Entity;
import Game.World;
import server.Server;

public class PacketUpdateEntities extends Packet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;
	ArrayList<EntityData> entityData = new ArrayList<EntityData>();

	
	
	public void addEntityData(int x,int  y, int vx, int vy){
		EntityData data = new EntityData();
		data.x = x;
		data.y = y;
		data.vx = vx;
		data.vy = vy;
		entityData.add(data);
	}
	
	@Override
	public void onServer(Server server, int cID) {
		Entity[] entities = server.gameWorld.getEntities();
		PacketUpdateEntities pue = new PacketUpdateEntities();
		for(int i =0;i<entities.length;i++){			
			pue.addEntityData(entities[i].getx(), entities[i].gety(), entities[i].getVelx(), entities[i].getVely());
		}
		server.getClientInstance(cID).sendPacket(pue);
	}

	@Override
	public void onClient(World world) {
		Entity[] entities = world.getEntities();
		for(int i =0;i<entities.length;i++){		
			entities[i].setLocation(entityData.get(i).x,entityData.get(i).y);
			entities[i].setVelocity(entityData.get(i).vx,entityData.get(i).vy);
		}
	}

	private class EntityData implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 100L;
		int id;
		int vx, vy, x, y;
	}
}