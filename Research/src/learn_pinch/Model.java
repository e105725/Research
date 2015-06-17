package learn_pinch;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

//手の超簡略化、蝶番のイメージ
final class Model {
	static final double INDEX_FINGER_LENGTH = 20;
	static final double THUMB_FINGER_LENGTH = 20;
	
	private static final double INDEX_FINGER_DEFAULT_ANGLE = 90;
	private static final double THUMB_FINGER_DEFAULT_ANGLE = 180;
	
	private final DoubleProperty indexFingerAngle;
	private final DoubleProperty thumbFingerAngle;
	
	Model() {
		this.indexFingerAngle = new SimpleDoubleProperty();
		this.thumbFingerAngle = new SimpleDoubleProperty();
		this.reset();
	}

	final void reset() {
		this.indexFingerAngle.set(INDEX_FINGER_DEFAULT_ANGLE);
		this.thumbFingerAngle.set(THUMB_FINGER_DEFAULT_ANGLE);
	}
	
	final double getIndexFingerAngle() {
		return this.indexFingerAngle.get();
	}
	
	final DoubleProperty indexFingerAngleProperty() {
		return this.indexFingerAngle;
	}
	
	final void setIndexFingerAngle(double angle) {
		this.indexFingerAngle.set(angle);
	}
	
	final double getThumbFingerAngle() {
		return this.thumbFingerAngle.get();
	}
	
	final DoubleProperty thumbFingerAngleProperty() {
		return this.thumbFingerAngle;
	}
	
	final void setThumbFingerAngle(double angle) {
		this.thumbFingerAngle.set(angle);
	}
}