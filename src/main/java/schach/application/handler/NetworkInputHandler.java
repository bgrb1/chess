package schach.application.handler;

import schach.domain.game.Board;
import schach.domain.game.Game;
import schach.domain.game.Move;
import schach.domain.game.Player;

public class NetworkInputHandler implements Handler {
	
	private Player current;
	private boolean open = false;
	private Board board;
	private Game game;

	public NetworkInputHandler(Board board, Game game){
		this.board = board;
		this.game = game;
	}
	
	public void ping(Player current) {
		this.current = current;
		open = true;
	}
	
	public synchronized boolean handleNetworkInput(String notation) {
		if(open) {
			Move move = Move.fromString(notation, board);
			if(move.getOrigin().getPiece().getPlayer().equals(current) && move.isValid()) {
					open = false;
					move.perform();
					game.registerMove(move, false);
					return true;
			} else {
					return false;
			}
		} else {
			return false;
		}
	}

}
