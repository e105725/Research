package q_learning;

import java.awt.Point;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public final class Model {
	private final DoubleProperty positionX;
	private final DoubleProperty positionY;
	private final DoubleProperty positionZ;

	public Model() {
		this(0, 0, 0);
	}
	
	public Model(double x, double y, double z) {
		this.positionX = new SimpleDoubleProperty(x);
		this.positionY = new SimpleDoubleProperty(y);
		this.positionZ = new SimpleDoubleProperty(z);
	}

	public final double getPositionX() {
		return this.positionX.get();
	}

	public final double getPositionY() {
		return this.positionY.get();
	}

	public final double getPositionZ() {
		return this.positionZ.get();
	}

	public final void setPositionX(double x) {
		this.positionX.set(x);
	}
	
	public final void setPositionY(double y) {
		this.positionY.set(y);
	}
	
	public final void setPositionZ(double z) {
		this.positionZ.set(z);
	}

	public final void move(Point selectedAction) {
		double xPos = this.positionX.get() + selectedAction.x;
		double yPos = this.positionY.get() + selectedAction.y;
		
		this.positionX.set(xPos);
		this.positionY.set(yPos);
	}
}