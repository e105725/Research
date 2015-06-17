package q_learning_hierarchical;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

final class ActionList {
	private static final double MAX_DISTANCE = 3;
	private final List<Point> list;
	
	ActionList() {
		this.list = new ArrayList<>();
		
		for (int x = 0; x < MAX_DISTANCE; x++) {
			for (int y = 0; y < MAX_DISTANCE; y++) {
				this.list.add(new Point(x, y));
			}
		}
	}
	
	final int size() {
		return this.list.size();
	}
	
	final Point getAction(int index) {
		return this.list.get(index);
	}
}