package Packets;

import Game.World;
import server.Server;

public class PacketDissconnect extends Packet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	@Override
	public void onServer(Server server, int cID) {
		System.out.println("Dissconecting Client: " + cID);
		server.getClientInstance(cID).dissconectClient();
	}

	@Override
	public void onClient(World world) {		
		
	}

}
