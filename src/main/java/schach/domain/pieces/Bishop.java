package schach.domain.pieces;

import schach.domain.game.Player;
import schach.domain.game.Square;

public class Bishop extends AbstractPiece {

	// Läufer

	public Bishop(Player player, byte vertical, byte horizontal) {
		super(player, vertical, horizontal);
	}

	@Override
	public boolean isValidMove(Square Target) {
		// TODO Auto-generated method stub
		return false;
	}

}
