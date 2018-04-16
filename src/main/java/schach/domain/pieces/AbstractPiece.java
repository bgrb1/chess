package schach.domain.pieces;

import schach.domain.actions.ActionType;
import schach.domain.game.Board;
import schach.domain.game.Player;
import schach.domain.game.Square;

public abstract class AbstractPiece {
	
	// public abstract final Object icon; 
	
	
	// !!!!! Möglichkeit finden, Abhängigkeit von Board zu besetitigen !!!!!
	public abstract ActionType analyseMove(Board board, Square destination); // Gibt die Art des Zugs zurück, INVALID wenn nicht zulässig 

	public abstract char toChar(); // Figur als Schach-Zeichen
	
	protected Square position;

	private Player player;
	
	private boolean captured = false;
	
	
	public AbstractPiece(Player player, Square position) {
		this.position = position;
		this.player = player;	
	}
	
	public final String toChessNotation(){ // Gibt die Figur mit Position in Schachnotation zurück 
		return null;
	}
	
	public final void move(Square destination){ 
		
	}

	public final Player getPlayer() {
		return player;
	}
	
	public final void capture(){ // Schlägt die Figur

	}
	
	public final ActionType moveOrCapture(Square destination){ // Hilfsfunktion für analyseMove(Board, Square);
		boolean capture = !destination.isEmpty();
		if(capture){
			return ActionType.CAPTURE;
		} else {
			return ActionType.NORMAL_MOVE;
		}
	}

}
