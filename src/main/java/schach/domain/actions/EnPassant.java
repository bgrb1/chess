package schach.domain.actions;

import schach.domain.game.Square;

public class EnPassant extends Move implements Action {

	public EnPassant(Square origin, Square destination) {
		super(origin, destination);
		// TODO Auto-generated constructor stub
	}

	public void perform() {
		// TODO Auto-generated method stub
		
	}

	public void undo() {
		// TODO Auto-generated method stub
		
	}

	public boolean check() {
		// TODO Auto-generated method stub
		return false;
	}

	public String toChessNotation() {
		// TODO Auto-generated method stub
		return null;
	}

}
