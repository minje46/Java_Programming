package Elevator;

import java.util.LinkedList;

class Queue<Integer> {
	LinkedList<Integer> set = new LinkedList<Integer>();

	public void enqueue(Integer item) {
		set.add(item);
	}

	public Integer rearValue() {
		if (set.size() == 0)
			return null;
		return set.get(set.size() - 1);
	}

	public int Last(Integer item) {
		return set.lastIndexOf(item);
	}

	public int getSize() {
		return set.size();
	}

	public void insertNormal(int idx, Integer item) {
		set.add(idx, item);
	}

	public Integer getValue(int idx) {
		return set.get(idx);
	}

	public Integer confirm() {
		if (qIsEmpty()) {
			return null;
		} else {
			return set.get(0);
		}
	}

	public void deleteByIdx(int idx) {
		set.remove(idx);
	}

	public void deleteObject(Integer num) {
		set.remove(num);
	}

	public Integer deleteQ() {
		if (qIsEmpty()) {
			return null;
		} else {
			Integer temp = set.get(0);
			set.remove(0);
			return temp;
		}
	}

	public boolean qIsEmpty() {
		if (set.size() == 0)
			return true;
		else
			return false;
	}
}
