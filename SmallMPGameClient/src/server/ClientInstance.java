package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import Game.Entity;
import Game.EntityOtherPlayer;
import Game.EntityPlayer;
import Game.World;
import Packets.Packet;
import Packets.PacketNewEntity;
import Packets.PacketSendEntitiesAndID;

public class ClientInstance implements Runnable {

	ObjectOutputStream oos;
	ObjectInputStream ois;

	Socket mySocket;

	int mycID;
	int x, y, vx, vy;
	World world;
	Server server;
	boolean connected = true;

	public ClientInstance(Socket socket, Server server, int cID, World world) {
		mySocket = socket;
		mycID = cID;
		this.world = world;
		this.server = server;
		try {
			initClient();
		} catch (IOException e) {
			e.printStackTrace();
			dissconectClient();
		}
	}

	public void sendPacket(Packet p) {
		try {
			// System.out.println("sending packed to client: " + mycID + " P: "
			// + p.toString());
			oos.writeObject(p);
		} catch (IOException e) {
			e.printStackTrace();
			dissconectClient();
		}
	}

	long startTime = 0;
	long endTime = 0;

	@Override
	public void run() {
		try {
			while (mySocket.isConnected() && !mySocket.isClosed() && connected) {
				startTime = System.currentTimeMillis();
				handlePackets();
				endTime = System.currentTimeMillis();
				double mspf = (1f / Game.TARGET_FPS) * 1000;
				if (endTime - startTime < mspf) {
					try {
						Thread.sleep((long) (mspf - (endTime - startTime)));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} finally {
			try {
				oos.close();
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void initClient() throws IOException {
		System.out.println("started server thread for client");
		oos = new ObjectOutputStream(mySocket.getOutputStream());
		ois = new ObjectInputStream(mySocket.getInputStream());
		// send all the entities
		HashMap<Integer, Entity> entities = null;
		entities = (HashMap<Integer, Entity>) world.getEntities().clone();
		/*
		 * for(Integer i : entities.keySet()){ Entity e = entities.get(i); if(e
		 * instanceof EntityPlayer){
		 * 
		 * } }
		 */

		PacketSendEntitiesAndID pseai = new PacketSendEntitiesAndID(entities, mycID);
		oos.writeObject(pseai);
		server.gameWorld.addEntity(new EntityPlayer(null, null), mycID);
		// Broadcast that an entity has been added
		EntityOtherPlayer eop = new EntityOtherPlayer();
		PacketNewEntity pne = new PacketNewEntity(eop, mycID);
		server.broadCastPacketToAllBut(pne, mycID);
	}

	private void handlePackets() throws IOException, ClassNotFoundException {
		Packet p = (Packet) ois.readObject();
		// System.out.println("reading client packets" + p.toString());
		p.onServer(server, mycID);
	}

	public void dissconectClient() {
		try {
			oos.close();
			ois.close();
			mySocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connected = false;
		server.removeClient(this);
	}
}
