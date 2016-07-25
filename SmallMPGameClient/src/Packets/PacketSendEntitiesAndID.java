package Packets;

import java.util.HashMap;

import Game.Entity;
import Game.EntityPlayer;
import Game.World;
import server.Server;

public class PacketSendEntitiesAndID extends Packet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;

	HashMap<Integer, Entity> entities;
	int cID;

	public PacketSendEntitiesAndID(HashMap<Integer, Entity> es, int cID) {
		entities = es;
		this.cID = cID;
	}

	@Override
	public void onServer(Server server, int cID) {

	}

	@Override
	public void onClient(World world) {
		for(Integer i : entities.keySet()){
			Entity  e = entities.get(i);
			if(e instanceof EntityPlayer){
				e = ((EntityPlayer) e).toOtherPlayer();
			}
			world.addEntity(e, i);
		}
		world.getPlayer().setID(cID);
		world.addEntity(world.getPlayer(), cID);
	}
}
