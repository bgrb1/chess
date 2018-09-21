package schach.domain.game;

import java.util.LinkedList;

import schach.application.handler.Handler;
import schach.domain.pieces.AbstractPiece;
import schach.domain.pieces.King;

public class Player {
	
	private int id = 1;
	private Handler handler;
	private King king;
	
	public Player(int id){
		this.id = id;
	}
	
	private LinkedList<AbstractPiece> pieces  = new LinkedList<AbstractPiece>();
	
	public LinkedList<AbstractPiece> getPieces(){
		return pieces;
	}
	
	@Override 
	public boolean equals(Object o){
		if(o instanceof Player){
			Player p2 = (Player) o;
			if(p2.getID() == this.getID()){
				return true;
			} 
		} 
		return false;
	}
	
	public int getID(){
		return id;
	}
	
	public void registerPiece(AbstractPiece piece){
		pieces.add(piece);
		if(piece instanceof King) {
			king = (King) piece;
		}
	}
	public void unregisterPiece(AbstractPiece piece){
		pieces.remove(piece);
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public void pingHandler(){
		handler.ping(this);
	}
	public King getKing() {
		return king;
	}

}