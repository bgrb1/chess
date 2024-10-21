package chess.logic.scanners.drawrules;

import chess.model.Board;
import chess.model.Move;

public class NoCaptureRule implements Rule {
	
	int counter = 0;

	public boolean check(Move lastMove, Board board) {
		if(lastMove.getCaptureFlag()) {
			counter = 0;
		} else {
			counter ++;
		}
		if(counter < 50) {
			return false;
		} else {
			return true;
		}
	}

}
