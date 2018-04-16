package schach.domain.pieces;

import schach.domain.actions.ActionType;
import schach.domain.game.Board;
import schach.domain.game.Player;
import schach.domain.game.Square;

public class Knight extends AbstractPiece {
	
	// Springer

	public Knight(Player player, Square position) {
		super(player, position);
	}

	@Override
	public ActionType analyseMove(Board board, Square destination) {
		byte row_origin = position.getRow();
		byte column_origin = position.getColumn();
		byte row_destination = destination.getRow();
		byte column_destination = destination.getColumn();
		
		if( (Math.abs(row_origin-row_destination) == 2 && Math.abs(column_origin-column_destination) == 1) ||
			 (Math.abs(column_origin-column_destination) == 2 && Math.abs(row_origin-row_destination) == 1)) {
				 return moveOrCapture(destination);
		} else {
			return ActionType.INVALID;
		}
	}

	@Override
	public char toChar() {
		return 'N';
	}
	
}
