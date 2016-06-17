package Packets;

import Game.World;

public class PacketUpdatePlayer extends Packet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	int x,y,vx,vy, cID;
	
	
	public PacketUpdatePlayer(int x, int y, int vx, int vy, int cID) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
	}
	
	@Override
	public void onServer() {		
	}

	@Override
	public void onClient(World world) {
		world.getPlayer().setLocation(x, y);
		world.getPlayer().setVelocity(vx, vy);
	}
}
