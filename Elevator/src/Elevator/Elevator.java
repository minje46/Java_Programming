package Elevator;

import java.awt.Image;

//11월 28일 23시 00분 수정 - setInitialFloor method 추가
import javax.swing.*;

public class Elevator {

	boolean updown, move;
	char serial_num;
	int floor, initialFloor;
	int low, high;
	int x, y;
	int door = 1;
	boolean isMoving = false;

	Sally[] psn = new Sally[30];
	int num_person = 0;

	boolean using = false;
	boolean exactFloor = true;

	JButton[] btn = new JButton[10];
	int[] btnNum = new int[10];
	int[] btnDes = new int[10];
	static int[] count = new int[11];
	static int First;
	static int Second;
	int start = 0;

	Queue<Integer> des = new Queue<Integer>();
	ImageIcon ii1 = new ImageIcon("friends\\브라운1.png");
	Image ori1 = ii1.getImage();
	Image cha1 = ori1.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	ImageIcon closed_EA = new ImageIcon(cha1);
	ImageIcon ii2 = new ImageIcon("friends\\브라운2.png");
	Image ori2 = ii2.getImage();
	Image cha2 = ori2.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	ImageIcon open1_EA = new ImageIcon(cha2);
	ImageIcon ii3 = new ImageIcon("friends\\브라운3.png");
	Image ori3 = ii3.getImage();
	Image cha3 = ori3.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	ImageIcon open2_EA = new ImageIcon(cha3);
	ImageIcon ii4 = new ImageIcon("friends\\샐리1.png");
	Image ori4 = ii4.getImage();
	Image cha4 = ori4.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	ImageIcon closed_EB = new ImageIcon(cha4);
	ImageIcon ii5 = new ImageIcon("friends\\샐리2.png");
	Image ori5 = ii5.getImage();
	Image cha5 = ori5.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	ImageIcon open1_EB = new ImageIcon(cha4);
	ImageIcon ii6 = new ImageIcon("friends\\샐리3.png");
	Image ori6 = ii6.getImage();
	Image cha6 = ori6.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	ImageIcon open2_EB = new ImageIcon(cha6);
	ImageIcon ii7 = new ImageIcon("friends\\코니1.png");
	Image ori7 = ii7.getImage();
	Image cha7 = ori7.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	ImageIcon closed_EC = new ImageIcon(cha7);
	ImageIcon ii8 = new ImageIcon("friends\\코니2.png");
	Image ori8 = ii8.getImage();
	Image cha8 = ori8.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	ImageIcon open1_EC = new ImageIcon(cha8);
	ImageIcon ii9 = new ImageIcon("friends\\코니3.png");
	Image ori9 = ii9.getImage();
	Image cha9 = ori9.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	ImageIcon open2_EC = new ImageIcon(cha9);
	ImageIcon ii10 = new ImageIcon("friends\\개굴1.png");
	Image ori10 = ii10.getImage();
	Image cha10 = ori10.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	ImageIcon closed_ED = new ImageIcon(cha10);
	ImageIcon ii11 = new ImageIcon("friends\\개굴2.png");
	Image ori11 = ii11.getImage();
	Image cha11 = ori11.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	ImageIcon open1_ED = new ImageIcon(cha11);
	ImageIcon ii12 = new ImageIcon("friends\\개굴3.png");
	Image ori12 = ii12.getImage();
	Image cha12 = ori12.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	ImageIcon open2_ED = new ImageIcon(cha12);

	public Elevator(int low, int high, char sn) {
		this.low = low;
		this.high = high;
		serial_num = sn;
		if (serial_num == 'A') {
			floor = 1;
			initialFloor = 1;
			x = 80;
			y = 540;
		} else if (serial_num == 'B') {
			floor = 5;
			initialFloor = 5;
			x = 335;
			y = 300;
		} else if (serial_num == 'C') {
			floor = 8;
			initialFloor = 8;
			x = 210;
			y = 120;
		} else {
			floor = 3;
			initialFloor = 3;
			x = 210;
			y = 420;
		}
	}

	public Elevator() {
	}

