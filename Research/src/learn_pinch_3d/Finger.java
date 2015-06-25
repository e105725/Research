package learn_pinch_3d;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point3D;

final class Finger {
	private static final double PHALANX_BONE_LENGTH = 50;
	
	private final Point3D basePosition;
	private final Point3D baseAngle;
	
	private final List<Joint> jointList;
	private final ObjectProperty<Point3D> tipPosition;
	
	Finger(Point3D _basePosition) {
		this.jointList = new ArrayList<>();
		this.tipPosition = new SimpleObjectProperty<>();
	}
}