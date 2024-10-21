package chess.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chess.data.Variables;
import chess.logic.Game;
import chess.logic.GameMode;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



@SuppressWarnings("serial")
public class MainView extends JFrame {

	private JPanel contentPane;
	

	public static void main(String[] args){
		new MainView().setVisible(true);
	}

	public MainView() {
		setResizable(false);
		setTitle("Schach");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Variables.adaptToResolution(Variables.adaptToResolution(300)), Variables.adaptToResolution(165), Variables.adaptToResolution(1154), Variables.adaptToResolution(647));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setBackground(Variables.LIGHT_COLOR);
		contentPane.setLayout(null);
		
		JButton btnSingleplayerVsComputer = new JButton("Einzelspieler vs. Computer");
		btnSingleplayerVsComputer.setEnabled(true);
		btnSingleplayerVsComputer.setFocusPainted(false);
		btnSingleplayerVsComputer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new Game(GameMode.PvE);
					dispose();
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("Critical Exception while initializing new game!");
				}
			}
		});
		btnSingleplayerVsComputer.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(27)));
		btnSingleplayerVsComputer.setBounds(Variables.adaptToResolution(273), Variables.adaptToResolution(139), Variables.adaptToResolution(592), Variables.adaptToResolution(94));
		btnSingleplayerVsComputer.setBackground(Variables.DARK_COLOR);
		contentPane.add(btnSingleplayerVsComputer);
		
		JButton btnMultiplayerLokal = new JButton("Mehrspieler 1 PC");
		btnMultiplayerLokal.setFocusPainted(false);
		btnMultiplayerLokal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new Game(GameMode.PvP);
					dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
					System.err.println("Critical Exception while initializing new game!");
				}
			}
		});
		btnMultiplayerLokal.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(27)));
		btnMultiplayerLokal.setBounds(Variables.adaptToResolution(273), Variables.adaptToResolution(254), Variables.adaptToResolution(592), Variables.adaptToResolution(94));
		btnMultiplayerLokal.setBackground(Variables.DARK_COLOR);
		contentPane.add(btnMultiplayerLokal);

	}
}
