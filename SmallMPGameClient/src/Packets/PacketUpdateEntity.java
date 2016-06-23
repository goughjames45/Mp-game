package Packets;

import Game.World;
import server.Server;

public class PacketUpdateEntity extends Packet{

	int id;
	int vx,vy,x,y;
	
	public PacketUpdateEntity(int id,int x, int y,int vx,int vy) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
	}
	
	
	@Override
	public void onServer(Server server) {
		
		
	}

	@Override
	public void onClient(World world) {
		world.getEntities()[id].setLocation(x, y);
		world.getEntities()[id].setVelocity(vx, vy);		
	}
}
