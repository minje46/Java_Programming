package Elevator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MoveSally extends JFrame {
	Thread display;
	static ImageIcon[][] images = new ImageIcon[3][2];

	JLabel label = new JLabel(images[0][0]);

	public MoveSally() {
		getContentPane().add(label);
		getContentPane().addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				display = new display();
				display.start();
			}

			public void mouseExited(MouseEvent e) {
				display.interrupt();
				while (display.isAlive()) {
				}
				display = null;
				label.setIcon(images[0][0]);
				label.repaint();
			}
		});
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(250, 200);
		setVisible(true);
	}

	public static void main(String[] args) {
		new MoveSally();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				images[i][j] = new ImageIcon("p" + (i + 1) + (j + 1) + ".png");
			}
		}
	}

	class display extends Thread {

		public void run() {
			try {
				while (true) {
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 2; j++) {
							move(i, j, 300);
						}
					}
				}
			} catch (Exception ee) {
				if (ee instanceof InterruptedException) {
				} else {
					System.out.println(ee);
					System.exit(1);
				}
			}
		}

		void move(int width, int height, int time) throws InterruptedException {
			label.setIcon(images[width][height]);
			label.repaint();
			if (Thread.currentThread().interrupted())
				throw (new InterruptedException());
			Thread.currentThread().sleep(time);
		}
	}

}