package schach.data.network;

public class OwnOpenGame {
	
	private final String name;
	private final String uid;
	private final String password;
	
	public OwnOpenGame(String name, String uid) {
		this.name = name;
		this.uid = uid;
		password = null;
	}

	public OwnOpenGame(String name, String password, String uid) {
		this.name = name;
		this.uid = uid;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean isPublic(){
		return password == null ? true : false;
	}
	public String getUID() {
		return uid;
	}
	

}
