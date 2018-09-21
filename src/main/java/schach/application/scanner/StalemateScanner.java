package schach.application.scanner;

import schach.domain.game.Board;
import schach.domain.game.Move;
import schach.domain.game.Player;
import schach.domain.pieces.AbstractPiece;
import schach.domain.pieces.King;

public class StalemateScanner {
	
	public static GameStatus scan(Board board, Player lastMoved, Player other) {
		King king = other.getKing();
		for(AbstractPiece piece : other.getPieces()) {
			for(Move move : piece.getAllValidMoves(board)) {
				move.perform();
				boolean valid = true;
				for(AbstractPiece piece2 : lastMoved.getPieces()) {
					Move move2 = new Move(board, piece2.getPosition(), king.getPosition());
					if(move2.isValid()) {
						valid = false;
						break;
					}
				}
				move.revert();
				if(valid) {
					return GameStatus.NOTHING;
				}
			}
		}
		return GameStatus.STALEMATE;
		
	}

}