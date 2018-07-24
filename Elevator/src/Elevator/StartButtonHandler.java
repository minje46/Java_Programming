package Elevator;

import java.awt.event.*;
import javax.swing.*;

public class StartButtonHandler implements ActionListener {
	int current;
	Building building;
	ElevatorHandler elev_handler;

	public StartButtonHandler(int curF, Building cPanel, ElevatorHandler elev_handler) {
		this.current = curF;
		this.building = cPanel;
		this.elev_handler = elev_handler;
	}

	public void actionPerformed(ActionEvent e) {
		FloorButton sbtn = new FloorButton("Floor " + current, current, building, elev_handler);
	}
}