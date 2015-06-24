package learn_pinch_3d;

final class Action {
	final double indexJoint1;
	final double indexJoint2;
	final double indexJoint3;
	final double thumbJoint1;
	final double thumbJoint2;

	Action(double indexJoint1, double indexJoint2, double indexJoint3, double thumbJoint1, double thumbJoint2) {
		this.indexJoint1 = indexJoint1;
		this.indexJoint2 = indexJoint2;
		this.indexJoint3 = indexJoint3;
		this.thumbJoint1 = thumbJoint1;
		this.thumbJoint2 = thumbJoint2;
	}
	
	@Override
	public String toString() {
		String text = "[" + indexJoint1 + "," + indexJoint2 + "," + indexJoint3 + "," + thumbJoint1 + "," + thumbJoint2 + "]";
		return text;
	}
}