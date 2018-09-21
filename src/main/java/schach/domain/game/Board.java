package schach.domain.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

import schach.data.Variables;
import schach.domain.pieces.AbstractPiece;
import schach.domain.pieces.Bishop;
import schach.domain.pieces.King;
import schach.domain.pieces.Knight;
import schach.domain.pieces.Pawn;
import schach.domain.pieces.Queen;
import schach.domain.pieces.Rook;

public class Board {
	
	public ArrayList<Board> allBoards = new ArrayList<Board>();
	private Square [] squares  = new Square[64];
	private LinkedList<AbstractPiece> pieces  = new LinkedList<AbstractPiece>();

	
	public Board(Player player1, Player player2){
		createSquareObjects();
		createPieceObjects(player1, player2);
	}
	
	public Square getSquare(int column, int row){
		for(Square s : squares){
			if(s.getColumn() == column && s.getRow() == row){
				return s;
			}
		}
		return null;
	}
	
	public void registerPiece(AbstractPiece piece){
		pieces.add(piece);
	}
	public void unregisterPiece(AbstractPiece piece){
		pieces.remove(piece);
	}
	public Square [] getAllSquares(){
		return squares;
	}
	public LinkedList<AbstractPiece>getAllPieces(){
		return pieces;
	}
	private void createSquareObjects(){
		boolean color1 = true;
		for(int y = 1; y <= 8; y++){
			for(int x = 1; x <= 8; x++){
				Color color;
		        if(color1){
		        	color = Variables.LIGHT_COLOR;
		         } else {
		        	 color = Variables.DARK_COLOR;
		         }
		         if(x != 8){
		        	 color1 = !color1;            	
		         }
		         squares[(y-1)*8 + x-1] = new Square(x, y, color);
			}
		}
	
	}
	
	private void createPieceObjects(Player player1, Player player2){
		//Bauern
		for(int x = 1; x <= 8; x++){ //Weiß
			registerPiece(new Pawn(player1, getSquare(x, 2)));
		}
		for(int x = 1; x <= 8; x++){ //Schwarz
			registerPiece(new Pawn(player2, getSquare(x, 7)));
		}
		//Könige
		registerPiece(new King(player1, getSquare(5, 1))); //Weiß
		registerPiece(new King(player2, getSquare(5, 8))); //Schwarz
		//Damen
		registerPiece(new Queen(player1, getSquare(4, 1))); //Weiß
		registerPiece(new Queen(player2, getSquare(4, 8))); //Schwarz
		//Läufer
		registerPiece(new Bishop(player1, getSquare(3, 1))); //Weiß
		registerPiece(new Bishop(player1, getSquare(6, 1)));
		registerPiece(new Bishop(player2, getSquare(3, 8))); //Schwarz
		registerPiece(new Bishop(player2, getSquare(6, 8))); 
		//Springer
		registerPiece(new Knight(player1, getSquare(2, 1))); //Weiß 
		registerPiece(new Knight(player1, getSquare(7, 1)));
		registerPiece(new Knight(player2, getSquare(2, 8))); //Schwarz
		registerPiece(new Knight(player2, getSquare(7, 8))); 
		//Türme
		registerPiece(new Rook(player1, getSquare(1, 1))); //Weiß
		registerPiece(new Rook(player1, getSquare(8, 1))); 
		registerPiece(new Rook(player2, getSquare(1, 8))); //Schwarz
		registerPiece(new Rook(player2, getSquare(8, 8))); //Schwarz 
	}
}
