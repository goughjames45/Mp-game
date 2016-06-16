package Game;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import Packets.Packet;

public class Game {

	public final int TARGET_FPS = 60;

	Display display;
	World world;
	Input input;
	Socket server;
	ObjectInputStream ois;
	ObjectOutputStream oos;

	public Game() {
		world = new World();
		display = new Display(world);
		input = new Input();
		display.addKeyListener(input);
		init();
		loop();

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
				e.printStackTrace();
			}
		}
	}

	private void init() {
		try {
			world.addEntity(new EntityPlayer(input));
			String host = (String) JOptionPane.showInputDialog("Enter host");
			int port = Integer.parseInt(
					(String) JOptionPane.showInputDialog("Enter port number"));
			server = new Socket(host, port);
			ois = new ObjectInputStream(server.getInputStream());
			oos = new ObjectOutputStream(server.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
			init();
		}
	}

	private void loop() {
		long startTime = 0L;
		long endTime = 0L;
		while (display.isVisible()) {
			startTime = System.currentTimeMillis();
			proccesPacket();
			display.repaint();
			endTime = System.currentTimeMillis();
			double mspf = (1f / TARGET_FPS) * 1000;
			if (endTime - startTime < mspf) {
				try {
					Thread.sleep((long) (mspf - (endTime - startTime)));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private void proccesPacket(){
		Packet p;
		try {
			p = (Packet) ois.readObject();
			p.onClient(world);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
