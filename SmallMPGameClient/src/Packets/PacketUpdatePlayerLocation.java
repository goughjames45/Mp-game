package Packets;

import Game.World;
import server.Server;

public class PacketUpdatePlayerLocation extends Packet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	int x,y,vx,vy, cID;
	
	
	public PacketUpdatePlayerLocation(int x, int y, int vx, int vy, int cID) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.cID = cID;
	}
	
	@Override
	public void onServer(Server server, int cID) {	
		server.gameWorld.getEntities()[cID].setVelocity(vx, vy);
		server.gameWorld.getEntities()[cID].setLocation(x, y);
		
	}

	@Override
	public void onClient(World world) {		
		world.getPlayer().setLocation(x, y);
		world.getPlayer().setVelocity(vx, vy);
	}
}
