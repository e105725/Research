package learn_pinch_2d;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;

final class ThumbFinger {
	final DoubleProperty firstJointTwistAngle;
	final DoubleProperty firstJointBendAngle;
	final DoubleProperty secondJointAdductionAngle;
	final DoubleProperty secondJointBendAngle;
	final DoubleProperty lastJointBendAngle;
	
	final ObjectProperty<Point2D> firstJointPos;
	final ObjectProperty<Point2D> lastJointPos;
	final ObjectProperty<Point2D> tipPos;
	
	ThumbFinger() {
		this.firstJointTwistAngle = new SimpleDoubleProperty();
		this.firstJointBendAngle = new SimpleDoubleProperty();
		this.secondJointAdductionAngle = new SimpleDoubleProperty();
		this.secondJointBendAngle = new SimpleDoubleProperty();
		this.lastJointBendAngle = new SimpleDoubleProperty();
	
		this.firstJointPos = new SimpleObjectProperty<>();
		this.lastJointPos = new SimpleObjectProperty<>();
		this.tipPos = new SimpleObjectProperty<>();
		this.reset();
	}

	final void reset() {
		this.firstJointTwistAngle.set(0);
		this.firstJointBendAngle.set(0);
		this.secondJointAdductionAngle.set(0);
		this.secondJointBendAngle.set(0);
		this.lastJointBendAngle.set(0);
	}
}