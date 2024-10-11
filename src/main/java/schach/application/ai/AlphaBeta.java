package chess.application.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chess.application.scanner.drawrules.StalemateRule;
import chess.data.Variables;
import chess.domain.game.Board;
import chess.domain.game.Move;
import chess.domain.game.Player;
import chess.domain.pieces.AbstractPiece;
import chess.domain.pieces.Bishop;
import chess.domain.pieces.King;
import chess.domain.pieces.Knight;
import chess.domain.pieces.Pawn;
import chess.domain.pieces.Queen;
import chess.domain.pieces.Rook;


//Alpha-Beta-Suche mit Zugsortierung und iterativer Tiefensuche
public class AlphaBeta implements IntelligentAlgorithm {

	private boolean stop_flag = false;

	public Move calculateBestMove(Board board, Player computer, Player human) {
		long start = System.currentTimeMillis();
	
		EvaluatedMove bestMove = null;
		List<Move> rawMoves = computer.getAllLegalMoves(board, true);
		clearList(rawMoves, board);
		rawMoves =  orderHeuristic(computer.getAllLegalMoves(board, true), true);
		List<EvaluatedMove> moves = EvaluatedMove.transformList(rawMoves);
		startTimer(Variables.ITERATIVE_SEARCH_TIME);
		for(int depth = 3;; depth++) {
			for(EvaluatedMove m : moves) {
				m.getMove().perform();
				int eval = min(board, computer, human, depth, -100000, 100000);
				m.getMove().revert();
				if(!stop_flag) {
					m.setEvaluation(eval);
				} else {
					break;
				}
				bestMove = Evaluator.choose(m, bestMove, true);
			}
			if(stop_flag) {
				break;
			} 
		}
		stop_flag = false;
		long end = System.currentTimeMillis();
		System.out.println("AlphaBetaIterative: " + bestMove.getMove().toString() + " | Evaluation=" + bestMove.getEvaluation() + " | Time=" + (end-start) + "ms");
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
	private int max(Board board, Player computer, Player human, int depth, int alpha, int beta) {
		if(depth == 0) {
			return Evaluator.evaluate(board); 
		}
		List<Move> moves = orderHeuristic(computer.getAllLegalMoves(board, false), true);
		int best = -10000;
		for(Move m : moves) {
			if(m.getDestination().getPiece() instanceof King) {
				return 1000;
			}
			m.perform();
			if(!stop_flag) {
				best = Math.max(best, min(board, computer, human, depth-1, alpha, beta)); 
			} else {
				best = Math.max(best, min(board, computer, human, 0, alpha, beta)); 
			}
			m.revert();
			alpha = Math.max(alpha, best); 
			if(beta <= alpha) { //Äquivalent zu best >= beta
				return best;
			}
		}
		return best;
	}
	
	private int min(Board board, Player computer, Player human, int depth, int alpha, int beta) {
		if(depth == 0) {
			return Evaluator.evaluate(board); 
		}
		List<Move> moves = orderHeuristic(human.getAllLegalMoves(board, false), false);
		int best = 10000;
		for(Move m : moves) {
			if(m.getDestination().getPiece() instanceof King) {
			return -1000;
			}
			m.perform();
			if(!stop_flag) {
				best = Math.min(best, max(board, computer, human, depth-1, alpha, beta));
			} else {
				best = Math.min(best, max(board, computer, human, 0, alpha, beta));
			}
			m.revert();
			beta = Math.min(beta, best);
			if(beta <= alpha) { //Äquivalent zu best <= alpha
				return best;
			}
		}
		return best;
	}
	
	private List<Move> orderHeuristic(List<Move> moves, boolean maximizing) {
		Move [] moveArray = (Move[]) (moves.toArray(new Move[0])); 
		int [] heuristicArray = new int [moves.size()];
		for(int i = 0; i < moves.size(); i++) {
			if(moveArray[i].getHitFlag()) {
				int value = getHeurisitcValue(moveArray[i].getMovedPiece()) - (int)(getHeurisitcValue(moveArray[i].getCapturedPiece()));
				heuristicArray[i] = value;
			} else {
				heuristicArray[i] = 0;
			}
		}
		quickSort(moveArray, heuristicArray, 0, moveArray.length-1);
		if(maximizing) {
			return Arrays.asList(reverseArray(moveArray));
		} else {
			return Arrays.asList(moveArray);
		}
	}
	
	private int getHeurisitcValue(AbstractPiece a) {
		if(a instanceof Pawn) {
			return DefaultValue.PAWN;
		} else if (a instanceof Bishop) {
			return DefaultValue.BISHOP;
		} else if (a instanceof Knight) {
			return DefaultValue.KNIGHT;
		} else if (a instanceof Rook) {
			return DefaultValue.ROOK;
		} else if (a instanceof Queen) {
			return DefaultValue.QUEEN;
		} else {
			return DefaultValue.KING;
		}
	}
	
	public static void quickSort(Move [] moves, int [] values, int left, int right) {
		if (left < right) {	   
			int i = left;
			int j = right-1;
			int pivot = values[right];  
			int tempV = 0;
			Move tempM = null;
			   
			while(i<=j) {
				if (values[i] > pivot) {     
					tempV = values[i]; 
					tempM = moves[i];   
					values[i] = values[j]; 
					moves[i] = moves[j]; 
					values[j] = tempV;  
					moves[j] = tempM;                             
					j--;
				} else {
					i++;
				}
			}   
			tempV = values[i]; 
			tempM = moves[i];
			values[i] = values[right]; 
			moves[i] = moves[right];     
			values[right] = tempV;  
			moves[right] = tempM;
		       
			quickSort(moves, values, left, i-1);
			quickSort(moves, values, i+1, right);
		}
	}	    

	private static Move [] reverseArray(Move [] arr){
		Move [] reversedArray = new Move [arr.length];
	    for (int i = 0; i < arr.length; i++) {
	    	reversedArray[i] = arr[arr.length-1-i];
	    }
	    return reversedArray;
	}
	
	public void startTimer(final long time) {
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				AlphaBeta.this.stop_flag = true;
			}
		}.start();
	}
}
