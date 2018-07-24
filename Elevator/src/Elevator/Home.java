package Elevator;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Home extends JFrame {

	private JPanel contentPane;
	ImageIcon icon1;
	JButton button1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
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
	public Home() {
		icon1 = new ImageIcon("friends\\Developer.png");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);

		Image originImg1 = icon1.getImage();
		Image changedImg1 = originImg1.getScaledInstance(982, 753, Image.SCALE_SMOOTH);
		ImageIcon Icon1 = new ImageIcon(changedImg1);
		button1 = new JButton(Icon1);
		button1.setBorderPainted(false);
		button1.setContentAreaFilled(false);
		button1.setBorderPainted(false);
		button1.setFocusPainted(false);
		button1.setBounds(0, 0, 982, 753);
		contentPane.add(button1);
		button1.addActionListener(new ActionListener() {
			int check = 0;

			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if (obj.equals(button1)) {
					if (check == 0) {
						new Main();
						setVisible(false);
					}
					check++;
				}
			}
		});
	}
}