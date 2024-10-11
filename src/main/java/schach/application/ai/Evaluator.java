package chess.application.ai;

import chess.domain.game.Board;
import chess.domain.game.Player;
import chess.domain.game.Square;
import chess.domain.pieces.AbstractPiece;
import chess.domain.pieces.Bishop;
import chess.domain.pieces.King;
import chess.domain.pieces.Knight;
import chess.domain.pieces.Pawn;
import chess.domain.pieces.Queen;
import chess.domain.pieces.Rook;

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
		return (int)value;

		//return (int) Math.ceil(value);
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
			//double [] [] reversed = reverseArray(arr);
			return arr[x-1] [y-1];
		}
	}
	
	@SuppressWarnings("unused")
	private static double [] [] reverseArray(double [] [] arr){
	    /*for (int i = 0; i < arr.length / 2; i++) {
	        double [] temp = arr[i];
	        arr[i] = arr[arr.length - 1 - i];
	        arr[arr.length - 1 - i] = temp;
	        return arr;
	    }*/
	    
	    double [] [] reversedArray = new double [8] [8];
	    for (int i = 0; i < arr.length; i++) {
	    	reversedArray[i] = arr[arr.length-1-i];
	    }
	    return reversedArray;
	}

}
