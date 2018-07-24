package Elevator;

import java.util.Scanner;

public class ElevatorHandler {
	ElevatorThread eThread;
	Elevator[] elev = new Elevator[4];
	Building cPanel;

	boolean threadOperate = false;

	public ElevatorHandler(Building cp) {
		cPanel = cp;
		eThread = new ElevatorThread(cPanel);
		elev[0] = cPanel.Brown;
		elev[1] = cPanel.Sallly;
		elev[2] = cPanel.Frog;
		elev[3] = cPanel.Cony;
	}

	public char moving(int source, int destination, FloorButton sb) {
		Scanner keyboard = new Scanner(System.in);

		if (threadOperate == false) {
			eThread.start();
			threadOperate = true;
		}
		Elevator to_get;
		sb.startLabel.setText(source + " ");
		sb.desLabel.setText(" " + destination);

		if ((source <= 5 && destination >= 6) || (source >= 6 && destination <= 5)) {
			if (elev[0].num_person < 10 && elev[1].num_person < 10) {
				double wtA = waitingTime(source, destination, elev[0]);
				double wtB = waitingTime(source, destination, elev[1]);

				if (wtA <= wtB)
					to_get = elev[0];
				else
					to_get = elev[1];
			} else {
				if (elev[0].num_person < 10) {
					double wtA = waitingTime(source, destination, elev[0]);
					to_get = elev[0];
				} else if (elev[1].num_person < 10) {
					double wtB = waitingTime(source, destination, elev[1]);
					to_get = elev[1];
				} else
					return 'F';
			}
		} else if (source <= 5 && destination <= 5) {
			double[] wt = new double[] { 9999, 9999, 9999, 9999 };
			if (elev[3].num_person < 10 || elev[0].num_person < 10 || elev[1].num_person < 10) {
				for (int k = 0; k < 4; k++) {
					if (k != 2 && elev[k].num_person < 10) {
						wt[k] = waitingTime(source, destination, elev[k]);
					}
				}
				int minIdx = 0;
				for (int k = 1; k < 4; k++) {
					if (k != 2 && wt[k] <= wt[minIdx])
						minIdx = k;
				}
				to_get = elev[minIdx];
			} else {
				return 'F';
			}
		} else {
			double[] wt = new double[] { 9999, 9999, 9999 };
			if (elev[2].num_person < 10 || elev[0].num_person < 10 || elev[1].num_person < 10) {
				for (int k = 0; k < 3; k++) {
					if (elev[k].num_person < 10) {
						wt[k] = waitingTime(source, destination, elev[k]);
					}
				}
				int minIdx = 0;
				for (int k = 1; k < 3; k++) {
					if (wt[k] <= wt[minIdx])
						minIdx = k;
				}
				to_get = elev[minIdx];
			} else {
				return 'F';
			}
		}

		cPanel.getSally(source, destination, to_get);
		to_get.using = true;
		to_get.nextdes(source, destination);

		return to_get.serial_num;
	}

	public double waitingTime(int source, int destination, Elevator elev) {
		Queue<Integer> floorQ = new Queue<Integer>();
		if (elev.des.confirm() != null)
			getFloorQueue(source, destination, elev, floorQ);
		if (floorQ.qIsEmpty() == true) {
			int floor_num = 0;
			floor_num = Math.abs(source - elev.floor) + Math.abs(destination - source);
			return floor_num * 1.2 + 2.5;
		} else {
			int size = floorQ.getSize();
			int floor_num = 0;
			int stopfloor = 0;
			floor_num += Math.abs(floorQ.getValue(0) - elev.floor);
			for (int k = 0; k < floorQ.Last(destination); k++) {
				floor_num += Math.abs(floorQ.getValue(k + 1) - floorQ.getValue(k));
				stopfloor++;
			}

			return floor_num * 1.2 + stopfloor * 2.5;
		}
	}

	public void getFloorQueue(int source, int destination, Elevator elev, Queue<Integer> floorQ) {
		for (int k = 0; k < elev.des.getSize(); k++)
			floorQ.enqueue(elev.des.getValue(k));

		int size = floorQ.getSize();
		int i = 1, j;
		int istart = 1;
		if (elev.updown == true) {
			boolean inserted = false;
			if (source != floorQ.confirm()) {
				if ((elev.floor < source && source < elev.des.confirm())
						|| (elev.floor > source && source > elev.des.confirm()))
					istart = 0;
				if ((source < elev.des.confirm() && source < elev.floor)
						|| (source > elev.des.confirm() && source > elev.floor))
					istart = 0;

				for (i = istart; i < size; i++) {
					if (600 - (source * 60) > elev.y && elev.isMoving == true && elev.move == true) {
						continue;
					}
					if (source < floorQ.getValue(i)) {
						floorQ.insertNormal(i, source);
						inserted = true;
						i++;
						break;
					} else if (source == floorQ.getValue(i)) {
						i++;
						inserted = true;
						break;
					} else
						continue;
				}
				if (inserted == false) {
					boolean repeated = false;
					for (int k = 1; k < size; k++) {
						if (source == floorQ.getValue(k)) {
							i = k;
							repeated = true;
							break;
						}
					}
					if (repeated == false) {
						floorQ.enqueue(source);
						i = floorQ.getSize() - 1;
					}
				}
			}
			if (source > destination) {
				if (floorQ.rearValue() != destination)
					floorQ.enqueue(destination);
				return;
			}
			inserted = false;
			size = floorQ.getSize();
			for (j = i; j < size; j++) {
				if (destination < floorQ.getValue(j)) {
					floorQ.insertNormal(j, destination);
					inserted = true;
					j++;
					break;
				} else if (destination == floorQ.getValue(j)) {
					j++;
					inserted = true;
					break;
				} else
					continue;
			}
			if (inserted == false) {
				floorQ.enqueue(destination);
			}
		} else {
			boolean inserted = false;
			if (source != floorQ.confirm()) {
				if ((elev.floor < source && source < elev.des.confirm())
						|| (elev.floor > source && source > elev.des.confirm()))
					istart = 0;
				if ((source < elev.des.confirm() && source < elev.floor)
						|| (source > elev.des.confirm() && source > elev.floor))
					istart = 0;

				for (i = istart; i < size; i++) {
					if (600 - (source * 60) < elev.y && elev.isMoving == true && elev.move == false)
						continue;
					if (source > floorQ.getValue(i)) {
						floorQ.insertNormal(i, source);
						inserted = true;
						i++;
						break;
					} else if (source == floorQ.getValue(i)) {
						i++;
						inserted = true;
						break;
					} else
						continue;
				}
				if (inserted == false) {
					boolean repeated = false;
					for (int k = 1; k < size; k++) {
						if (source == floorQ.getValue(k)) {
							i = k;
							repeated = true;
							break;
						}
					}
					if (repeated == false) {
						floorQ.enqueue(source);
						i = floorQ.getSize() - 1;
					}
				}
			}
			if (source < destination) {
				if (floorQ.rearValue() != destination)
					floorQ.enqueue(destination);
				return;
			}
			inserted = false;
			size = floorQ.getSize();
			for (j = i; j < size; j++) {
				if (destination > floorQ.getValue(j)) {
					floorQ.insertNormal(j, destination);
					inserted = true;
					j++;
					break;
				} else if (destination == floorQ.getValue(j)) {
					j++;
					inserted = true;
					break;
				} else
					continue;
			}
			if (inserted == false)
				floorQ.enqueue(destination);
		}
	}

}