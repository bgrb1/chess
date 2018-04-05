package schach.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Startbildschirm enthält Logo Begrüßung Auswahl Einzelspieler/Mehrspieler
 * lokal/Mehrspieler LAN ggf. Hilfestellung
 */
@SuppressWarnings("serial")
public class MainView extends JFrame {

	private JPanel contentPane;

	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
