package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;

import Game.Entity;
import Game.World;
import Packets.Packet;
import Packets.PacketNewEntity;
import Packets.PacketRemoveEntity;
import Packets.PacketUpdatePlayerLocation;

public class Server {

	int portNumber = 25566;
	HashMap<Integer, ClientInstance> clientList;
	HashSet<Integer> usedIDs = new HashSet<Integer>();
	public World gameWorld;
	boolean isRunning = true;

	public Server() {
		gameWorld = new World(null,this);
		clientList = new HashMap<Integer, ClientInstance>();
		Game game = new Game(this);
		Thread gameThread = new Thread(game);
		gameThread.start();

		try {
			ServerSocket ss = new ServerSocket(portNumber);

			while (!ss.isClosed() && isRunning) {
				System.out.println("wathing for client");
				Socket sock = ss.accept();
				System.out.println("connected");
				ClientInstance ci = new ClientInstance(sock, this, genClientID(), gameWorld);
				synchronized (clientList) {
					clientList.put(ci.mycID, ci);
				}
				Thread t = new Thread(ci);
				t.start();
			}
			synchronized (clientList) {
				for(ClientInstance ci : clientList.values()){
					ci.dissconectClient();
				}
			}
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			isRunning = false;
		}
	}

	public World getWorld() {
		return gameWorld;
	}

	public void broadCastPacket(Packet p) {
		synchronized (clientList) {
			for (Integer i : clientList.keySet()) {
				clientList.get(i).sendPacket(p);
			}
		}
	}

	public void broadCastPacketToAllBut(Packet p, int cid) {
		synchronized (clientList) {
			for (Integer i : clientList.keySet()) {
				if (i != cid) {
					clientList.get(i).sendPacket(p);
				}
			}
		}
	}

	public static void main(String[] args) {
		new Server();
	}

	public void removeClient(ClientInstance ci) {
		synchronized (clientList) {
			clientList.remove(ci.mycID);
		}
		synchronized (usedIDs) {
			usedIDs.remove(ci.mycID);
		}
		despawnEntity(ci.mycID);
	}

	public void removeClient(int cID) {
		synchronized (clientList) {
			clientList.remove(cID);
		}
		synchronized (usedIDs) {
			usedIDs.remove(cID);
		}
		despawnEntity(cID);
	}

	public ClientInstance getClientInstance(int cID) {
		synchronized (clientList) {
			return clientList.get(cID);
		}
	}

	public int genClientID() {
		int id = 0;
		synchronized (usedIDs) {
			while (usedIDs.contains(id)) {
				id++;
			}
			usedIDs.add(id);
		}
		return id;
	}
	
	public void despawnEntity(int id){
		gameWorld.removeEntity(id);
		
	}
	
	public void spawnEntity(Entity e){
		gameWorld.addEntity(e);
	}
	
}
