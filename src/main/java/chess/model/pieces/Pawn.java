package chess.model.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.logic.scanners.CheckScanner;
import chess.logic.scanners.GameStatus;
import chess.model.Board;
import chess.model.Move;
import chess.model.Player;
import chess.model.Square;

public class Pawn extends AbstractPiece {

	// Bauer
	
	int moves = 0;
	final int direction;

	public Pawn(Player player, Square position) {
		super(player, position);
		if(player.getID() == 1){
			direction = 1;
		} else {
			direction = -1;
		}
	}
	
	@Override
	public char toChar() {
		return 'P';
	}	
	
	@Override
	public boolean canReach(Board board, Square destination) {
		if(destination.getRow()- position.getRow() == direction){
			if(position.getColumn() == destination.getColumn() && 
			   destination.isEmpty()) {
				return true;
			} else if( (Math.abs(position.getColumn()- destination.getColumn()) == 1) &&
						!destination.isEmpty()){
				return true;
			}
		} else if (destination.getRow()- position.getRow() == 2*direction && 
				   this.getPosition().getColumn() == destination.getColumn() &&
				   moves == 0 && destination.isEmpty()){
			return true;
		}
		return false;
	}
	
	@Override
	public Square[] getPath(Board board, Square destination) {
		if(Math.abs(destination.getRow()- position.getRow()) == 2){
			Square a = board.getSquare(position.getColumn(), position.getRow()+direction);
			return new Square [] {a, destination};
		} else {
			return new Square [] {destination};
		}
	}
	
	

	@Override 
	public void move(Square destination){
		super.move(destination);
		moves++;
	}
	
	@Override
	public void moveBack(Square origin) {
		super.moveBack(origin);
		moves--;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Move> getAllLegalMoves(Board board, boolean includePseudoLegal) { //Pr�fen ob Feld im 8x8 enthalten ist
		ArrayList<Move> list = new ArrayList<Move>();
		int x = this.getPosition().getColumn();
		int y = this.getPosition().getRow()+direction;
		if(isValidSquare(x, y)) {
			Square s = board.getSquare(x, y);
			if(s.isEmpty()) {
				list.add(new Move(board, this.getPosition(), s));
			}
		}
		x = x-1;
		if(isValidSquare(x, y)) {
			Square s = board.getSquare(x, y);
			if(!s.isEmpty() && !s.getPiece().getPlayer().equals(this.getPlayer())) {
				list.add(new Move(board, this.getPosition(), s));
			}
		}
		x = x+2;
		if(isValidSquare(x, y)) {
			Square s = board.getSquare(x, y);
			if(!s.isEmpty() && !s.getPiece().getPlayer().equals(this.getPlayer())) {
				list.add(new Move(board, this.getPosition(), s));
			}
		}
		if(moves == 0) {
			x = x-1;
			int y1 = y;
			y = y+direction;
			if(isValidSquare(x, y)) {
				Square s = board.getSquare(x, y);
				Square s1 = board.getSquare(x, y1);
				if(s.isEmpty() && s1.isEmpty()) {
					list.add(new Move(board, this.getPosition(), s));
				}
			}
		}
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

}