	public static void Initialize() {
		for (int i = 1; i <= 10; i++)
			count[i] = 0;
	}

	public static void Max() {
		int max = 0, idx = 0;
		for (int i = 1; i <= 10; i++) {
			if (max < count[i]) {
				max = count[i];
				idx = i;
			}
		}
		First = idx;
		count[idx] = 0;
		max = 0;
		for (int i = 1; i <= 10; i++) {
			if (max < count[i]) {
				max = count[i];
				idx = i;
			}
		}
		Second = idx;
		ElevatorThread.F = First;
		ElevatorThread.S = Second;
		Initialize();
	}

	public void setInitialFloor(int initialFloor) {
		this.initialFloor = initialFloor;
	}

	public void getDesButton(JButton btn, int btnNum, int btnDes) {
		for (int i = 0; i < 10; i++) {
			if (this.btn[i] == null) {
				this.btn[i] = btn;
				this.btnNum[i] = btnNum;
				this.btnDes[i] = btnDes;
				count[btnDes] = count[btnDes] + 1;
				ElevatorThread.k = 1;
				for (int k = 1; k <= 10; k++)
					System.out.println(k + "'s K : " + count[k]);
				break;
			}
		}
	}

	public void nextdes(int curF, int desF) {
		if (des.rearValue() != null) {
			inFunction(curF, desF);
		} else {
			if (curF < desF) {
				updown = true;
				move = true;
			} else {
				updown = false;
				move = false;
			}

			des.enqueue(curF);
			des.enqueue(desF);
		}
	}

	public void inFunction(int curF, int desF) {
		int size = des.getSize();
		int i = 1, j;
		int S = 1;

		if (updown == true) {
			boolean check = false;

			if (curF != des.confirm()) {
				if ((floor < curF && curF < des.confirm()) || (floor > curF && curF > des.confirm()))
					S = 0;
				if ((curF < des.confirm() && curF < floor) || (curF > des.confirm() && curF > floor))
					S = 0;

				for (i = S; i < size; i++) {
					if (600 - (curF * 60) > y && move == true)
						break;

					if (curF < des.getValue(i)) {
						des.insertNormal(i, curF);
						check = true;
						i++;
						break;
					} else if (curF == des.getValue(i)) {

						i++;
						check = true;
						break;
					} else
						continue;
				}
				if (check == false) {
					boolean repeated = false;
					for (int k = 1; k < size; k++) {
						if (curF == des.getValue(k)) {
							i = k;
							repeated = true;
							break;
						}
					}
					if (repeated == false) // 중복되지 않는다면 insert
					{
						des.enqueue(curF);
						i = des.getSize() - 1;
					}
				}
			}
			if (curF > desF) {
				if (des.rearValue() != desF)
					des.enqueue(desF);
				return;
			}
			check = false;
			size = des.getSize();
			for (j = i; j < size; j++) {
				if (desF < des.getValue(j)) {
					des.insertNormal(j, desF);
					check = true;
					j++;
					break;
				} else if (desF == des.getValue(j)) {
					j++;
					check = true;
					break;
				} else
					continue;
			}
			if (check == false)
				des.enqueue(desF);
		} else {
			boolean check = false;
			if (curF != des.confirm()) {
				if ((floor < curF && curF < des.confirm()) || (floor > curF && curF > des.confirm()))
					S = 0;
				if ((curF < des.confirm() && curF < floor) || (curF > des.confirm() && curF > floor))
					S = 0;

				for (i = S; i < size; i++) {
					if (600 - (curF * 60) < y && move == false)
						break;
					if (curF > des.getValue(i)) {
						des.insertNormal(i, curF);
						check = true;
						i++;
						break;
					} else if (curF == des.getValue(i)) {
						i++;
						check = true;
						break;
					} else
						continue;
				}
				if (check == false) {
					boolean repeated = false;
					for (int k = 1; k < size; k++) {
						if (curF == des.getValue(k)) {
							i = k;
							repeated = true;
							break;
						}
					}
					if (repeated == false) {
						des.enqueue(curF);
						i = des.getSize() - 1;
					}
				}
			}
			if (curF < desF) {
				if (des.rearValue() != desF)
					des.enqueue(desF);
				return;
			}
			check = false;
			size = des.getSize();
			for (j = i; j < size; j++) {
				if (desF > des.getValue(j)) {
					des.insertNormal(j, desF);
					check = true;
					j++;
					break;
				} else if (desF == des.getValue(j)) {
					j++;
					check = true;
					break;
				} else
					continue;
			}
			if (check == false)
				des.enqueue(desF);
		}
	}

