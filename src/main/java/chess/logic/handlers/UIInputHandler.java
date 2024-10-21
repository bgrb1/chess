package chess.logic.handlers;

import java.awt.Color;
import java.awt.event.MouseEvent;

import chess.data.Variables;
import chess.model.Board;
import chess.logic.Game;
import chess.model.Move;
import chess.model.Player;
import chess.model.Square;

public class UIInputHandler implements Handler {
	
	private boolean open = false;
	
	private Player current;
	private Square selectedSquare;
	private Square failedSquare;
	private boolean isSelected = false;
	private boolean failed;
	
	private final Board board;
	private final Game game;

	
	public UIInputHandler(Board board, Game game){
		this.board = board;
		this.game = game;
	}
	
	public synchronized void call(MouseEvent e){
		if(open){
			Square clickedSquare = getSquare(e.getX(), e.getY());
			if(isSelected){
				if(!(!clickedSquare.isEmpty() && clickedSquare.getPiece().getPlayer().equals(current))){
					boolean confirmed = execute(selectedSquare, clickedSquare);
					isSelected = !confirmed;
					if(!confirmed){
						if(failed) {
							failedSquare.unhighlight();							
						}
						failedSquare = clickedSquare;
						failedSquare.highlight(Color.RED);
						failed = true;
					} else {
						if(failed) {
							failedSquare.unhighlight();							
						}
						selectedSquare.unhighlight();
						failed = false;
					}
				} else {
					selectedSquare.unhighlight();
					selectedSquare = clickedSquare;
					selectedSquare.highlight(Color.GREEN);
					isSelected = true;
					if(failed) {
						failedSquare.unhighlight();
					}
					failed = false;
				} 
			} else {
				if((!clickedSquare.isEmpty()) && clickedSquare.getPiece().getPlayer().equals(current)){
					selectedSquare = clickedSquare;
					selectedSquare.highlight(Color.GREEN);
					isSelected = true;
				} 
			}
		}
		game.getGameView().repaintBoard();
	}
	
	private Square getSquare(int x, int y){
		int x2 =  (int) (x/Variables.SQUARE_SIZE())+1;
		int y2 =  8-(int) (y/Variables.SQUARE_SIZE());
		return board.getSquare(x2, y2) ;
	}
	
	public void ping(Player current) {
		open = true;
		this.current = current;
	}
	
	public synchronized boolean execute(Square origin, Square destination){
		Move move = new Move(board, origin, destination);
		if(move.isValid(true)){
			move.perform();
			open = false;
			return game.registerMove(move);
		} else {
			return false;
		}
	}

}
