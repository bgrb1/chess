package chess.logic.handlers.ai;

import chess.logic.handlers.Handler;
import chess.model.Board;
import chess.model.Move;
import chess.model.Player;

public class AIHandler implements Handler {
	
	private final Board board;
	private final IntelligentAlgorithm algo;

	
	public AIHandler(Board board){
		this.board = board;
		algo = new AlphaBeta();
	}
	
	public void ping(Player computer) {
		Move move = algo.calculateBestMove(board, computer, board.getGame().getPlayer(1));
		move.perform();
		board.getGame().registerMove(move);
	}
	

}
