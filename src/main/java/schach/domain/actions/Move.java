package schach.domain.actions;

import schach.domain.game.Board;
import schach.domain.game.Square;
import schach.domain.pieces.AbstractPiece;

public class Move {
	
	
	private final Square origin;
	private final Square destination;
	
	private final Board board;

		
	private ActionType type;
	
	private boolean hit_flag = false;
	private boolean enpassant_flag = false;
	private boolean promotion_flag = false;
	private char promotion_char = '\0';
	
	public Move(Board board, Square origin, Square destination){
		this.origin = origin;
		this.destination = destination;
		this.board = board;
	}
	
	private Move(Board board, Action [] actions){
		origin = null;
		destination = null;
		this.board = board;

	}
	
	
	public void perform(){
		
	}
	
	public void revert(){
		
	}


	public boolean isValid() {
		AbstractPiece piece = origin.getPiece();
		if(piece != null){
			type = piece.analyseMove(board, destination);
			if(type != ActionType.INVALID){
				return true;
			}
		}
		return false;
	}


	public String toChessNotation() { //NOT IMPLEMENTED
		return null;
	}

	public static Move fromChessNotation(String string, Board board){ //NOT IMPLEMENTED0
		return new Move(null, null);
	}
	
}
