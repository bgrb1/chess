package schach.domain.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import schach.application.handler.Handler;
import schach.application.handler.NetworkInputHandler;
import schach.application.handler.UIInputHandler;
import schach.application.scanner.CheckScanner;
import schach.application.scanner.CheckmateScanner;
import schach.application.scanner.GameStatus;
import schach.application.scanner.StalemateScanner;
import schach.data.SoundLoader;
import schach.data.network.TimeoutManager;
import schach.data.network.UDPClient;
import schach.ui.GameView;

//Funktionen z.T. in Application-Layer auslagern!


public class Game {
	
	private final ArrayList<Move> allMoves;
	private final Board board;
		
	private Player player1; //WEIß
	private Player player2; //SCHWARZ
	
	private Player current;
	private Player other;
	
	private final Handler h1;
	private final Handler h2;
	private NetworkInputHandler nh;
	
	private final long time_a;
	
	private final GameView gv;
	
	public Game(GameMode mode) throws Exception {
		allMoves = new ArrayList<Move>();
		player1 = new Player(1);
		player2 = new Player(2);
		board = new Board(player1, player2);
		
		if(mode == GameMode.MULTI_LOCAL){
			h1 = new UIInputHandler(board, this);
			h2 = h1;
			(gv = new GameView(board, (UIInputHandler) h1, this)).setVisible(true);
		}  else {
			h1 = new UIInputHandler(board, this);
			h2 = null;
			(gv = new GameView(board, (UIInputHandler) h1, this)).setVisible(true);
		}

		player1.setHandler(h1);
		player2.setHandler(h2);
		current = player1;
		other = player2;
		time_a = System.currentTimeMillis();
		current.pingHandler();
	} 
	private UDPClient udp;
	private Player localPlayer;
	boolean network = false;
	public Game(GameMode mode, UDPClient udp) throws Exception {
		network  = true;
		this.udp = udp;
		allMoves = new ArrayList<Move>();
		player1 = new Player(1);
		player2 = new Player(2);
		board = new Board(player1, player2);
		
		if(mode == GameMode.MULTI_LAN_BLACK) {
			h1 = new NetworkInputHandler(board, this);
			h2 = new UIInputHandler(board, this);
			(gv = new GameView(board, (UIInputHandler) h2, this)).setVisible(true);
			nh = (NetworkInputHandler) h1;
			localPlayer = player2;
			gv.showInfoField(false);
		} else {
			h1 =  new UIInputHandler(board, this);
			h2 = new NetworkInputHandler(board, this);
			(gv = new GameView(board, (UIInputHandler) h1, this)).setVisible(true);
			nh = (NetworkInputHandler) h2;
			localPlayer = player1;
			gv.showInfoField(true);
		} 

		player1.setHandler(h1);
		player2.setHandler(h2);
		current = player1;
		other = player2;
		time_a = System.currentTimeMillis();
		current.pingHandler();
	} 
	
	private boolean kingMarked = false;
	private Square marked = null;
	public boolean registerMove(Move move, boolean me) {
		GameStatus gs_ch = CheckScanner.scan(board, current, other);
		if(current.equals(player1) && gs_ch == GameStatus.CHECK_PLAYER1 || current.equals(player2) && gs_ch == GameStatus.CHECK_PLAYER2) { //Zug prüfen
			if(me) {
				move.revert();
				gv.repaintBoard();
				current.pingHandler();
			} else {
				//gehackter Client
			}
			return false;
		} else {
			if(network && me) {
				udp.sendMove(move);
			}
		}
		if(kingMarked) {
			marked.unhighlight();
			kingMarked = false;
		}
		allMoves.add(move);
		gv.repaintBoard();
		if(move.getHitFlag() && sound) {
			SoundLoader.playHitSound();
		} else if (!move.getHitFlag() && sound) {
			SoundLoader.playMoveSound();
		}
		if (current.equals(player2) && gs_ch == GameStatus.CHECK_PLAYER1) {
			GameStatus sr2 = CheckmateScanner.scan(board, player1, player2);
			if(sr2 == GameStatus.CHECKMATE_PLAYER1) {
				end(player2, false);
			}
		} else if (current.equals(player1) && gs_ch == GameStatus.CHECK_PLAYER2) {
			GameStatus gs_chm = CheckmateScanner.scan(board, player2, player1);
			if(gs_chm == GameStatus.CHECKMATE_PLAYER2) {
				end(player1, false);
			}
		}
		if(gs_ch == GameStatus.NOTHING) {
			GameStatus gs_sm = StalemateScanner.scan(board, current, other);
			if(gs_sm == GameStatus.STALEMATE) {
				endStalemate();
			}
		} else {
			if(gs_ch == GameStatus.CHECK_PLAYER1) {
				marked = player1.getKing().getPosition();
				marked.highlight(new Color(255, 94, 1));
				kingMarked = true;
			} else if(gs_ch == GameStatus.CHECK_PLAYER2) {
				marked = player2.getKing().getPosition();
				marked.highlight(Color.ORANGE);
				kingMarked = true;
			}
		}
		gv.repaintBoard();
		next();
		return true;
	}
	
	public Board getBoard(){
		return board;
	}
	public NetworkInputHandler getNetworkHandler() { 
		return nh;
	}
	private boolean sound = false;
	public void setSound(boolean on) {
		sound = on;
	}
	
	private void next(){ 
		if(current.equals(player1)){
			current = player2;
			other = player1;
		} else {
			current = player1;
			other = player2;
		}
		current.pingHandler();
		if(network && !localPlayer.equals(current)) {
			gv.next(false);
		} else {
			gv.next(true);
		}
	}
	
	public void surrender(boolean me) {
		if(network && me) {
			udp.surrender();
		} else if(network && !me && current.equals(localPlayer)) {
			return;
		}
		end(other, true);
	}
	
	public void end(Player winner, boolean surrendered){
		TimeoutManager.shutdownAll();
		long time = System.currentTimeMillis() - time_a;
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
		TimeoutManager.shutdownAll();
		long time = System.currentTimeMillis() - time_a;
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
	
	public GameView getGameView(){
		return gv;
	}
	
}
