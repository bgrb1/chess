package schach.domain.pieces;

import schach.domain.game.Square;
import schach.domain.actions.ActionType;
import schach.domain.game.Board;
import schach.domain.game.Player;

public class King extends AbstractPiece {

	// König

	public boolean moved = false;
	public boolean check = false;


	public King(Player player, Square position) {
		super(player, position);
		// TODO Auto-generated constructor stub
	}


	@Override
	public final ActionType analyseMove(Board board, Square destination) {
		byte row_origin = position.getRow();
		byte column_origin = position.getColumn();
		byte row_destination = destination.getRow();
		byte column_destination = destination.getColumn();
		
		boolean capture = !destination.isEmpty();
		
		if(	Math.abs(row_origin-row_destination) == 1 && column_origin == column_destination || //gleiche Spalte
			Math.abs(column_origin-column_destination) == 1 && row_origin == row_destination || //gleiche Reihe
			Math.abs(row_origin-row_destination) == 1 && Math.abs(column_origin-column_destination) == 1) //Diagonal
		{ 
			return moveOrCapture(destination);
		} else if (){
			//Roachde
		} else {
			return ActionType.INVALID;
		}
	}


	@Override
	public final char toCharacter() {
		return 'K';
	}



}
