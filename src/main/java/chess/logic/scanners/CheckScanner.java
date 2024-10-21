package chess.logic.scanners;

import chess.model.Board;
import chess.model.Move;
import chess.model.Player;
import chess.model.Square;
import chess.model.pieces.AbstractPiece;

public class CheckScanner {

	public static GameStatus applyScan(Board board) {
		Player player1 = board.getGame().getPlayer(true);
		Player player2 = board.getGame().getPlayer(false);
		Square king1 = player1.getKing().getPosition();
		for(AbstractPiece piece : player2.getPieces(false)) {
			Move move = new Move(board, piece.getPosition(), king1);
			if(move.isValid(false)) {
				if(player1.getID() == 1) {
					return GameStatus.CHECK_PLAYER1;
				} else {
					return GameStatus.CHECK_PLAYER2;
				}
			}
		}
		Square king2 = player2.getKing().getPosition();

		for(AbstractPiece piece : player1.getPieces(false)) {
			Move move = new Move(board, piece.getPosition(), king2);
			if(move.isValid(false)) {
				if(player1.getID() == 2) {
					return GameStatus.CHECK_PLAYER1;
				} else {
					return GameStatus.CHECK_PLAYER2;
				}			
			}
		}
		return GameStatus.NOTHING;
	}
	
	public static GameStatus applyScanForOne(Board board, Player player) {
		Player player1 = player;
		Player player2 = null;
		if(player.getID() == 1) {
			player2 = board.getGame().getPlayer(2);
		} else {
			player2 = board.getGame().getPlayer(1);
		}
		Square king1 = player1.getKing().getPosition();
		for(AbstractPiece piece : player2.getPieces(false)) {
			Move move = new Move(board, piece.getPosition(), king1);
			if(move.isValid(false)) {
				if(player1.getID() == 1) {
					return GameStatus.CHECK_PLAYER1;
				} else {
					return GameStatus.CHECK_PLAYER2;
				}			
			}
		}
		return GameStatus.NOTHING;
	}
	
}
