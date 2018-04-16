package schach.domain.pieces;

import schach.domain.actions.ActionType;
import schach.domain.game.Board;
import schach.domain.game.Player;
import schach.domain.game.Square;

public class Bishop extends AbstractPiece {
	
	//Läufer

	public Bishop(Player player, Square position) {
		super(player, position);
	}

	@Override
	public ActionType analyseMove(Board board, Square destination) {
		
		//!! ANMERKUNG: Eventuell diese Abfrage entfernen da nicht sinnvoll für Performance !!
		if(position.getColor() == destination.getColor()){
			byte row_origin = position.getRow();
			byte column_origin = position.getColumn();
			byte row_destination = destination.getRow();
			byte column_destination = destination.getColumn();
			if(Math.abs(row_origin-row_destination) == Math.abs(column_origin- column_destination)){
				return moveOrCapture(destination);
			} else {
				return ActionType.INVALID;
			}
		} else {
			return ActionType.INVALID;
		}
	}

	@Override
	public char toChar() {
		return 'B';
	}



}
