package chess.logic.handlers.ai;

import chess.model.Board;
import chess.model.Player;
import chess.model.Square;
import chess.model.pieces.AbstractPiece;
import chess.model.pieces.Bishop;
import chess.model.pieces.King;
import chess.model.pieces.Knight;
import chess.model.pieces.Pawn;
import chess.model.pieces.Queen;
import chess.model.pieces.Rook;

public class Evaluator {
	
	
	public static int evaluate(Board board) {
		int value = 0;
		for(AbstractPiece piece : board.getAllPieces(false)) {
			if(piece.getPlayer().getID() == 1) {
				value -= getPieceValue(piece);
			} else {
				value += getPieceValue(piece);
			}
		}
		return value;
	}

	private static int getPieceValue(AbstractPiece piece) {
		double value = 0;
		if (piece instanceof Pawn) {
			value = DefaultValue.PAWN + getPositionValue(DefaultValue.PAWN_POSITION_ARRAY, piece.getPlayer(), piece.getPosition());
		} else if (piece instanceof Rook) {
			value = DefaultValue.ROOK + getPositionValue(DefaultValue.ROOK_POSITION_ARRAY, piece.getPlayer(), piece.getPosition());
		} else if (piece instanceof Knight) {
			value = DefaultValue.KNIGHT + getPositionValue(DefaultValue.KNIGHT_POSITION_ARRAY, piece.getPlayer(), piece.getPosition());
		} else if (piece instanceof Bishop) {
			value = DefaultValue.BISHOP + getPositionValue(DefaultValue.BISHOP_POSITION_ARRAY, piece.getPlayer(), piece.getPosition());
		} else if (piece instanceof King) {
			value = DefaultValue.KING + getPositionValue(DefaultValue.KING_POSITION_ARRAY, piece.getPlayer(), piece.getPosition());
		} else if (piece instanceof Queen) {
			value = DefaultValue.QUEEN + getPositionValue(DefaultValue.QUEEN_POSITION_ARRAY, piece.getPlayer(), piece.getPosition());
		}
		return (int) value;
	}
	
	protected static EvaluatedMove choose(EvaluatedMove a, EvaluatedMove b, boolean maximizing) {
		if(a == null) {
			return b;
		} else if(b == null) {
			return a;
		} else if(b.getEvaluation() > a.getEvaluation()) {
			if(maximizing) {
				return b;
			} else {
				return a;
			}
		} else if(a.getEvaluation() > b.getEvaluation()) {
			if(maximizing) {
				return a;
			} else {
				return b;
			}
		} else {
			return a;
		}
	}
	
	private static double getPositionValue(double [] [] arr, Player player, Square position) {
		int x = position.getRow();
		int y = position.getColumn();
		if(player.getID() == 1) {
			return arr [8-x][y-1];
		} else {
			return arr[x-1] [y-1];
		}
	}
}
