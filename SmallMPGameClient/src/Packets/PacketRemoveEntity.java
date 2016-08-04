package Packets;

import Game.World;
import server.Server;

public class PacketRemoveEntity extends Packet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	int id;
	public PacketRemoveEntity(int id) {
		this.id = id;
	}
	
	@Override
	public void onServer(Server server, int cID) {
		

	}

	@Override
	public void onClient(World world) {		
		world.removeEntity(id);
	}

}
