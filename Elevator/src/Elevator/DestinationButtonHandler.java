package Elevator;

import java.awt.event.*;
import javax.swing.*;

public class DestinationButtonHandler implements ActionListener {
	int source;
	int destination;
	ElevatorHandler elev_handler;
	FloorButton sb;

	public DestinationButtonHandler(int source, int destination, FloorButton sb, ElevatorHandler elev_handler) {
		this.source = source;
		this.destination = destination;
		this.elev_handler = elev_handler;
		this.sb = sb;
	}

	public void actionPerformed(ActionEvent e) {
		sb.btn[destination - 1].setIcon(new ImageIcon("button\\f" + destination + "_on.png"));
		char e_name = elev_handler.moving(source, destination, sb);
		int idx;

		if (e_name == 'A') {
			idx = 0;
			elev_handler.elev[0].getDesButton(sb.btn[destination - 1], source, destination);
		} else if (e_name == 'B') {
			idx = 1;
			elev_handler.elev[1].getDesButton(sb.btn[destination - 1], source, destination);
		} else if (e_name == 'C') {
			idx = 2;
			elev_handler.elev[2].getDesButton(sb.btn[destination - 1], source, destination);
		} else if (e_name == 'D') {
			idx = 3;
			elev_handler.elev[3].getDesButton(sb.btn[destination - 1], source, destination);
		} else
			idx = 4;
	}
}