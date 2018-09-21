package schach.application.scanner;

import schach.domain.game.Board;
import schach.domain.game.Move;
import schach.domain.game.Player;
import schach.domain.game.Square;
import schach.domain.pieces.AbstractPiece;

public class CheckScanner {
	
	/**
	 * Alle Figuren des Spieler 1 durchlaufe
	 * Move-Objekt zur Position des Königs von Spieler 2 erstellen
	 * Move prüfen
	 * wenn valid, return true
	 */

	public static GameStatus scan(Board board, Player lastMoved, Player other) {
		//Ob Spieler lastMoved im Schach steht, dann wäre der Zug unzulässig
		Square king1 = lastMoved.getKing().getPosition();
		for(AbstractPiece piece : other.getPieces()) {
			Move move = new Move(board, piece.getPosition(), king1);
			if(move.isValid()) {
				if(lastMoved.getID() == 1) {
					return GameStatus.CHECK_PLAYER1;
				} else {
					return GameStatus.CHECK_PLAYER2;
				}
			}
		}
		//Jetzt ob nach Zug der andere Spieler im Schach steht, führt zum Aufruf des Checkmate Scanners
		Square king2 = other.getKing().getPosition();

		for(AbstractPiece piece : lastMoved.getPieces()) {
			Move move = new Move(board, piece.getPosition(), king2);
			if(move.isValid()) {
				if(other.getID() == 1) {
					return GameStatus.CHECK_PLAYER1;
				} else {
					return GameStatus.CHECK_PLAYER2;
				}
			}
		}
		return GameStatus.NOTHING;
	}
	
	
	

}
