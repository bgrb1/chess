package chess.logic.handlers.ai;

import java.util.ArrayList;
import java.util.List;

import chess.model.Move;

public class EvaluatedMove {
	
	private Move move;
	private int eval;
	
	public EvaluatedMove(Move move) {
		this.move = move;
	}
	
	protected Move getMove() {
		return move;
	}
	protected int getEvaluation() {
		return eval;
	}
	protected void setEvaluation(int eval) {
		this.eval = eval;
	}
	
	public static List<EvaluatedMove> transformList(List<Move> moves){
		List<EvaluatedMove> buffer =  new ArrayList<EvaluatedMove>();
		for(Move m : moves) {
			buffer.add(new EvaluatedMove(m));
		}
		return buffer;
	}

}
