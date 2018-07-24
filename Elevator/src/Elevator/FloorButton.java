package Elevator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class FloorButton extends JFrame {
	Building cPanel;
	ElevatorHandler elev_handler;
	JButton[] btn;
	JPanel pathPanel;
	JLabel startLabel, arrowLabel, desLabel;
	private JPanel panel;

	public FloorButton(String title, int curF, Building cPanel, ElevatorHandler elev_handler) {
		super(title);
		this.cPanel = cPanel;
		this.elev_handler = elev_handler;

		ImageIcon icon1 = new ImageIcon("friends\\arrow.png");

		btn = new JButton[10];
		for (int i = 0; i < 10; i++) {
			btn[i] = new JButton(new ImageIcon("button\\f" + (i + 1) + ".png"));
			btn[i].setBackground(Color.red);
			btn[i].setBorderPainted(false);
			btn[i].setContentAreaFilled(false);
			btn[i].setPressedIcon(new ImageIcon("button\\f" + (i + 1) + "_on.png"));
		}

		setBounds(710, 10, 400, 500);

		getContentPane().setLayout(null);
		Border border = BorderFactory.createEtchedBorder();

		panel = new JPanel() {
			public void paintComponent(Graphics g) {
				Dimension d = getSize();
				g.drawImage(new ImageIcon("friends/button_panel.png").getImage(), 0, 0, 400, 460, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		panel.setLayout(null);
		panel.setBounds(0, 0, 381, 453);
		getContentPane().add(panel);
		JPanel bPanel = new JPanel();
		bPanel.setBounds(30, 104, 155, 337);
		panel.add(bPanel);
		bPanel.setLayout(new GridLayout(5, 2));
		bPanel.setPreferredSize(new Dimension(130, 350));
		pathPanel = new JPanel();
		pathPanel.setBounds(217, 104, 150, 338);
		panel.add(pathPanel);
		pathPanel.setLayout(null);
		pathPanel.setPreferredSize(new Dimension(130, 350));

		startLabel = new JLabel(" ");
		startLabel.setBounds(53, 40, 70, 70);
		startLabel.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 50));
		Image originImg1 = icon1.getImage();
		Image changedImg1 = originImg1.getScaledInstance(78, 92, Image.SCALE_SMOOTH);
		ImageIcon Icon1 = new ImageIcon(changedImg1);

		arrowLabel = new JLabel(Icon1);
		arrowLabel.setBounds(30, 110, 78, 92);
		desLabel = new JLabel(" ");
		desLabel.setBounds(50, 210, 70, 70);
		desLabel.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 50));
		pathPanel.add(startLabel);
		pathPanel.add(arrowLabel);
		pathPanel.add(desLabel);
		// button action listener
		for (int i = 0; i < 10; i++) {
			btn[i].addActionListener(new DestinationButtonHandler(curF, i + 1, this, elev_handler));
		}

		for (int i = 0; i < 10; i++) {
			if (i + 1 == curF)
				continue;
			else
				bPanel.add(btn[i]);
		}

		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
