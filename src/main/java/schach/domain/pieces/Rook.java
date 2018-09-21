package schach.domain.pieces;

import schach.domain.game.Square;

import java.util.ArrayList;

import schach.domain.game.Board;
import schach.domain.game.Move;
import schach.domain.game.Player;

public class Rook extends AbstractPiece {

	// Turm
	
	public Rook(Player player, Square position) {
		super(player, position);
	}

	@Override
	public char toChar() {
		return 'R';
	}

	@Override
	public boolean canReach(Board board, Square destination) {
		int row_origin = position.getRow();
		int column_origin = position.getColumn();
		int row_destination = destination.getRow();
		int column_destination = destination.getColumn();
		if(row_origin == row_destination || column_origin == column_destination){
			return true;
		} 
		return false;		
	}

	@Override
	public Square[] getPath(Board board, Square destination) {
		int row_origin = position.getRow();
		int column_origin = position.getColumn();
		int row_destination = destination.getRow();
		int column_destination = destination.getColumn();
		
		Square [] path;
		if(row_origin-row_destination != 0){
			path = new Square[Math.abs(row_origin-row_destination)];
		} else {
			path = new Square[Math.abs(column_destination-column_origin)];
		}
		int row_vector = (int) Math.signum(row_destination-row_origin);
		int column_vector = (int) Math.signum(column_destination-column_origin);
		for(int i = 0; i < path.length ; i++){
			path[i] = board.getSquare(column_origin + (i+1)*column_vector, row_origin + (i+1)*row_vector);
		}
		return path;
	}

	@Override
	public ArrayList<Move> getAllValidMoves(Board board) {
		ArrayList<Move> list = new ArrayList<Move>();
		goInDirection(board, list,  1,  0);
		goInDirection(board, list, -1,  0);
		goInDirection(board, list,  0,  1);
		goInDirection(board, list,  0, -1);

		return list;
	}
	
	private void goInDirection(Board board, ArrayList<Move> list, int dx, int dy) {
		int column = this.getPosition().getColumn();
		int row = this.getPosition().getRow();
		for(int i = 1; i < 9; i++) {
			int x = column + i*dx;
			int y = row + i*dy;
			if(isValidSquare(x, y)) {
				Square s = board.getSquare(x, y);
				if(s.isEmpty()) {
					list.add(new Move(board, this.getPosition(), s));
				} else if (!s.getPiece().getPlayer().equals(getPlayer())) {
					list.add(new Move(board, this.getPosition(), s));
					return;
				} else if (s.getPiece().getPlayer().equals(getPlayer())) {
					return;
				}
			} else {
				return;
			}
		}

	}
}
