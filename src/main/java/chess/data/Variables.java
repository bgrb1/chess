package chess.data;

import java.awt.Color;
import java.awt.Toolkit;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Variables {

	public final static Color DARK_COLOR = new Color(190, 125, 71);
	public final static Color LIGHT_COLOR = new Color(255, 206, 158);
	//public final static Color DARK_COLOR = new Color(184, 134, 100);
	//public final static Color LIGHT_COLOR = new Color(240, 218, 181);
	
	public final static int ITERATIVE_SEARCH_TIME = 4000;

	
	private final static double SIZE_FACTOR = 1.4;
	private static boolean calculatedBefore = false;
	private static double factor = 0;
	public final static double resolutionFactor() {			
		if(!calculatedBefore) {
			double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			factor =  height/1440;
			factor = factor*SIZE_FACTOR;
			calculatedBefore = true;
		}
		return factor;
	}
	
	public final static int SQUARE_SIZE() {
		return ((int)Math.ceil(100*resolutionFactor()));
	}
	
	public final static int adaptToResolution(int a) {
		return (int) Math.ceil(a*resolutionFactor());
	}
	
	public final static char [] PIECE_CHARS = new char [] {'B', 'K', 'N', 'P', 'Q', 'R'};
	


}
