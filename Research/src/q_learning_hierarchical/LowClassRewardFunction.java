package q_learning_hierarchical;

final class LowClassRewardFunction {
	
	LowClassRewardFunction() {}
	
	final double getReward(Model model) {
		boolean xPosIsClear = model.getPositionX() == 100;
		boolean yPosIsClear = model.getPositionY() == 100;
		boolean zPosIsClear = model.getPositionZ() == 100;
		
		if (!xPosIsClear || !yPosIsClear || !zPosIsClear) {
			return 0;
		}
		return 1;
	}
	
	final double getReward(double x, double y) {
		if (x == Main.goal.x && y == Main.goal.y) {
			return 1;
		}
		return 0;
	}
}