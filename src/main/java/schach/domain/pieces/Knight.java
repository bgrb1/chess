package schach.domain.pieces;

import java.util.ArrayList;

import schach.domain.game.Board;
import schach.domain.game.Move;
import schach.domain.game.Player;
import schach.domain.game.Square;

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

	@Override
	public ArrayList<Move> getAllValidMoves(Board board) {
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
