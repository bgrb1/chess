package schach.application.scanner;

import schach.domain.game.Board;

public interface Scanner {
	
	public GameStatus scan(Board board);

}
