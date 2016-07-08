package Packets;

import Game.Entity;
import Game.EntityPlayer;
import Game.World;
import server.Server;

public class PacketSendEntitiesAndID extends Packet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	Entity[] entities;
	int cID;

	public PacketSendEntitiesAndID(Entity[] es, int cID) {
		entities = es;
		this.cID = cID;
	}

	@Override
	public void onServer(Server server) {

	}

	@Override
	public void onClient(World world) {
		for (int i = 0; i < entities.length; i++) {
			world.addEntity(entities[i], i);
		}
		world.getPlayer().setID(cID);
		world.addEntity(world.getPlayer(), cID);
	}
}
