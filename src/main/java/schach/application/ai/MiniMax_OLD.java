package chess.application.ai;

import java.util.ArrayList;
import java.util.List;

import chess.application.scanner.drawrules.StalemateRule;
import chess.domain.game.Board;
import chess.domain.game.Move;
import chess.domain.game.Player;
import chess.domain.pieces.King;


//Standart MiniMax-Algorithmus
public class MiniMax_OLD implements IntelligentAlgorithm {
	
	private int DEPTH = 3;

	public Move calculateBestMove(Board board, Player computer, Player human) {
		long start = System.currentTimeMillis();
		
		EvaluatedMove bestMove = null;
		List<Move> moves = computer.getAllLegalMoves(board, false);
		clearList(moves, board);
		for(Move m : moves) {
			m.perform();
			int eval = min(board, computer, human, 0);
			m.revert();
			EvaluatedMove evalMove = new EvaluatedMove(m, eval);
			bestMove = Evaluator.choose(evalMove, bestMove, true);
		}
		
		long end = System.currentTimeMillis();
		System.out.println("MiniMax: " + bestMove.getMove().toString() + " | Evaluation=" + bestMove.getEvaluation() + " | Time=" + (end-start) + "ms");
		return bestMove.getMove();
	}
	@SuppressWarnings("unchecked")
	private void clearList(List<Move> moves, Board board) {
		StalemateRule stalemate = new StalemateRule();
		ArrayList<Move>list  = (ArrayList<Move>) moves;
		for(Move move : (List<Move>)list.clone()) {
			if(moves.size() > 0) {
				move.perform();
				if(stalemate.check(move, board)) {
					moves.remove(move);
				}
				move.revert();
			}
		}
	}
	
	private int max(Board board, Player computer, Player human, int depth) {
		if(depth == DEPTH) {
			return Evaluator.evaluate(board); 
		}
		List<Move> moves = computer.getAllLegalMoves(board, true);
		int best = -10000;
		for(Move m : moves) {
			if(m.getDestination().getPiece() instanceof King) {
				return 1000;
			}
			m.perform();
			best = Math.max(best, min(board, computer, human, depth+1)); 
			m.revert();
		}
		return best;
	}
	
	private int min(Board board, Player computer, Player human, int depth) {
		if(depth == DEPTH) {
			return Evaluator.evaluate(board); 
		}
		List<Move> moves = human.getAllLegalMoves(board, true);
		int best = 10000;
		for(Move m : moves) {
			if(m.getDestination().getPiece() instanceof King) {
				return -1000;
			}
			m.perform();
			best = Math.min(best, max(board, computer, human, depth+1));
			m.revert();
		}
		return best;
	}
}
