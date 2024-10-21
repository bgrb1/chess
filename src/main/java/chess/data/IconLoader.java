package chess.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class IconLoader {
	
	private final HashMap<String, BufferedImage> icons = new HashMap<String, BufferedImage>();
	
	public IconLoader() throws Exception{
		initialize();
	}
	
	public BufferedImage getImage(String key){
		return icons.get(key);
	}

	private void initialize() throws Exception {
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream stream = null;
		for(char c : Variables.PIECE_CHARS) {
			File file = new File("resources/pieces/" + c+"w.png");
			icons.put(c+"w", ImageIO.read(file));
			file = new File("resources/pieces/" + c+"b.png");
			icons.put(c+"b", ImageIO.read(file));

		}
		if(icons.size() != 12){
			System.err.println("Fatal Error at loading icons");
			//Error Handling
		}
	}
	
	public static BufferedImage getSoundIcon(boolean on) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		File file = null;
		if(on) {
			file = new File("resources/son.png");
		} else {
			file = new File("resources/soff.png");
		}
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	
	}
}
