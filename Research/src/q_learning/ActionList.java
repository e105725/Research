package q_learning;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public final class ActionList {
	private final List<Point> list;
	
	public ActionList() {
		this.list = new ArrayList<>();
		this.list.add(new Point(0, 1));
		this.list.add(new Point(0, -1));
		this.list.add(new Point(1, 0));
		this.list.add(new Point(-1, 0));
	}
	
	public final int size() {
		return this.list.size();
	}
	
	public final Point getAction(int index) {
		return this.list.get(index);
	}
}