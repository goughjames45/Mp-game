package Game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import Packets.PacketNewEntity;
import Packets.PacketRemoveEntity;
import Packets.PacketUpdateEntities;
import server.Server;

public class World {
	HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();
	EntityPlayer player;
	Server server;

	public World(EntityPlayer player, Server server) {
		this.player = player;
		this.server = server;
	}

	public synchronized void render(Graphics g) {
		Iterator<Entity> it = entities.values().iterator();
		while (it.hasNext()) {
			it.next().draw(g);
		}
	}

	public void tick() {
		Iterator<Entity> it = ((HashMap<Integer, Entity>) entities.clone()).values().iterator();
		while (it.hasNext()) {
			Entity e = it.next();
			boolean dTick = e.tick(this);
			if (e.isCollider() && dTick && checkCollision(e)) {
				e.unTick();
			}
		}
	}

	public synchronized void addEntity(Entity e, int id) {
		e.setID(id);
		if (server != null) {
			PacketNewEntity pne = new PacketNewEntity(e, id);
			server.broadCastPacket(pne);
		}
		entities.put(id, e);
	}

	public synchronized int addEntity(Entity e) {
		int id = 0;
		while (entities.containsKey(id)) {
			id++;
		}
		e.setID(id);
		if (server != null) {
			PacketNewEntity pne = new PacketNewEntity(e, id);
			server.broadCastPacket(pne);
		}
		entities.put(id, e);
		return id;
	}

	public synchronized void removeEntity(int index) {
		Entity e = entities.get(index);
		if (server != null) {
			PacketRemoveEntity pre = new PacketRemoveEntity(e.id);
			server.broadCastPacket(pre);
		}
		entities.remove(index);
	}

	public synchronized void updateEntity(int index, int x, int y) {
		entities.get(index).x = x;
		entities.get(index).y = y;
	}

	public EntityPlayer getPlayer() {
		return player;
	}

	public HashMap<Integer, Entity> getEntities() {
		return entities;
	}

	public synchronized boolean checkCollision(Entity e) {
		boolean collision = false;
		Iterator<Entity> i = entities.values().iterator();
		while (i.hasNext() && !collision) {
			Entity otherEntity = i.next();
			if (e.checkCollision(otherEntity) && e != otherEntity) {
				collision = true;
			}
		}
		return collision;
	}

	public synchronized List<Entity> getColliders(Entity e) {
		List<Entity> col = new ArrayList<Entity>();
		for (Entity otherEntity : entities.values()) {
			if (e.checkCollision(otherEntity) && e != otherEntity) {
				col.add(otherEntity);
			}
		}
		return col;
	}

	public synchronized void onServerTick(Server server) {

	}

}
