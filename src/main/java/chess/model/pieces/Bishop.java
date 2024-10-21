package chess.model.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.logic.scanners.CheckScanner;
import chess.logic.scanners.GameStatus;
import chess.model.Board;
import chess.model.Move;
import chess.model.Player;
import chess.model.Square;

public class Bishop extends AbstractPiece {
	
	//L�ufer
	
	@Override
	public char toChar() { return 'B'; }
	
	
	public Bishop(Player player, Square position) {
		super(player, position);
	}

	public boolean canReach(Board board, Square destination) {
		int row_origin = position.getRow();
		int column_origin = position.getColumn();
		int row_destination = destination.getRow();
		int column_destination = destination.getColumn();
		
		if(Math.abs(row_origin-row_destination) == Math.abs(column_origin- column_destination)){
			return true;
		} 
		return false;
	}
	
	public Square [] getPath(Board board, Square destination){
		int row_origin = position.getRow();
		int column_origin = position.getColumn();
		int row_destination = destination.getRow();
		int column_destination = destination.getColumn();
		
		Square [] path = new Square[Math.abs(row_origin-row_destination)];
		int row_vector = (int) Math.signum(row_destination-row_origin);
		int column_vector = (int) Math.signum(column_destination-column_origin);
		for(int i = 0; i < path.length ; i++){
			path[i] = board.getSquare(column_origin + (i+1)*column_vector, row_origin + (i+1)*row_vector);
		}
		return path;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Move> getAllLegalMoves(Board board, boolean includePseudoLegal) {
		ArrayList<Move> list = new ArrayList<Move>();
		
		goInDirection(board, list, 1, 1);
		goInDirection(board, list, 1, -1);
		goInDirection(board, list, -1, 1);
		goInDirection(board, list, -1, -1);

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
