package Packets;

import Game.Entity;
import Game.World;
import server.Server;

public class PacketNewEntity extends Packet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	Entity e;
	int index;
	
	public PacketNewEntity(Entity e, int index) {
		this.e = e;
		this.index = index;
	}
	
	@Override
	public void onServer(Server server) {		

	}

	@Override
	public void onClient(World world) {
		world.addEntity(e,index);
	}

}
