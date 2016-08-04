package server;

import Game.Display;
import Game.Entity;
import Game.EntityEnemy;

public class Game implements Runnable {
	public static final int TARGET_FPS = 60;

	Server server;

	public Game(Server server) {
		this.server = server;
	}

	long startTime = 0;
	long endTime = 0;

	@Override
	public void run() {
		while (server.isRunning) {
			startTime = System.currentTimeMillis();
			loop();
			endTime = System.currentTimeMillis();
			double mspf = (1f / TARGET_FPS) * 1000;
			if (endTime - startTime < mspf) {
				try {
				//	System.out.println(
				//			"server delay: " + (mspf - (endTime - startTime)) +" m:"+ mspf);
					Thread.sleep((long) (mspf - (endTime - startTime)));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	void loop() {
		server.gameWorld.tick();
		server.gameWorld.onServerTick(server);
		spawnEnemies();
	}
	
	void spawnEnemies(){
		if(Math.random()*600 <= 5){
			int x = (int) (Math.random()*Display.WIDTH);
			int y = (int) (Math.random()*Display.HEIGHT);
			server.spawnEntity(new EntityEnemy(x,y));
		}
	}
}
