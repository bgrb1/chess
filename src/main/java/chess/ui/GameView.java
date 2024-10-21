package chess.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chess.logic.handlers.UIInputHandler;
import chess.data.IconLoader;
import chess.data.Variables;
import chess.model.Board;
import chess.logic.Game;
import chess.model.Square;
import chess.model.pieces.AbstractPiece;
import javax.swing.JTextField;

/**
 * Spielbildschirm Schachbrett 8x8 Schachfiguren Aufgeben-Funktion Link zu
 * Regelwerk
 */

@SuppressWarnings("serial")
public class GameView extends JFrame {
	
	private boolean sound = false;
	
	private final Board board;
	private final IconLoader il;
	private final UIInputHandler handler;
	private final Game game;
	
	private JLabel lblNewLabel;
	private JPanel contentPane;
	private JPanel boardPanel;
	private JTextField txtAmZug;

	public GameView(Board board, UIInputHandler handler, final Game game) throws Exception {
		setTitle("Schach");
		this.board = board;
		this.game = game;
		il = new IconLoader();
		this.handler = handler;
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		setBounds(Variables.adaptToResolution(550), Variables.adaptToResolution(50), Variables.adaptToResolution(860), Variables.adaptToResolution(930));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setBackground(Color.WHITE);
		contentPane.setLayout(null);
		
		boardPanel = new JPanel(){
			@Override
			public void paint(Graphics g){
				paintBoard((Graphics2D) g);
				paintPieces(g);
				highlightSquares((Graphics2D) g);
			}
		};
		boardPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				GameView.this.handler.call(e);
			}
		});
		boardPanel.setBounds(Variables.adaptToResolution(23), Variables.adaptToResolution(11), Variables.adaptToResolution(800), Variables.adaptToResolution(800));
		contentPane.add(boardPanel);
		
		lblNewLabel = new JLabel("Icons designed by Cburnett and jurgenwesterhof from Wikimedia Commons");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(10)));
		lblNewLabel.setBounds(Variables.adaptToResolution(23), Variables.adaptToResolution(807), Variables.adaptToResolution(367), Variables.adaptToResolution(22));
		contentPane.add(lblNewLabel);
		
		txtAmZug = new JTextField();
		txtAmZug.setEditable(false);
		txtAmZug.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(16)));
		txtAmZug.setText(" Wei\u00DF ist am Zug");
		txtAmZug.setBounds(Variables.adaptToResolution(23), Variables.adaptToResolution(832), Variables.adaptToResolution(198), Variables.adaptToResolution(33));
		contentPane.add(txtAmZug);
		txtAmZug.setColumns(10);
		
		btnAufgeben = new JButton("Aufgeben");
		btnAufgeben.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GameView.this.game.surrender();
			}
		});
		btnAufgeben.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(20)));
		btnAufgeben.setBounds(Variables.adaptToResolution(595), Variables.adaptToResolution(827), Variables.adaptToResolution(216), Variables.adaptToResolution(39));
		btnAufgeben.setBackground(Variables.DARK_COLOR);
		contentPane.add(btnAufgeben);
		btnAufgeben.setEnabled(false);
		
		final JButton btnSound = new JButton("");
		btnSound.setBackground(Variables.DARK_COLOR);
		btnSound.setIcon(new ImageIcon(IconLoader.getSoundIcon(false)));
		btnSound.setFocusPainted(false);
		btnSound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound = !sound;
				game.setSound(sound);
				btnSound.setIcon(new ImageIcon(IconLoader.getSoundIcon(sound)));
			}
		});
		btnSound.setBounds(Variables.adaptToResolution(500), Variables.adaptToResolution(822), Variables.adaptToResolution(50), Variables.adaptToResolution(50));
		contentPane.add(btnSound);
		
		txtInfo = new JTextField();
		txtInfo.setEditable(false);
		txtInfo.setText(" Du bist Spieler Schwarz");
		txtInfo.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(16)));
		txtInfo.setBounds(Variables.adaptToResolution(250), Variables.adaptToResolution(832), Variables.adaptToResolution(210), Variables.adaptToResolution(33));
		contentPane.add(txtInfo);
		txtInfo.setColumns(10);
		txtInfo.setVisible(false);
	}
	
	
	public void repaintBoard(){
		boardPanel.repaint();
	}
	public boolean white = true;
	private JButton btnAufgeben;
	private JTextField txtInfo;
	public void next(boolean allowSurrender) {
		white = !white;
		if(white) {
			txtAmZug.setText(" Wei\u00DF ist am Zug");
		} else {
			txtAmZug.setText(" Schwarz ist am Zug");
		}
		btnAufgeben.setEnabled(allowSurrender);
	}
	
	public void showInfoField(boolean white) {
		txtInfo.setVisible(true);
		if(white) {
			txtInfo.setText(" Du bist Spieler Wei�");
		} else {
			txtInfo.setText(" Du bist Spieler Schwarz");
		}
	}
	
	private void paintBoard(Graphics2D g){
		boolean color1 = true;
        for (int i = 0; i < 64; i++) {
            int x = (i % 8) * Variables.SQUARE_SIZE();
            int y = (i / 8) * Variables.SQUARE_SIZE();
            Color color;
            if(color1){
            	color = Variables.LIGHT_COLOR;
            } else {
            	color = Variables.DARK_COLOR;
            }
            Rectangle rect = new Rectangle(x, y, Variables.SQUARE_SIZE(), Variables.SQUARE_SIZE());
            g.setColor(color);
            g.fill(rect);
            if(i % 8 != 7){
                color1 = !color1;            	
            }
        }
	}
	
	private void paintPieces(Graphics g){
		for(AbstractPiece p : board.getAllPieces(false)){
			BufferedImage icon = il.getImage(p.getIconKey());
		    g.drawImage(icon, (p.getPosition().getColumn()-1)*Variables.SQUARE_SIZE(), (8-p.getPosition().getRow())*Variables.SQUARE_SIZE(), Variables.SQUARE_SIZE(), Variables.SQUARE_SIZE(), this);
		}
	}


	
	private void highlightSquares(Graphics2D g){
		for(Square s : board.getAllSquares()){
			if(s.isHighlighted()){
				g.setColor(s.getHighlightColor());
			    BasicStroke bs = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, new float []{10, 10, 10}, 10);
				g.setStroke(bs);
		        Rectangle tile = new Rectangle((s.getColumn()-1)*Variables.SQUARE_SIZE(), (8-s.getRow())*Variables.SQUARE_SIZE(), Variables.SQUARE_SIZE(), Variables.SQUARE_SIZE());
		        g.draw(tile);
			}
		}
	}
}
