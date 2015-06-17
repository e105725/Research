package sample.q_learning_3d;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point3D;

public final class ActionList {
	private static final int MAX_DISTANCE = 3;
	private final List<Point3D> list;
	
	public ActionList() {
		this.list = new ArrayList<>();
		for (int x = 0; x < MAX_DISTANCE; x++) {
			for (int y = 0; y < MAX_DISTANCE; y++) {
				for (int z = 0; z < MAX_DISTANCE; z++) {
					this.list.add(new Point3D(x, y, z));
				}
			}
		}
	}
	
	public final int size() {
		return this.list.size();
	}
	
	public final Point3D getAction(int index) {
		return this.list.get(index);
	}
}