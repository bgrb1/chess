package chess.logic.scanners.drawrules;

import chess.model.Board;
import chess.model.Move;
import chess.model.Player;
import chess.model.pieces.AbstractPiece;
import chess.model.pieces.King;

public class StalemateRule implements Rule {

	public boolean check(Move lastMove, Board board) {
		Player lastMoved = board.getGame().getPlayer(true);
		Player other = board.getGame().getPlayer(false);
		King king = other.getKing();
		for(Move move : other.getAllLegalMoves(board, true)) {
			move.perform();
			boolean valid = true;
			for(AbstractPiece piece2 : lastMoved.getPieces(false)) {
				Move move2 = new Move(board, piece2.getPosition(), king.getPosition());
				if(move2.isValid(false)) {
					valid = false;
					break;
				}
			}
			move.revert();
			if(valid) {
				return false;
			}
		}
		return true;
		
	}

}
