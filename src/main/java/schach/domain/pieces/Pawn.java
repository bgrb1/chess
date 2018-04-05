package schach.domain.pieces;

import schach.domain.game.Square;
import schach.domain.actions.ActionType;
import schach.domain.game.Board;
import schach.domain.game.Player;

public class Pawn extends AbstractPiece {

	// Bauer

	public Pawn(Player player, Square position) {
		super(player, position);
	}

	@Override
	public final ActionType analyseMove(Board board, Square destination) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char toCharacter() {
		// TODO Auto-generated method stub
		return 'P';
	}

}
