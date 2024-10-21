package chess.logic.scanners;

import chess.logic.scanners.drawrules.DeadPositionRule;
import chess.logic.scanners.drawrules.NoCaptureRule;
import chess.logic.scanners.drawrules.StalemateRule;
import chess.model.Board;
import chess.model.Move;

public class DrawScanner {
	
	private static DeadPositionRule a;
	private static NoCaptureRule b;
	private static StalemateRule d;
	
	public static void init() {
		a = new DeadPositionRule();
		b = new NoCaptureRule();
		d = new StalemateRule();
	}

	public static GameStatus check(Board board, Move lastMove) {
		if(d.check(lastMove, board)) {
			return GameStatus.STALEMATE;
		}
		if(a.check(lastMove, board) || b.check(lastMove, board)) {
			return GameStatus.DRAW;
		}
		return GameStatus.NOTHING;
	}

}
