package learn_pinch_2d;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;

final class IndexFinger {	
	final DoubleProperty firstJointAdductionAngle;
	final DoubleProperty firstJointBendAngle;
	final DoubleProperty secondJointBendAngle;
	final DoubleProperty lastJointBendAngle;
	
	final ObjectProperty<Point2D> firstJointPos;
	final ObjectProperty<Point2D> secondJointPos;
	final ObjectProperty<Point2D> lastJointPos;
	final ObjectProperty<Point2D> tipPos;

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