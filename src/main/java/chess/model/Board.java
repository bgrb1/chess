package chess.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import chess.data.Variables;
import chess.logic.Game;
import chess.model.pieces.AbstractPiece;
import chess.model.pieces.Bishop;
import chess.model.pieces.King;
import chess.model.pieces.Knight;
import chess.model.pieces.Pawn;
import chess.model.pieces.Queen;
import chess.model.pieces.Rook;

public class Board {
	
	private final Square [] squares  = new Square[64];
	private final List<AbstractPiece> pieces  = new ArrayList<AbstractPiece>();

	private final Game game;
	
	public Board(Game game){
		this.game = game;
		createSquareObjects();
		createPieceObjects(game.getPlayer(1), game.getPlayer(2));
	}
	
	public Square getSquare(int column, int row){
		for(Square s : squares){
			if(s.getColumn() == column && s.getRow() == row){
				return s;
			}
		}
		return null;
	}
	
	public final Game getGame() {
		return game;
	}
	
	public void registerPiece(AbstractPiece piece){
		pieces.add(piece);
	}
	public void deletePiece(AbstractPiece piece){
		pieces.remove(piece);
	}

	public Square [] getAllSquares(){
		return squares;
	}
	public List<AbstractPiece> getAllPieces(boolean includeCaptured){
		if(!includeCaptured) {
			List<AbstractPiece> buffer = new ArrayList<AbstractPiece>();
			for(AbstractPiece piece : pieces) {
				if(!piece.isCaptured()) {
					buffer.add(piece);
				}
			}
			return buffer;
		} else {
			return pieces;
		}
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
		for(int x = 1; x <= 8; x++){ //Wei�
			registerPiece(new Pawn(player1, getSquare(x, 2)));
		}
		for(int x = 1; x <= 8; x++){ //Schwarz
			registerPiece(new Pawn(player2, getSquare(x, 7)));
		}
		//K�nige
		registerPiece(new King(player1, getSquare(5, 1))); //Wei�
		registerPiece(new King(player2, getSquare(5, 8))); //Schwarz
		//Damen
		registerPiece(new Queen(player1, getSquare(4, 1))); //Wei�
		registerPiece(new Queen(player2, getSquare(4, 8))); //Schwarz
		//L�ufer
		registerPiece(new Bishop(player1, getSquare(3, 1))); //Wei�
		registerPiece(new Bishop(player1, getSquare(6, 1)));
		registerPiece(new Bishop(player2, getSquare(3, 8))); //Schwarz
		registerPiece(new Bishop(player2, getSquare(6, 8))); 
		//Springer
		registerPiece(new Knight(player1, getSquare(2, 1))); //Wei� 
		registerPiece(new Knight(player1, getSquare(7, 1)));
		registerPiece(new Knight(player2, getSquare(2, 8))); //Schwarz
		registerPiece(new Knight(player2, getSquare(7, 8))); 
		//T�rme
		registerPiece(new Rook(player1, getSquare(1, 1))); //Wei�
		registerPiece(new Rook(player1, getSquare(8, 1))); 
		registerPiece(new Rook(player2, getSquare(1, 8))); //Schwarz
		registerPiece(new Rook(player2, getSquare(8, 8))); //Schwarz 
	}
	
}
