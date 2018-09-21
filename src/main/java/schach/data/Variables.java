package schach.data;

import java.awt.Color;
import java.awt.Toolkit;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Variables {
	
	public final static int PROTOCOL_VERSION = 1;
	
	public final static Color DARK_COLOR = new Color(209, 125, 71);
	public final static Color LIGHT_COLOR = new Color(255, 206, 158);
	

	public final static int PORT = 12349;
	public final static long WAIT_TIME = 750;
	public final static long TIMEOUT = 10000;
	public final static long ANTI_TIMEOUT_INTERVAL = 1000;

	
	public final static int RSA_KEY_SIZE = 1024;
	public final static int RSA_PUBLIC_KEY_LENGTH = 162;
	public final static int AES_KEY_SIZE = 192;
	public final static int UID_SIZE = 8;
	


	public final static String CHECKSUM_ALGORITHM = "SHA-256";
	public final static int HASH_LENGTH = 256/8;
	
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
		//return 1.081581;
	}
	
	public final static int SQUARE_SIZE() {
		//System.out.println((int)(100*resolutionFactor()));
		return ((int)Math.ceil(100*resolutionFactor()));
	}
	
	public final static int adaptToResolution(int a) {
		//System.out.println(a/1.3);
		//return (int)(a*1.3);
		return (int) Math.ceil(a*resolutionFactor());
	}
	
	public final static char [] PIECE_CHARS = new char [] {'B', 'K', 'N', 'P', 'Q', 'R'};
	
	//https://stackoverflow.com/questions/29238644/how-do-you-get-hosts-broadcast-address-of-the-default-network-adapter-java
	public final static InetAddress calculateBraodcastAddress() throws Exception{
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
		    NetworkInterface networkInterface = interfaces.nextElement();
		    if (networkInterface.isLoopback()) {
		        continue;    
		    }
		    for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
		        InetAddress broadcast = interfaceAddress.getBroadcast();
		        if (broadcast == null) {
		            continue;
		        }
		        return broadcast;
		    }
		}
		return null;
	}

}
