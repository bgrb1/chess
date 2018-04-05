package schach.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Spielbildschirm Schachbrett 8x8 Schachfiguren Aufgeben-Funktion Link zu
 * Regelwerk
 */

@SuppressWarnings("serial")
public class GameView extends JFrame {

	private JPanel contentPane;

	public GameView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
