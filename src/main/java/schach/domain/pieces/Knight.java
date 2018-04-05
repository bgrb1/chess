package schach.domain.pieces;

import schach.domain.game.Square;
import schach.domain.game.Player;

public class Knight extends AbstractPiece {

	// Springer

	public Knight(Player player, Square position) {
		super(player, position);
	}

	@Override
	public Square[] fieldsToPass(Square target) {
		return new Square [] {target};
	}



}
