package schach.domain.pieces;

import schach.domain.game.Square;
import schach.domain.actions.ActionType;
import schach.domain.game.Board;
import schach.domain.game.Player;

public class Rook extends AbstractPiece {

	// Turm

	boolean moved = false;

	public Rook(Player player, Square position) {
		super(player, position);
	}

	@Override
	public ActionType analyseMove(Board board, Square destination) {
		byte row_origin = position.getRow();
		byte column_origin = position.getColumn();
		byte row_destination = destination.getRow();
		byte column_destination = destination.getColumn();
		
		if( row_origin == row_destination ||
			column_origin == column_destination){
			if(){
				
			}
		} else if () {
			
		} else {
			return ActionType.INVALID;
		}
	}

	@Override
	public char toCharacter() {
		return 'R';
	}





}
