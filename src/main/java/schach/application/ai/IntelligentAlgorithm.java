package chess.application.ai;

import chess.domain.game.Board;
import chess.domain.game.Move;
import chess.domain.game.Player;

public interface IntelligentAlgorithm {
	
	public Move calculateBestMove(Board board, Player computer, Player human);

}
