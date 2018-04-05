package schach.domain.game;

import schach.domain.pieces.AbstractPiece;

public class Square {

	private byte vertical; // 1-8
	private byte horizontal; // A-E

	public byte getRow() {
		return vertical;
	}

	public byte getColumn() {
		return horizontal;
	}
	
	public AbstractPiece getPiece(){
		return null;
	}
	
	public boolean isEmpty(){
		return false;
	}

}
