package chess.model;

import java.util.ArrayList;
import java.util.List;

import chess.logic.handlers.Handler;
import chess.model.pieces.AbstractPiece;
import chess.model.pieces.King;

public class Player {
	
	private int id = 1;
	private Handler handler;
	private King king;
	
	public Player(int id){
		this.id = id;
	}
	
	private ArrayList<AbstractPiece> pieces  = new ArrayList<AbstractPiece>();
	
	public List<AbstractPiece> getPieces(boolean includeCaptured){
		if(!includeCaptured) {
			List<AbstractPiece> buffer = new ArrayList<AbstractPiece>();
			for(AbstractPiece piece : pieces) {
				if(!piece.isCaptured()) {
					buffer.add(piece);
				}
			}
			return buffer;
		} else {
			return pieces;
		}
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

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public void ping(){
		handler.ping(this);
	}
	public King getKing() {
		return king;
	}
	
	@SuppressWarnings("unchecked")
	public List<Move> getAllLegalMoves(Board board, boolean includePseudoLegal){
		List<Move> moves  = new ArrayList<Move>();
		for(AbstractPiece piece : ((ArrayList<AbstractPiece>) pieces.clone())) {
			if(!piece.isCaptured()) {
				List<Move> list  = piece.getAllLegalMoves(board, includePseudoLegal);
				moves.addAll(list);
			}
		}
		return moves;
	}

}