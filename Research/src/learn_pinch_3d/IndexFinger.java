package learn_pinch_3d;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point3D;

final class IndexFinger {	
	final DoubleProperty firstJointAdductionAngle;
	final DoubleProperty firstJointBendAngle;
	final DoubleProperty secondJointBendAngle;
	final DoubleProperty lastJointBendAngle;
	
	final ObjectProperty<Point3D> firstJointPos;
	final ObjectProperty<Point3D> secondJointPos;
	final ObjectProperty<Point3D> lastJointPos;
	final ObjectProperty<Point3D> tipPos;

	IndexFinger() {
		this.firstJointAdductionAngle = new SimpleDoubleProperty();
		this.firstJointBendAngle = new SimpleDoubleProperty();
		this.secondJointBendAngle = new SimpleDoubleProperty();
		this.lastJointBendAngle = new SimpleDoubleProperty();
		this.reset();
		
		this.firstJointPos = new SimpleObjectProperty<>();
		this.secondJointPos = new SimpleObjectProperty<>();
		this.lastJointPos = new SimpleObjectProperty<>();
		this.tipPos = new SimpleObjectProperty<>();
	}

	final void reset() {
		this.firstJointAdductionAngle.set(0);
		this.firstJointBendAngle.set(0);
		this.secondJointBendAngle.set(0);
		this.lastJointBendAngle.set(0);
	}
}