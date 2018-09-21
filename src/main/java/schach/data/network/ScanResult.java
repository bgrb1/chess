package schach.data.network;

import java.net.InetAddress;

public class ScanResult {
	
	private String name;
	private  boolean isPublic;
	private InetAddress address;
	private String uid;
		
	public ScanResult(String name, String uid, boolean isPublic, InetAddress address) {
		this.name = name;
		this.uid = uid;
		this.isPublic = isPublic;
		this.address = address;
	}
	
	public String getName() { return name; }
	public String getUID() { return uid; }
	public boolean isPublic() { return isPublic; }
	public InetAddress getAddress() { return address; }

}
