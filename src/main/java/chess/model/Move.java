package chess.model;

import chess.logic.scanners.CheckScanner;
import chess.logic.scanners.GameStatus;
import chess.model.pieces.AbstractPiece;
import chess.model.pieces.Pawn;
import chess.model.pieces.Queen;

public class Move {
	
	private final Square origin;
	private final Square destination;
	private final AbstractPiece piece;
	private final Player player;

	private final Board board;
			
	private boolean capture_flag = false;
	private AbstractPiece capturedPiece;

	
	private boolean promotion_flag = false;
	private Queen promotionPiece;

		
	public Move(Board board, Square origin, Square destination){
		this.origin = origin;
		this.destination = destination;
		this.board = board;
		piece = origin.getPiece();
		player = origin.getPiece().getPlayer();
	}

	public Square getOrigin() {
		return origin;
	}
	public Square getDestination() {
		return destination;
	}
	public AbstractPiece getMovedPiece() {
		return piece;
	}
	public AbstractPiece getCapturedPiece() {
		return capturedPiece;
	}
	public boolean getCaptureFlag() {
		return capture_flag;
	}
	
	public void perform(){
		if(!destination.isEmpty()){ //Capture
			capturedPiece = destination.getPiece();
			capturedPiece.capture(board);
			capture_flag = true;
		}

		piece.move(destination);

		if((destination.getRow() == 8 || destination.getRow() == 1) && piece instanceof Pawn) { //Promotion
			promotion_flag = true;
			piece.capture(board);
			promotionPiece = new Queen(piece.getPlayer(), destination);
			board.registerPiece(promotionPiece);
		}
	}
	
	public void revert(){
		if(promotion_flag) { //Revert promotion
			promotionPiece.capture(board);
			piece.restore(board, origin);
			board.deletePiece(promotionPiece);
		}

		piece.moveBack(origin);

		if(capture_flag) { //Revert capture
			capturedPiece.restore(board, destination);
		}

	}

	/**
	 * check whether a move is possible
	 * @param checkForCheck if true, also check whether the move would put the own king into check (if so, the move is illegal)
	 *                      if false, this will also allow pseudo-legal moves
	 * @return whether the move is allowed
	 */
	public boolean isValid(boolean checkForCheck) {

		if(origin.equals(destination)){
			return false;
		}
		AbstractPiece piece = origin.getPiece();
		if(piece.canReach(board, destination)){
			Square [] path = piece.getPath(board, destination);
			for(int x = 0; x < path.length-1; x++){
				if(!path[x].isEmpty()){
					return false;
				}
			}
			if(checkForCheck) {
				perform();
				GameStatus checkScanResult = CheckScanner.applyScan(board);
				if((checkScanResult == GameStatus.CHECK_PLAYER1 && player.getID() == 1) || (checkScanResult == GameStatus.CHECK_PLAYER2 && player.getID() == 2)) {
					revert();
					return false;
				}
				revert();
			}
			return true;
		} else {
			return false;
		}
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(piece.getIconKey().charAt(0));
		sb.append(origin.toString());
		if(capture_flag){
			sb.append('x');
		} else {
			sb.append('-');
		}
		sb.append(destination.toString());
		return sb.toString();
	}


	@Override
	public boolean equals(Object b) {
		if(b instanceof Move) {
			Move move = (Move)b;
			if(move.getOrigin().equals(getOrigin()) && move.getDestination().equals(getDestination()) && move.getMovedPiece().equals(getMovedPiece()) && move.capture_flag == capture_flag) {
				return true;
			} 
		}
		return false;
	}
		
}
