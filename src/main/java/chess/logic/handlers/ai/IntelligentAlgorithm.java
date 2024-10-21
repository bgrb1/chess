package chess.logic.handlers.ai;

import chess.model.Board;
import chess.model.Move;
import chess.model.Player;

public interface IntelligentAlgorithm {
	
	public Move calculateBestMove(Board board, Player computer, Player human);

}
