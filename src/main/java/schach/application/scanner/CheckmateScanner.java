package schach.application.scanner;

import schach.domain.game.Board;
import schach.domain.game.Move;
import schach.domain.game.Player;
import schach.domain.pieces.AbstractPiece;
import schach.domain.pieces.King;

public class CheckmateScanner {
	
	/**
	 * Nur aufrufen wenn Check-Scan positiv war
	 * Check-Scan für ALLE möglichen Züge von Spieler 2 ob eine Figur von Spieler 1 dann den König schlagen kann, wenn kein Test negativ war positiv zurück geben
	 */

	public static GameStatus scan(Board board, Player checked, Player other) {
		King king = checked.getKing();

		for(AbstractPiece piece : checked.getPieces()){
			for(Move move : piece.getAllValidMoves(board)) {
				move.perform();
				boolean check = false;
				for(AbstractPiece piece2 : other.getPieces()) {
					Move move3 = new Move(board, piece2.getPosition(), king.getPosition());
					if(move3.isValid()) {
						check = true;
						break;
					}
				}
				move.revert();
				if(!check) {
					return GameStatus.NOTHING;
				}
			}
		}
		if(checked.getID() == 1) {
			return GameStatus.CHECKMATE_PLAYER1;
		} else {
			return GameStatus.CHECKMATE_PLAYER2;
		}
	}

}
