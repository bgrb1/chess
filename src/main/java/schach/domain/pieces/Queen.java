package schach.domain.pieces;

import schach.domain.actions.ActionType;
import schach.domain.game.Board;
import schach.domain.game.Player;
import schach.domain.game.Square;

public class Queen extends AbstractPiece {
	
	// Königin

	public Queen(Player player, Square position) {
		super(player, position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionType analyseMove(Board board, Square destination) {
		byte row_origin = position.getRow();
		byte column_origin = position.getColumn();
		byte row_destination = destination.getRow();
		byte column_destination = destination.getColumn();
		
		// Diagonale Züge (vom Läufer kopiert)
		if(Math.abs(row_origin-row_destination) == Math.abs(column_origin- column_destination)){
			return moveOrCapture(destination);
		} else {
			return ActionType.INVALID;
		}
		
		// Gerade Züge (vom Turm kopiert)
	}

	@Override
	public char toChar() {
		return 'Q';
	}




}
