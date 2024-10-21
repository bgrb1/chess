package chess.model.pieces;

import chess.model.Square;

import java.util.ArrayList;
import java.util.List;

import chess.logic.scanners.CheckScanner;
import chess.logic.scanners.GameStatus;
import chess.model.Board;
import chess.model.Move;
import chess.model.Player;

public class King extends AbstractPiece {

	// K�nig

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
	public Square[] getPath(Board board, Square destination) {
		return new Square[] {destination};
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Move> getAllLegalMoves(Board board, boolean includePseudoLegal) {
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
