package chess.model.pieces;

import java.util.ArrayList;

import chess.model.Board;
import chess.model.Move;
import chess.model.Player;
import chess.model.Square;

public abstract class AbstractPiece {
	
	public abstract char toChar(); 

	public abstract boolean canReach(Board board, Square destination);
	public abstract Square [] getPath(Board board, Square destination);
	public abstract ArrayList<Move> getAllLegalMoves(Board board, boolean includePseudoLegal);

	
	protected Square position;

	private final Player player;
	private boolean captured = false;
	
	public AbstractPiece(Player player, Square position) {
		this.position = position;
		this.player = player;	
		player.registerPiece(this);
		position.setPiece(this);
	}

	public void move(Square destination){ 
		position.clear();
		destination.setPiece(this);
		position = destination;
	}
	public void moveBack(Square origin){ 
		position.clear();
		origin.setPiece(this);
		position = origin;
	}

	public final Player getPlayer() {
		return player;
	}
	
	public void capture(Board board){ 
		captured = true;
		position.clear();

	}
	public void restore(Board board, Square location){ 
		captured = false;
		position = location;
		location.setPiece(this);
	}
	
	public boolean isCaptured() {
		return captured;
	}
	
	public Square getPosition(){
		return position;
	}
	
	public String getIconKey(){
		if(player.getID() == 1){
			return toChar() + "w";
		} else {
			return toChar() + "b";
		}
	}
	
	protected boolean isValidSquare(int column, int row) {
		if(column > 8 || row > 8 || column < 1 || row < 1) {
			return false;
		} 
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		AbstractPiece piece = (AbstractPiece) o;
		if(piece.getPosition().equals(getPosition()) && o.getClass().equals(getClass())) {
			return true;
		}
		return false;
	}
	
	

}
