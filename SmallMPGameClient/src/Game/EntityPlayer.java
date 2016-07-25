package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;

import Packets.Packet;
import Packets.PacketNewEntity;
import Packets.PacketUpdateEntities;
import Packets.PacketUpdatePlayerVelocity;

public class EntityPlayer extends Entity implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 103L;
	Input input;
	ObjectOutputStream oos;
	int cID;
	int timeToShoot = 0;

	public EntityPlayer(Input input, ObjectOutputStream oos) {
		r = 20;
		x = 100;
		y = 100;
		this.input = input;
		this.oos = oos;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(x - r, y - r, 2* r, 2*r);
	}

	@Override
	public boolean tick(World world) {
		return super.tick(world);
	}

	int pvx = 0, pvy = 0;
	long startTime = 0;
	long endTime = 0;
	@Override
	public void run() {
		while (Game.running) {
			startTime = System.currentTimeMillis();
			handleInput();			
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
	}
	
	private void handleInput(){
		vx = 0;
		vy = 0;
		timeToShoot--;
		if (input.isKeyDown(KeyEvent.VK_W)) {
			vy -= 5;
		}
		if (input.isKeyDown(KeyEvent.VK_S)) {
			vy += 5;
		}
		if (input.isKeyDown(KeyEvent.VK_A)) {
			vx -= 5;
		}
		if (input.isKeyDown(KeyEvent.VK_D)) {
			vx += 5;
		}
		if (pvx != vx || pvy != vy) {
			PacketUpdatePlayerVelocity pup = new PacketUpdatePlayerVelocity(vx, vy, cID);			
			sendPacket(pup);
			pvx = vx;
			pvy = vy;			
		}
		if(input.isKeyDown(KeyEvent.VK_SPACE)){
			if(timeToShoot < 1){
				timeToShoot = 100;
				Point mouse = input.display.getMousePosition();
				if(mouse!= null){
					System.out.println("shoot");
					double hyp = Math.sqrt(Math.pow(x - mouse.getX(), 2) + Math.pow(y - mouse.getY(), 2));
					double dx = (mouse.getX() - (double)x)/hyp;
					double dy = (mouse.getY() - (double)y)/hyp;
					int br = 20;
					int bs = 30;
					int sx = (int) (x + (r + br) * dx);
					int sy = (int) (y + (r + br) * dy);					
					EntityBullet eb = new EntityBullet(sx,sy,(int)(bs * dx),(int)(bs * dy),br);
					PacketNewEntity pne = new PacketNewEntity(eb, 0);
					sendPacket(pne);
				}
			}
		}
	}

	public void setID(int cID) {
		this.cID = cID;
	}

	public EntityOtherPlayer toOtherPlayer() {
		EntityOtherPlayer eop = new EntityOtherPlayer();
		eop.r = r;
		eop.x = x;
		eop.y = y;
		eop.vy = vy;
		eop.vx = vx;
		return eop;
	}
	
	public synchronized void sendPacket(Packet packet){
		try{
			oos.writeObject(packet);
		}catch(IOException e){
			System.err.println("Failed to send packet " + packet.toString() + " to server!");
			e.printStackTrace();
		}
	}
}
