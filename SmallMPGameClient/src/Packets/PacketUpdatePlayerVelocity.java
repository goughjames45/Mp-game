package Packets;

import Game.World;
import server.Server;

public class PacketUpdatePlayerVelocity extends Packet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	int vx,vy, cID;
	
	
	public PacketUpdatePlayerVelocity(int vx, int vy, int cID) {
		
		this.vx = vx;
		this.vy = vy;
		this.cID = cID;
	}
	
	@Override
	public void onServer(Server server) {	
		server.gameWorld.getEntities()[cID].setVelocity(vx, vy);
	}

	@Override
	public void onClient(World world) {		
		world.getPlayer().setVelocity(vx, vy);
	}
}
