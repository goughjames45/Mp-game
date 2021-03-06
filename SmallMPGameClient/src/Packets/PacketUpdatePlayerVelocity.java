package Packets;

import Game.World;
import server.Server;

public class PacketUpdatePlayerVelocity extends Packet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8L;

	int vx,vy, cID;
	
	
	public PacketUpdatePlayerVelocity(int vx, int vy, int cID) {
		
		this.vx = vx;
		this.vy = vy;
		this.cID = cID;
	}
	
	@Override
	public void onServer(Server server, int cID) {
		synchronized (server) {
			server.gameWorld.getEntities().get(cID).setVelocity(vx, vy);
		}
	}

	@Override
	public void onClient(World world) {		
		world.getPlayer().setVelocity(vx, vy);
	}
}
