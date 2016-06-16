package Packets;

import Game.Entity;
import Game.World;

public class NewEntity extends Packet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	Entity e;
	
	public NewEntity(Entity e) {
		this.e = e;
	}
	
	@Override
	public void onServer() {		

	}

	@Override
	public void onClient(World world) {
		world.addEntity(e);
	}

}
