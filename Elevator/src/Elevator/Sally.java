package Elevator;

import javax.swing.*;

import java.awt.Image;
import java.util.Random;

public class Sally {
	int startF, destinationF;
	int des, destination;
	boolean rideElev = false;
	boolean willRide = true;
	int x, y;
	int Gone, floor;
	boolean isOpen = false;
	Elevator elev;

	ImageIcon psn1_1 = new ImageIcon("friends\\사람1.png");
	Image originImg_1 = psn1_1.getImage();
	Image changedImg_1 = originImg_1.getScaledInstance(60, 75, Image.SCALE_SMOOTH);
	ImageIcon psn1 = new ImageIcon(changedImg_1);

	ImageIcon psn2_2 = new ImageIcon("friends\\사람2.png");
	Image originImg_2 = psn2_2.getImage();
	Image changedImg_2 = originImg_2.getScaledInstance(60, 75, Image.SCALE_SMOOTH);
	ImageIcon psn2 = new ImageIcon(changedImg_2);

	public Sally(int startF, int des, Elevator elev) {
		this.startF = startF;
		destinationF = des;

		floor = startF;
		this.des = des;
		Random random = new Random();
		if (elev.serial_num == 'A') {
			destination = 80 + random.nextInt(25);
		} else if (elev.serial_num == 'B') {
			destination = 335 + random.nextInt(25);
		} else {
			destination = 210 + random.nextInt(25);
		}
		this.elev = elev;
		Gone = 700;
		x = -20;
		y = 600 - (60 * floor);
	}

	public ImageIcon img() {
		if (0 <= (x % 12) && (x % 12) <= 5)
			return psn1;
		else
			return psn2;
	}

	public void GoRight(int destination) {
		if (x < destination)
			x += 1;
	}

	public void GODOWN(Integer destination) {
		if (y < 600 - (60 * destination)) {
			y++;
			if ((y - 20) % 60 == 0)
				floor--;
		}
	}

	public void GOUP(Integer destination) {
		if (y > 600 - (60 * destination)) {
			y--;
			if ((y - 20) % 60 == 0)
				floor++;
		}
	}
}
