package schach.domain.pieces;

import schach.domain.game.Square;

import schach.domain.actions.ActionType;
import schach.domain.game.Board;
import schach.domain.game.Player;

public abstract class AbstractPiece {

	protected Square position;

	private Player player;
	
	private boolean standing = true;
		
	// public abstract final  Image image;

	public AbstractPiece(Player player, Square position) {
		this.position = position;
		this.player = player;	
	}
	
	public abstract ActionType analyseMove(Board board, Square destination); //Gibt das/die zu übergehend/-en Feld/-er zurück (inkl. das Zielfeld), gibt null zurück wenn nicht erreichbar
	
	public abstract char toCharacter();
	
	public final String toChessNotation(){
		return null;
	}
	
	public final void move(Square destination){
		
	}

	public final Player getPlayer() {
		return player;
	}
	
	public final void capture(){

	}
	
	public final ActionType moveOrCapture(Square destination){
		boolean capture = !destination.isEmpty();
		if(capture){
			return ActionType.CAPTURE;
		} else {
			return ActionType.NORMAL_MOVE;
		}
	}

}
