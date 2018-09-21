package schach.domain.pieces;

import java.util.ArrayList;

import schach.domain.game.Board;
import schach.domain.game.Move;
import schach.domain.game.Player;
import schach.domain.game.Square;

public abstract class AbstractPiece {
	
	public abstract char toChar(); 

	public abstract boolean canReach(Board board, Square destination); //Nein
	public abstract Square [] getPath(Board board, Square destination);
	public abstract ArrayList<Move> getAllValidMoves(Board board);
	
	protected Square position;

	private Player player;
	
	public AbstractPiece(Player player, Square position) {
		this.position = position;
		this.player = player;	
		player.registerPiece(this);
		position.setPieceHere(this);
	}
	
	public final String toChessNotation(){ // Gibt die Figur mit Position in Schachnotation zurück 
		return null;
	}
	
	public void move(Square destination){ 
		this.position = destination;
	}
	public void moveBackTo(Square destination){ 
		this.position = destination;
	}

	public final Player getPlayer() {
		return player;
	}
	
	public final void capture(){ 
		player.unregisterPiece(this);
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
	
	

}
