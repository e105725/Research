package learn_pinch_3d;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

//手のモデル。ただし　親指と人差指だけで、操作できる関節はとりあえず根本1ヶ所
final class Hand {
	private static final Point3D BASE_POSITION = new Point3D(0, 0, 0);
	private static final Point3D INDEX_FINGER_BASE_POSITION = new Point3D(0, 0, 0);
	private static final Point3D THUMB_FINGER_BASE_POSITION = new Point3D(0, 0, 0);
	
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
	}
}