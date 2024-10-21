package chess.logic.scanners;

import chess.model.Board;
import chess.model.Move;
import chess.model.Player;
import chess.model.pieces.AbstractPiece;
import chess.model.pieces.King;

public class CheckmateScanner {


	public static GameStatus scan(Board board, Player checked, Player other) {
		King king = checked.getKing();

		for(Move move  : checked.getAllLegalMoves(board, true)){
			move.perform();
			boolean check = false;
			for(AbstractPiece piece2 : other.getPieces(false)) {
				Move move3 = new Move(board, piece2.getPosition(), king.getPosition());
				if(move3.isValid(false)) {
					check = true;
					break;
				}
			}
			move.revert();
			if(!check) {
				return GameStatus.NOTHING;
			}
		}
		if(checked.getID() == 1) {
			return GameStatus.CHECKMATE_PLAYER1;
		} else {
			return GameStatus.CHECKMATE_PLAYER2;
		}
	}

}
