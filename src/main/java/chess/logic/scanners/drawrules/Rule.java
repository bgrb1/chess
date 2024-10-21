package chess.logic.scanners.drawrules;

import chess.model.Board;
import chess.model.Move;

public interface Rule {
	
	
	public boolean check(Move lastMove, Board board);

}
