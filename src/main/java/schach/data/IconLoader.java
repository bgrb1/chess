package schach.data;

import java.awt.image.BufferedImage;
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
			stream = loader.getResourceAsStream("resources/pieces/" + c+"w.png");
			icons.put(c+"w", ImageIO.read(stream));
			stream = loader.getResourceAsStream("resources/pieces/" + c+"b.png");
			icons.put(c+"b", ImageIO.read(stream));

		}
		if(icons.size() != 12){
			System.err.println("Fatal Error at loading icons");
			//Error Handling
		}
	}
	
	public static BufferedImage getSoundIcon(boolean on) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream stream = null;
		if(on) {
			stream = loader.getResourceAsStream("resources/son.png");
		} else {
			stream = loader.getResourceAsStream("resources/soff.png");
		}
		try {
			return ImageIO.read(stream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	
	}
}
