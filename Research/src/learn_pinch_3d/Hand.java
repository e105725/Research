package learn_pinch_3d;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;

//手のモデル
//ただ持ってるのは人差し指と親指のjointだけ
final class Hand {
	
	final ObjectProperty<Point2D> indexTickPos;
	private final List<Joint> indexFingerJointList;
	final ObjectProperty<Point2D> thumbTickPos;
	private final List<Joint> thumbFingerJointList;
		
	Hand() {
		this.indexTickPos = new SimpleObjectProperty<Point2D>(new Point2D(0,0));
		this.thumbTickPos = new SimpleObjectProperty<Point2D>(new Point2D(0,0));
		
		this.indexFingerJointList = new ArrayList<>();
		//人差し指のjointは3つ
		for (int index = 0; index < 3; index++) {
			this.indexFingerJointList.add(new Joint());
		}
		
		this.thumbFingerJointList = new ArrayList<>();
		//親指のjointは2つ
		for (int index = 0; index < 2; index++) {
			this.thumbFingerJointList.add(new Joint());
		}
		this.reset();
	}

	final void reset() {
		this.indexFingerJointList.forEach(joint -> joint.reset());
		this.thumbFingerJointList.forEach(joint -> joint.reset());
	}
	
	final List<Joint> getIndexFingerJointList() {
		return this.indexFingerJointList;
	}
	
	final List<Joint> getThumbFingerJointList() {
		return this.thumbFingerJointList;
	}
}