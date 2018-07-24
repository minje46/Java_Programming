package Elevator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FloorPanel extends JPanel {
	public FloorPanel(Building cPanel, ElevatorHandler elev_handler) {
		setSize(0, 0);
		setLayout(new GridLayout(10, 1));
		JButton[] btn = new JButton[10];

		for (int i = 0; i < 10; i++) {
			btn[i] = new JButton(new ImageIcon("button\\" + (10 - i) + "F.png"));
			btn[i].setBorderPainted(false);
			btn[i].setPreferredSize(new Dimension(60, 60));
		}
		for (int i = 0; i < 10; i++)
			btn[i].addActionListener(new StartButtonHandler(10 - i, cPanel, elev_handler));
		for (int i = 0; i < 10; i++)
			add(btn[i]);
	}
}
