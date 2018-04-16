package schach.domain.actions;

import schach.domain.game.Board;
import schach.domain.game.Square;
import schach.domain.pieces.AbstractPiece;

public final class Move {
	
	private final Square origin;
	private final Square destination;
	
	private final Board board;

		
	private ActionType type;
	
	private GameAction [] actions; // Speichert alle GameActions die Auszuführen sind
	
	// Flags für die Übertragung in Schachnotation
	private boolean hit_flag = false; 
	private boolean enpassant_flag = false;
	private boolean promotion_flag = false;
	private char promotion_char = '\0';
	
	public Move(Board board, Square origin, Square destination){
		this.origin = origin;
		this.destination = destination;
		this.board = board;
	}
	
	private Move(Board board, GameAction [] actions){
		origin = null;
		destination = null;
		this.board = board;
	}
	
	
	public void perform(){ // Zug ausführen

	}
	
	public void revert(){ // Zug zurücksetzen, wichtig für die AI
		
	}

	public boolean isValid() { // Überprüft ob der Zug zulässig/regelkonform ist
		AbstractPiece piece = origin.getPiece();
		if(piece != null){
			type = piece.analyseMove(board, destination);
			if(type != ActionType.INVALID){
				return true;
			}
		}
		return false;
	}

	public String toChessNotation() { // Wandelt Zug in Schachnotation um
		return null;
	}

	public static Move fromChessNotation(String string, Board board){ // Stellt einen Zug aus der Schachnotation her, wichtig für den Netzwerkmodus
		return new Move(null, null);
	}
	
}
