package schach.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	int i = 1;
	private JPanel panel;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame frame = new TestFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 894, 531);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel(){
			@Override
			public void paint(Graphics g){
				bla(g);
			}
		};
		panel.setBounds(31, 28, 484, 369);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel(){
			@Override
			public void paint(Graphics g){
				bla(g);
			}
		};
		panel_1.setBounds(556, 46, 232, 344);
		contentPane.add(panel_1);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.repaint();
			}
		});
		btnNewButton.setBounds(76, 458, 89, 23);
		contentPane.add(btnNewButton);
	}
	
	
	public void bla(Graphics g){
		if(i == 0 || i == 1){
			g.drawOval(50, 50, 50, 50);
			i++;
		} else {
			g.drawOval(50, 50, 100, 100);
		}
	}
}
