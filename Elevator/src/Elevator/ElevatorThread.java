package Elevator;

import javax.swing.ImageIcon;

public class ElevatorThread extends Thread {
	Building cPanel;
	Elevator[] elev = new Elevator[4];
	Sally[] psn = new Sally[100];
	static int k = 1;

	static int F = 1, S = 1;

	public ElevatorThread(Building cPanel) {
		this.cPanel = cPanel;
		elev[0] = cPanel.Brown;
		elev[1] = cPanel.Sallly;
		elev[2] = cPanel.Frog;
		elev[3] = cPanel.Cony;
		psn = cPanel.psn;
	}

	public void run() {
		int[] time = new int[4];
		int[] freeTime = new int[4];

		while (true) {
			cPanel.repaint();
			for (int i = 0; i < 100; i++) {
				if (psn[i] != null) {
					if (psn[i].rideElev == false)
						psn[i].GoRight(psn[i].destination);
					else {
						if (psn[i].floor == psn[i].des && psn[i].isOpen == true) {
							psn[i].GoRight(psn[i].Gone);
						} else {
							if (psn[i].elev.using == true) {
								if (psn[i].elev.floor < psn[i].elev.des.confirm()) {
									psn[i].GOUP(psn[i].elev.des.confirm());
								} else if (psn[i].elev.floor > psn[i].elev.des.confirm()) {
									psn[i].GODOWN(psn[i].elev.des.confirm());
								}
							}
						}
					}
				}
			}
			for (int i = 0; i < 4; i++) {
				if (elev[i].using == true) {
					freeTime[i] = 0;
					if (elev[i].des.getSize() >= 2) {
						if (elev[i].des.getValue(0) == elev[i].des.getValue(1))
							elev[i].des.deleteQ();
					}
					if (elev[i].y > 600 - (60 * elev[i].des.confirm())) {
						elev[i].GoUp(elev[i].des.confirm());
					} else if (elev[i].y < 600 - (60 * elev[i].des.confirm())) {
						elev[i].GoDown(elev[i].des.confirm());
					}

					boolean doContinue = false;
					char ch;
					for (int k = 0; k < 30; k++) {
						if (elev[i].psn[k] != null) {
							if (elev[i].psn[k].startF == elev[i].floor && elev[i].exactFloor == true) {
								if (elev[i].psn[k].startF != elev[i].des.confirm()) {
									elev[i].des.insertNormal(0, elev[i].psn[k].startF);
								}
								ch = 's';
								deleteSameFloor(elev[i], k, ch);
								int indicator = elev_door(i, k, time, elev);

								if (indicator == 0) {
									doContinue = true;
									break;
								}
								closeDoor(i, k, time, elev, ch);
							} else if (elev[i].psn[k].destinationF == elev[i].floor && elev[i].psn[k].startF == -1
									&& elev[i].exactFloor == true) {
								if (elev[i].psn[k].destinationF != elev[i].des.confirm()) {

									elev[i].des.insertNormal(0, elev[i].psn[k].destinationF);
								}
								ch = 'd';
								deleteSameFloor(elev[i], k, ch);
								int indicator = elev_door(i, k, time, elev);

								if (indicator == 0) {
									doContinue = true;
									break;
								}
								closeDoor(i, k, time, elev, ch);
							}
						}
					}
					if (doContinue)
						continue;

					for (int k = 0; k < 30; k++) {
						if (elev[i].psn[k] != null && elev[i].psn[k].x > 600)
							elev[i].psn[k] = null;
					}
				}

				else {
					if (freeTime[i] >= 500) {
						if (freeTime[i] == 500 && k == 1) {
							Elevator.Max();
							System.out.println(F + "<- 1st");
							System.out.println(S + "<- 2nd");
							elev[0].setInitialFloor(F);
							elev[1].setInitialFloor(S);
							elev[2].setInitialFloor(8);
							elev[3].setInitialFloor(3);
							k = 0;

						}
						if (elev[i].floor < elev[i].initialFloor) {
							elev[i].GoUp(elev[i].initialFloor);
						} else if (elev[i].floor > elev[i].initialFloor) {
							elev[i].GoDown(elev[i].initialFloor);
						}

					}
					freeTime[i]++;
				}
			}
			try {
				sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public int elev_door(int i, int k, int[] time, Elevator[] elev) {
		if (elev[i].havePerson() == true && time[i] == 40) {
			elev[i].door = 2;
			elev[i].isMoving = false;
		}
		if (elev[i].door == 2 && time[i] == 60)

		{
			elev[i].door = 3;
			elev[i].doorOpened();
		}
		if (elev[i].getoutComplete() == false) {
			time[i]++;
			if (time[i] > 60)
				time[i] = 61;
			return 0;
		}
		if (elev[i].pickupComplete() == false) {
			time[i]++;
			if (time[i] > 60)
				time[i] = 61;
			return 0;
		}
		return 1;
	}

	public void closeDoor(int i, int k, int[] time, Elevator[] elev, char ch) {
		if (elev[i].door == 3 && time[i] == 70) {
			elev[i].door = 2;
			for (int d = 0; d < 10; d++) {
				if (elev[i].btn[d] != null && elev[i].btnNum[d] == elev[i].floor) {
					elev[i].btn[d].setIcon(new ImageIcon("button\\f" + elev[i].btnDes[d] + ".png"));
					elev[i].btn[d] = null;
					elev[i].btnNum[d] = 0;
					elev[i].btnDes[d] = 0;
				}
			}
		}

		if (elev[i].door == 2 && time[i] == 100)

		{
			elev[i].door = 1;
			elev[i].riding();
			time[i] = 0;

			deleteSameFloor(elev[i], k, ch);
			if (ch == 's')
				elev[i].psn[k].startF = -1;
			else
				elev[i].psn[k].destinationF = -1;
			elev[i].des.deleteQ();
		}
		if (elev[i].des.confirm() == null) {
			elev[i].using = false;
			elev[i].isMoving = false;
			for (int m = 0; m < 30; m++) {
				if (elev[i].psn[m] != null)
					elev[i].psn[m] = null;
			}
		}
		time[i]++;
	}

	public void deleteSameFloor(Elevator elev, int k, char ch) {
		int count = 0;
		if (ch == 's') {
			for (int i = 0; i < 30; i++) {
				if (elev.psn[i] != null && i != k) {
					if (elev.psn[i].startF == elev.psn[k].startF) {
						elev.psn[i].startF = -1;
					}
					if (elev.psn[i].destinationF == elev.psn[k].startF && elev.psn[i].startF == -1) {
						elev.psn[i].destinationF = -1;
					}
					if (elev.psn[i].destinationF == elev.psn[k].startF && elev.psn[i].startF != -1)
						count++;
				}
			}
			if (count == 0) {
				for (int i = 1; i < elev.des.getSize(); i++) {
					if (elev.des.getValue(i) == elev.psn[k].startF)
						elev.des.deleteByIdx(i);
				}
			}
		} else {
			for (int i = 0; i < 30; i++) {
				if (elev.psn[i] != null && i != k) {
					if (elev.psn[i].destinationF == elev.psn[k].destinationF && elev.psn[i].startF == -1) {
						elev.psn[i].destinationF = -1;
					}
					if ((elev.psn[i].startF == elev.psn[k].destinationF)
							|| (elev.psn[i].destinationF == elev.psn[k].destinationF && elev.psn[i].startF != -1))
						count++;
				}
			}
			if (count == 0) {
				for (int i = 1; i < elev.des.getSize(); i++) {
					if (elev.des.getValue(i) == elev.psn[k].destinationF)
						elev.des.deleteByIdx(i);
				}
			}
		}
	}
}