package schach.domain.pieces;

import schach.domain.game.Player;
import schach.domain.game.Square;

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
