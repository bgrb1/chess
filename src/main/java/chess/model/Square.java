package chess.model;

import java.awt.Color;

import chess.model.pieces.AbstractPiece;

public class Square {
	
	public Square(int column, int row, Color color){
		this.row = row;
		this.column = column;
		this.color = color;
	}
	
	private Color highlightColor;
	private boolean highlight = false;
	private final Color color;
	private final int row; // 1-8
	private final int column; // A-E
	
	private AbstractPiece piece;

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public AbstractPiece getPiece(){
		return piece;
	}
	
	public boolean isEmpty(){
		if(piece == null){
			return true;
		} else {
			return false;
		}
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setPiece(AbstractPiece piece){
		this.piece = piece;
	}
	public void clear(){
		piece = null;
	}
	public void highlight(Color color){
		highlightColor = color;
		highlight = true;
	}
	public void unhighlight(){
		highlight = false;
	}
	public boolean isHighlighted(){
		return highlight;
	}
	public Color getHighlightColor(){
		return highlightColor;
	}
	
	@Override
	public String toString(){
		char columnChar = 'a';
		switch(column){
		case 1 : columnChar = 'a'; break;
		case 2 : columnChar = 'b'; break;
		case 3 : columnChar = 'c'; break;
		case 4 : columnChar = 'd'; break;
		case 5 : columnChar = 'e'; break;
		case 6 : columnChar = 'f'; break;
		case 7 : columnChar = 'g'; break;
		case 8 : columnChar = 'h'; break;
		}
		return new String(columnChar + "" + row);
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof Square){
			Square s2 = (Square) o;
			if(this.getColumn() == s2.getColumn() && this.getRow() == s2.getRow()){
				return true;
			}
		}
		return false;
	}

}
