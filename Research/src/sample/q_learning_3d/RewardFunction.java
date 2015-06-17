package sample.q_learning_3d;

import javafx.geometry.Point3D;

 final class RewardFunction {
	 RewardFunction() {}
	
	 final double getReward(Model model) {
		boolean xPosIsClear = model.getPositionX() == 100;
		boolean yPosIsClear = model.getPositionY() == 100;
		boolean zPosIsClear = model.getPositionZ() == 100;
		
		if (!xPosIsClear || !yPosIsClear || !zPosIsClear) {
			return 0;
		}
		return 1;
	}
	
	 final double getReward(double x, double y, double z) {
		Point3D goal = QLeaning.goal;
		if (x == goal.getX() && y == goal.getY() && z == goal.getZ()) {
			return 1;
		}
		return 0;
	}
}