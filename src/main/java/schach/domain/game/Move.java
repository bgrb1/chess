package schach.domain.game;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import schach.application.scanner.GameStatus;
import schach.domain.pieces.AbstractPiece;

@SuppressWarnings("unused")
public class Move {
	
	private final Square origin;
	private final Square destination;
	private final AbstractPiece movedPiece;
	private AbstractPiece capturedPiece;
	private GameStatus checkScanResult;
	

	private final Board board;
			
	
	// Flags für die Übertragung in Schachnotation
	private boolean hit_flag = false; 
	private boolean enpassant_flag = false;
	private boolean promotion_flag = false;
	private char promotion_char = '\0';
		
	public Move(Board board, Square origin, Square destination){
		this.origin = origin;
		this.destination = destination;
		this.board = board;
		movedPiece = origin.getPiece();
	}
	
	public Square getOrigin() {
		return origin;
	}
	public Square getDestination() {
		return destination;
	}
	public boolean getHitFlag() {
		return hit_flag;
	}
	
	public void perform(){ // Zug ausführen
		if(!destination.isEmpty()){
			capturedPiece = destination.getPiece();
			destination.getPiece().getPlayer().unregisterPiece(destination.getPiece());
			board.unregisterPiece(destination.getPiece());
			hit_flag = true;
		}		
		origin.clear();
		destination.setPieceHere(movedPiece);
		movedPiece.move(destination);
	}
	
	public void revert(){ // Zug zurücksetzen, wichtig für die AI und Scanner
		destination.clear();
		origin.setPieceHere(movedPiece);
		movedPiece.moveBackTo(origin);
		if(hit_flag) {
			destination.setPieceHere(capturedPiece);
			capturedPiece.move(destination);
			capturedPiece.getPlayer().registerPiece(capturedPiece);
			board.registerPiece(capturedPiece);
		}
	}

	public boolean isValid() { // Überprüft ob der Zug zulässig/regelkonform ist
		if(origin.equals(destination)){
			return false;
		}
		AbstractPiece piece = origin.getPiece();
		Player player = piece.getPlayer();
		if(piece.canReach(board, destination)){
			Square [] path = piece.getPath(board, destination);
			boolean valid = true;
			for(int x = 0; x < path.length-1; x++){
				if(!path[x].isEmpty()){
					valid = false;
				}
			}
			return valid;
		}
		return false;
	}
	
	public Move copy(Board board) {
		return new Move(board, this.origin, this.destination);
	}

	@Override
	public String toString() { // Wandelt Zug in Schachnotation um
		StringBuilder sb = new StringBuilder();
		sb.append(movedPiece.getIconKey().charAt(0));
		sb.append(origin.toString());
		if(hit_flag){
			sb.append('x');
		} else {
			sb.append('-');
		}
		sb.append(destination.toString());
		return sb.toString();
	}

	public static Move fromString(String string, Board board) throws IllegalArgumentException { // Stellt einen Zug aus der Schachnotation her, wichtig für den Netzwerkmodus
		String regex = "^([QKRNPB])([a-h][1-8])([-x])([a-h][1-8])$";
		Pattern pattern = Pattern.compile(regex);

		Matcher m = pattern.matcher(string);
		if(!m.find()) {
			throw new IllegalArgumentException("Invalid Chess Notation");
		}
		String originString = m.group(2);
		String destinationString = m.group(4);
		Square origin = getSquareFromString(originString, board);
		Square destination = getSquareFromString(destinationString, board);

		return new Move(board, origin, destination);
	}
	
	private static Square getSquareFromString(String string, Board board) {
		char [] c = string.toCharArray();
		int column = 1;
		switch(c[0]) {
		case 'a' : column = 1; break;
		case 'b' : column = 2; break;
		case 'c' : column = 3; break;
		case 'd' : column = 4; break;
		case 'e' : column = 5; break;
		case 'f' : column = 6; break;
		case 'g' : column = 7; break;
		case 'h' : column = 8; break;
		}
		return board.getSquare(column, Character.getNumericValue(c[1]));
	}
		
}
