package schach.domain.pieces;

import schach.domain.game.Square;

import java.util.ArrayList;

import schach.domain.game.Board;
import schach.domain.game.Move;
import schach.domain.game.Player;

public class King extends AbstractPiece {

	// König

	public boolean moved = false;
	public boolean check = false;


	public King(Player player, Square position) {
		super(player, position);
	}
	
	@Override
	public char toChar() {
		return 'K';
	}


	@Override
	public boolean canReach(Board board, Square destination) { 
		int row_origin = position.getRow();
		int column_origin = position.getColumn();
		int row_destination = destination.getRow();
		int column_destination = destination.getColumn();
		if(	Math.abs(row_origin-row_destination) == 1 && column_origin == column_destination || //gleiche Spalte
				Math.abs(column_origin-column_destination) == 1 && row_origin == row_destination || //gleiche Reihe
				Math.abs(row_origin-row_destination) == 1 && Math.abs(column_origin-column_destination) == 1) //Diagonal
		{ 
			return true;
		}
		return false;
	}

	@Override
	public void move(Square destination) {
		super.move(destination);
	}
	@Override
	public Square[] getPath(Board board, Square destination) {
		return new Square[] {destination};
	}

	@Override
	public ArrayList<Move> getAllValidMoves(Board board) {
		ArrayList<Move> list = new ArrayList<Move>();
		int x = this.getPosition().getColumn();
		int y = this.getPosition().getRow();
		
		testSquare(board, list, x, y+1);
		testSquare(board, list, x, y-1);
		testSquare(board, list, x+1, y);
		testSquare(board, list, x-1, y);
		testSquare(board, list, x+1, y+1);
		testSquare(board, list, x-1, y+1);
		testSquare(board, list, x+1, y-1);
		testSquare(board, list, x-1, y-1);
		
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
