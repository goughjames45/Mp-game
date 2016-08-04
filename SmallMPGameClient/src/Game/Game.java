package Game;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import Packets.Packet;
import Packets.PacketDissconnect;
import Packets.PacketUpdateEntities;
import Packets.PacketUpdatePlayerVelocity;

public class Game implements Runnable{

	public static final int TARGET_FPS = 60;

	Display display;
	World world;
	Input input;
	EntityPlayer player;
	Socket server;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	public static boolean running = true;

	public Game() {
		
		init();
		loop();

		/*
		// closes the streams
		if (ois != null) {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (oos != null) {
			try {
				oos.close();
			} catch (IOException e) {
				//System.exit(-1);
				e.printStackTrace();
			}
		}
		*/
	}
	int count = 0;
	private void init() {
		count++;
		try {
			String host = (String) JOptionPane.showInputDialog("Enter host");
			int port = Integer.parseInt(
					(String) JOptionPane.showInputDialog("Enter port number"));
			server = new Socket(host, port);
			ois = new ObjectInputStream(server.getInputStream());
			oos = new ObjectOutputStream(server.getOutputStream());
			input = new Input();
			player = new EntityPlayer(input, oos);
			world = new World(player,null);
			display = new Display(world);
			display.addKeyListener(input);
			input.setDisplay(display);
			Thread packets = new Thread(this);
			packets.start();
			Thread playerControl = new Thread(player);
			playerControl.start();			
		} catch (Exception e) {
			e.printStackTrace();
			if(count < 4){
				init();
			}
		}
		
	}

	private void loop() {
		long startTime = 0L;
		long endTime = 0L;
		while (display.isVisible() && running) {
			startTime = System.currentTimeMillis();
			display.repaint();
			endTime = System.currentTimeMillis();
			double mspf = (1f / TARGET_FPS) * 1000;
			if (endTime - startTime < mspf) {
				try {
			//		System.out.println(mspf - (endTime - startTime));
					Thread.sleep((long) (mspf - (endTime - startTime)));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		running = false;
		display.dispose();
		try{
			System.out.println("sending dissconect packet");
			PacketDissconnect dis = new PacketDissconnect();
			oos.writeObject(dis);
		} catch (IOException e) {
			e.printStackTrace();			
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	private void proccesPacket() {
		Packet p;
		try {
			PacketUpdateEntities pue = new PacketUpdateEntities();
			player.sendPacket(pue);
			p = (Packet) ois.readObject();
			p.onClient(world);
			//System.out.println("receving packed from server: "+player.cID + " P: "
			//		+ p.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Game.running = false;
		} catch (IOException e) {
			e.printStackTrace();
			Game.running = false;
		}
	}

	@Override
	public void run() {
		while(running){
			proccesPacket();
		}
	}
}
