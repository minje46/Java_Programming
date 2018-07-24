package Elevator;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

public class Building extends JPanel {
	ImageIcon bg;

	Sally[] psn = new Sally[100];

	Elevator Brown = new Elevator(1, 10, 'A');
	Elevator Sallly = new Elevator(1, 10, 'B');
	Elevator Frog = new Elevator(1, 5, 'C');
	Elevator Cony = new Elevator(6, 10, 'D');

	public Building() {
		ImageIcon bgg = new ImageIcon("friends\\background.png");
		Image ori = bgg.getImage();
		Image cha = ori.getScaledInstance(700, 630, Image.SCALE_SMOOTH);
		bg = new ImageIcon(cha);
		setLayout(null);
	}

	protected void paintComponent(Graphics g) {
		g.drawImage(bg.getImage(), 0, 0, null);
		setOpaque(false);
		super.paintComponent(g);
	}

	public void paint(Graphics g) {
		super.paint(g);

		g.drawImage(this.Brown.img_A().getImage(), this.Brown.x, this.Brown.y, this);
		g.drawImage(this.Sallly.img_B().getImage(), this.Sallly.x, this.Sallly.y, this);
		g.drawImage(this.Frog.img_C().getImage(), this.Frog.x, this.Frog.y, this);
		g.drawImage(this.Cony.img_D().getImage(), this.Cony.x, this.Cony.y, this);
		for (int i = 0; i < 100; i++) {
			if (psn[i] != null) {
				g.drawImage(this.psn[i].img().getImage(), this.psn[i].x, this.psn[i].y, this);
				if (psn[i].x > 630)
					psn[i] = null;
			}

		}
	}

	public void getSally(int source, int destination, Elevator elev) {
		int i;
		for (i = 0; i < 100; i++) {
			if (psn[i] == null) {
				psn[i] = new Sally(source, destination, elev);
				elev.pickupSally(psn[i]);
				break;
			}
		}

	}
}