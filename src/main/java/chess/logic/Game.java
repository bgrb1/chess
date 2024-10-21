package chess.logic;

import chess.logic.handlers.Handler;
import chess.logic.handlers.UIInputHandler;
import chess.logic.handlers.ai.AIHandler;
import chess.logic.scanners.CheckScanner;
import chess.logic.scanners.CheckmateScanner;
import chess.logic.scanners.DrawScanner;
import chess.logic.scanners.GameStatus;
import chess.model.Board;
import chess.model.Move;
import chess.model.Player;
import chess.model.Square;
import chess.ui.GameView;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;


public class Game {

	private final Board board;

	private final Player player1;
	private final Player player2;

	private Player current;
	private Player other;


	private final long time_start;

	private final GameView gv;

	private final GameMode mode;

	public Game(GameMode mode) throws Exception {
		this.mode = mode;
		player1 = new Player(1);
		player2 = new Player(2);
		board = new Board(this);
		UIInputHandler h1 = new UIInputHandler(board, this);
		Handler h2 = mode == GameMode.PvP ? h1 : new AIHandler(board);
		player1.setHandler(h1);
		player2.setHandler(h2);
		DrawScanner.init();

		(gv = new GameView(board, h1, this)).setVisible(true);
		current = player1;
		other = player2;
		time_start = System.currentTimeMillis();
		current.ping();
	}



	public boolean registerMove(Move move) {
		for(Square s : board.getAllSquares()) {
			s.unhighlight();
		}
		gv.repaintBoard();
		checkEnd(move);
		next();
		return true;
	}

	public void checkEnd(Move move){
		GameStatus isCheck = CheckScanner.applyScan(board);

		if (isCheck == GameStatus.CHECK_PLAYER1 && current.equals(player2)){
			GameStatus checkmateP1 = CheckmateScanner.scan(board, player1, player2);
			if(checkmateP1 == GameStatus.CHECKMATE_PLAYER1) {
				end(player2, false);
			} else {
				Square marked = player1.getKing().getPosition();
				marked.highlight(Color.ORANGE);
			}
		}

		else if (isCheck == GameStatus.CHECK_PLAYER2 && current.equals(player1)){
			GameStatus checkmateP2 = CheckmateScanner.scan(board, player2, player1);
			if(checkmateP2 == GameStatus.CHECKMATE_PLAYER1) {
				end(player1, false);
			} else {
				Square marked = player2.getKing().getPosition();
				marked.highlight(Color.ORANGE);
			}
		}

		else if (isCheck == GameStatus.NOTHING){
			GameStatus gs_draw = DrawScanner.check(board, move);
			if(gs_draw == GameStatus.STALEMATE) {
				endStalemate();
			} else if(gs_draw == GameStatus.DRAW) {
				endDraw();
			}
		}
	}



	public void next(){
		if(current.equals(player1)){
			current = player2;
			other = player1;
		} else {
			current = player1;
			other = player2;
		}
		if(mode == GameMode.PvE) {
			gv.update(gv.getGraphics());
		} else {
			gv.repaintBoard();
		}
		current.ping();
	}

	public void surrender() {
		end(other, true);
	}


	public final Player getPlayer(int id) {
		if(id == 1) {
			return player1;
		} else {
			return player2;
		}
	}
	public final Player getPlayer(boolean isCurrentPlayer) {
		if(isCurrentPlayer) {
			return current;
		} else {
			return other;
		}
	}

	public Board getBoard(){
		return board;
	}
	private boolean sound = false;

	public void setSound(boolean on) {
		sound = on;
	}

	public void end(Player winner, boolean surrendered){
		long time = System.currentTimeMillis() - time_start;
		String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(time),
				TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
				TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
		StringBuilder builder = new StringBuilder();
		if(winner.getID() == 1) {
			builder.append("Weiß");
		} else {
			builder.append("Schwarz");
		}
		builder.append(" hat nach ");
		builder.append(hms);
		if(surrendered) {
			builder.append(" durch Aufgabe");
		}
		builder.append(" gewonnen");
		JOptionPane.showMessageDialog(null, builder.toString(), "Ende", JOptionPane.INFORMATION_MESSAGE);
		gv.dispose();
		System.exit(0);
	}

	public void endStalemate() {
		long time = System.currentTimeMillis() - time_start;
		String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(time),
				TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
				TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
		StringBuilder builder = new StringBuilder();
		builder.append("Patt nach ");
		builder.append(hms);
		JOptionPane.showMessageDialog(null, builder.toString(), "Ende", JOptionPane.INFORMATION_MESSAGE);
		gv.dispose();
		System.exit(0);
	}

	public void endDraw() {
		long time = System.currentTimeMillis() - time_start;
		String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(time),
				TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
				TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
		StringBuilder builder = new StringBuilder();
		builder.append("Remis nach ");
		builder.append(hms);
		JOptionPane.showMessageDialog(null, builder.toString(), "Ende", JOptionPane.INFORMATION_MESSAGE);
		gv.dispose();
		System.exit(0);
	}


	public GameView getGameView() {
		return gv;
	}
}