	public boolean pickupComplete() {
		int count = 0;
		int p_num = 0;
		for (int i = 0; i < 30; i++) {
			if (psn[i] != null) {
				if (psn[i].willRide == true && psn[i].floor == floor && isMoving == false) {
					p_num++;
					if (x <= psn[i].x && psn[i].x <= x + 60)
						count++;
				}
			}
		}
		if (count == p_num)
			return true;
		else
			return false;
	}

	public boolean havePerson() {
		int count = 0;
		for (int i = 0; i < 30; i++) {
			if (psn[i] != null) {
				if (psn[i].x <= x + 60)
					count++;
			}
		}
		if (count != 0)
			return true;
		else
			return false;
	}

	public boolean getoutComplete() {
		int count = 0;
		for (int i = 0; i < 30; i++) {
			if (psn[i] != null && psn[i].willRide == true) {
				if (psn[i].des == floor && psn[i].floor == floor) {
					if (psn[i].x <= x + 60)
						count++;
					else {
						psn[i].willRide = false;

						num_person--;
					}
				}
			}
		}
		if (count == 0)
			return true;
		else
			return false;
	}

	public void doorOpened() {
		for (int i = 0; i < 30; i++) {
			if (psn[i] != null) {
				if (psn[i].des == floor && psn[i].floor == floor) {
					psn[i].isOpen = true;
				}
			}
		}
	}

	public void pickupSally(Sally newPerson) {
		if (num_person < 10) {
			for (int i = 0; i < 30; i++) {
				if (psn[i] == null) {
					psn[i] = newPerson;
					break;
				}
			}
			num_person++;
		} else
			System.out.println("Elevator" + this.serial_num + " is Full !");
	}

	public ImageIcon img_A() {
		if (door == 1)
			return closed_EA;
		else if (door == 2)
			return open1_EA;
		else
			return open2_EA;
	}

	public ImageIcon img_B() {
		if (door == 1)
			return closed_EB;
		else if (door == 2)
			return open1_EB;
		else
			return open2_EB;
	}

	public ImageIcon img_C() {
		if (door == 1)
			return closed_EC;
		else if (door == 2)
			return open1_EC;
		else
			return open2_EC;
	}

	public ImageIcon img_D() {
		if (door == 1)
			return closed_ED;
		else if (door == 2)
			return open1_ED;
		else
			return open2_ED;
	}

	public void GoDown(Integer destination) {
		isMoving = true;
		move = false;
		if (des.getSize() >= 2 && des.getValue(0) < des.getValue(1))
			updown = true;
		else
			updown = false;
		if (y < 600 - (60 * destination)) {
			y++;
			if (y % 60 == 0) {
				floor = 10 - (y / 60);
				exactFloor = true;
			} else
				exactFloor = false;
		}
	}

	public void GoUp(Integer destination) {
		isMoving = true;
		move = true;
		if (des.getSize() >= 2 && des.getValue(0) < des.getValue(1))
			updown = true;
		else
			updown = false;
		if (y > 600 - (60 * destination)) {
			y--;
			if (y % 60 == 0) {
				floor = 10 - (y / 60);
				exactFloor = true;
			} else
				exactFloor = false;
		}
	}

	public boolean isReach(int destination) {
		if (y == 600 - (60 * destination))
			return true;
		else
			return false;
	}

	public void riding() {
		for (int i = 0; i < 30; i++) {
			if (psn[i] != null) {
				if (psn[i].floor == floor && isMoving == false)
					psn[i].rideElev = true;
			}
		}
	}
}