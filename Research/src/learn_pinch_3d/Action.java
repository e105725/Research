package learn_pinch_3d;

final class Action {
	final double indexJoint1Adduction;
	final double indexJoint1;
	final double indexJoint2;
	final double indexJoint3;
	
	final double thumbJoint1Adduction;
	final double thumbJoint1;
	final double thumbJoint2;
	

	Action(double index1Adduction, double index1, double index2, double index3, double thumb1Adduction, double thumb1, double thumb2) {
		this.indexJoint1Adduction = index1Adduction;
		this.indexJoint1 = index1;
		this.indexJoint2 = index2;
		this.indexJoint3 = index3;
		
		this.thumbJoint1Adduction = thumb1Adduction;
		this.thumbJoint1 = thumb1;
		this.thumbJoint2 = thumb2;
	}
}