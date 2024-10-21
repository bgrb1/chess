package chess.logic.scanners.drawrules;

import java.util.List;

import chess.model.Board;
import chess.model.Move;
import chess.model.pieces.AbstractPiece;
import chess.model.pieces.Bishop;
import chess.model.pieces.King;
import chess.model.pieces.Knight;

public class DeadPositionRule implements Rule {

	public boolean check(Move lastMove, Board board) {
        return king_vs_king(board) || king_and_bishop_vs_king(board) || king_and_knight_vs_king(board);
    }
	
	
	private boolean king_vs_king(Board board) {
		List<AbstractPiece> pieces = board.getAllPieces(false);
        return pieces.size() == 2;
    }
	private boolean king_and_bishop_vs_king(Board board) {
		List<AbstractPiece> pieces = board.getAllPieces(false);
		if(pieces.size() == 3) {
			List<AbstractPiece> pieces1 = board.getGame().getPlayer(1).getPieces(false);
			List<AbstractPiece> pieces2 = board.getGame().getPlayer(2).getPieces(false);

			if(pieces1.size() == 2 && ((pieces1.get(0) instanceof King && pieces1.get(1) instanceof Bishop) || (pieces1.get(1) instanceof King && pieces1.get(0) instanceof Bishop))) {
				return true;
			} else if(pieces2.size() == 2 && ((pieces2.get(0) instanceof King && pieces2.get(1) instanceof Bishop) || (pieces2.get(1) instanceof King && pieces2.get(0) instanceof Bishop))) {
				return true;
			}
		}
		return false;
	}
	private boolean king_and_knight_vs_king(Board board) {
		List<AbstractPiece> pieces = board.getAllPieces(false);
		if(pieces.size() == 3) {
			List<AbstractPiece> pieces1 = board.getGame().getPlayer(1).getPieces(false);
			List<AbstractPiece> pieces2 = board.getGame().getPlayer(2).getPieces(false);

			if(pieces1.size() == 2 && ((pieces1.get(0) instanceof King && pieces1.get(1) instanceof Knight) || (pieces1.get(1) instanceof King && pieces1.get(0) instanceof Knight))) {
				return true;
			} else if(pieces2.size() == 2 && ((pieces2.get(0) instanceof King && pieces2.get(1) instanceof Knight) || (pieces2.get(1) instanceof King && pieces2.get(0) instanceof Knight))) {
				return true;
			}
		}
		return false;
	}

}
