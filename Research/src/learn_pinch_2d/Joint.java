package learn_pinch_2d;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;

//関節。角度を持つだけ
final class Joint {
	private static final double DEFAULT_JOINT = 0;
	private final DoubleProperty angle;
	final ObjectProperty<Point2D> position;
	
	Joint() {
		this.angle = new SimpleDoubleProperty();
		this.position = new SimpleObjectProperty<>(new Point2D(0, 0));
		this.reset();
	}
	
	final void reset() {
		this.angle.set(DEFAULT_JOINT);
	}
	
	final double getAngle() {
		return this.angle.get();
	}
	
	final DoubleProperty angleProperty() {
		return this.angle;
	}
	
	final void setAngle(double angle) {
		this.angle.set(angle);
	}
}