package chess.model.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.logic.scanners.CheckScanner;
import chess.logic.scanners.GameStatus;
import chess.model.Board;
import chess.model.Move;
import chess.model.Player;
import chess.model.Square;

public class Knight extends AbstractPiece {
	
	// Springer

	public Knight(Player player, Square position) {
		super(player, position);
	}
	
	@Override
	public boolean canReach(Board board, Square destination) {
		int row_origin = position.getRow();
		int column_origin = position.getColumn();
		int row_destination = destination.getRow();
		int column_destination = destination.getColumn();
		
		if( (Math.abs(row_origin-row_destination) == 2 && Math.abs(column_origin-column_destination) == 1) ||
			(Math.abs(column_origin-column_destination) == 2 && Math.abs(row_origin-row_destination) == 1)) {		
			return true;
		} else {
			return false;
		}
	} 

	@Override
	public Square[] getPath(Board board, Square destination) {
		return new Square[] {destination};
	}
	
	@Override
	public char toChar() {
		return 'N';
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Move> getAllLegalMoves(Board board, boolean includePseudoLegal) {
		ArrayList<Move> list = new ArrayList<Move>();
		int x = this.getPosition().getColumn();
		int y = this.getPosition().getRow();
		
		testSquare(board, list, x+1, y+2);
		testSquare(board, list, x-1, y+2);
		testSquare(board, list, x+1, y-2);
		testSquare(board, list, x-1, y-2);
		testSquare(board, list, x+2, y+1);
		testSquare(board, list, x+2, y-1);
		testSquare(board, list, x-2, y+1);
		testSquare(board, list, x-2, y-1);
		
		if(!includePseudoLegal) {
			for(Move move : ((List <Move>)list.clone())) {
				move.perform();
				GameStatus gs = CheckScanner.applyScanForOne(board, getPlayer());
				if((getPlayer().getID() == 1 && gs == GameStatus.CHECK_PLAYER1) || (getPlayer().getID() == 2 && gs == GameStatus.CHECK_PLAYER2)) {
					list.remove(move);
				}
				move.revert();
			}
		}
		return list;
	}
	
	private void testSquare(Board board, ArrayList<Move> list, int column, int row) {
		if(isValidSquare(column, row)) {
			Square s = board.getSquare(column, row);
			if(s.isEmpty() || !s.getPiece().getPlayer().equals(getPlayer())) {
				list.add(new Move(board, this.getPosition(), s));
			}
		}
	}

}
