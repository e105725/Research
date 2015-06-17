package sample.q_learning_3d;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;

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

	public final void move(Point3D action) {
		double xPos = this.positionX.get() + action.getX();
		this.positionX.set(xPos);
		
		double yPos = this.positionY.get() + action.getY();
		this.positionY.set(yPos);
		
		double zPos = this.positionZ.get() + action.getZ();
		this.positionZ.set(zPos);
	}

	public final void setPosition(double x, double y, double z) {
		this.setPositionX(x);
		this.setPositionY(y);
		this.setPositionZ(z);
	}
}