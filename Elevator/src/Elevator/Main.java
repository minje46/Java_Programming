package Elevator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
	public static void main(String[] args) {
	}

	Main() {
		JFrame frm = new JFrame("ÁÖÇüÁø_°û¹ÎÁ¦_ÀÌ»ó¿ø_ÀÌ¼ÒÀ±");
		Building cPanel = new Building();

		ElevatorHandler elev_handler = new ElevatorHandler(cPanel);
		FloorPanel fPanel = new FloorPanel(cPanel, elev_handler);
		frm.setBounds(10, 10, 700, 665);
		frm.setLayout(new BorderLayout());
		frm.add(fPanel, BorderLayout.EAST);
		frm.add(cPanel, BorderLayout.CENTER);
		frm.setVisible(true);
		frm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}