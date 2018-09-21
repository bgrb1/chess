package schach.domain.pieces;

import java.util.ArrayList;

import schach.domain.game.Board;
import schach.domain.game.Move;
import schach.domain.game.Player;
import schach.domain.game.Square;

public class Pawn extends AbstractPiece {

	// Bauer
	
	boolean moved = false;
	boolean firstmove = false;
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
		// TODO Auto-generated method stub
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
				   moved == false && destination.isEmpty()){
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
		if(firstmove  == false && moved == false) {
			firstmove = true;
		} else if(firstmove == true && moved == true) {
			firstmove = false;
		}
		moved = true;

	}
	
	@Override
	public void moveBackTo(Square destination) {
		super.moveBackTo(destination);
		if(moved == true && firstmove == true) {
			moved = false;
			firstmove = false;
		}
	}
	@Override
	public ArrayList<Move> getAllValidMoves(Board board) { //Prüfen ob Feld im 8x8 enthalten ist
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
		if(!moved) {
			x = x-1;
			y = y+direction;
			if(isValidSquare(x, y)) {
				Square s = board.getSquare(x, y);
				if(s.isEmpty()) {
					list.add(new Move(board, this.getPosition(), s));
				}
			}
		}
		return list;
	}

}
