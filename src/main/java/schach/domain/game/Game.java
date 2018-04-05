package schach.domain.game;

import java.util.ArrayList;

import schach.domain.actions.Action;

public class Game {
	
	private final ArrayList<Square> squares;
	
	public Game(){
		squares = new ArrayList<Square>();
	}
	
	public Board getBoard(){
		return null;
	}
	
/*	public Square getField(byte horizontal, byte vertical){
		for(Square square : squares){
			if(square.getHorizontalOrdinate() == horizontal && square.getVerticalOrdinate() == vertical){
				return square;
			}
		}
		return null;
	
	
	public void registerAction(Action action){
		
	}
	

}
