package Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;

import javax.swing.JFrame;

public class Display extends JFrame {

	public final static int WIDTH = 640;
	public final static int HEIGHT = 480;

	BufferedImage image;
	Graphics imageGraphics;
	World world;

	public Display(World world) {
		super("Game");		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		imageGraphics = image.getGraphics();
		this.world = world;
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	public void paint(Graphics g) {
		imageGraphics.clearRect(0, 0, WIDTH, HEIGHT);		
		world.render(imageGraphics);
		g.drawImage(image, 0, 0,this);
	}
}
