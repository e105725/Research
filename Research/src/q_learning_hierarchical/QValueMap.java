package q_learning_hierarchical;

import java.awt.Point;


final class QValueMap {
	private static final double DISCOUNT = 0.8;
	private static final double STUDY = 0.5;
	
	private final double[][][] qValues;
	private final ActionList actionList;

	QValueMap(int width, int height, ActionList _actionList) {
		this.actionList = _actionList;
		this.qValues = new double[width][height][this.actionList.size()];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int actionIndex = 0; actionIndex < this.actionList.size(); actionIndex++) {
					this.qValues[x][y][actionIndex] = 0.01;
				}
			}
		}
	}

	final double getQValue(int width, int height, int actionIndex) {
		//if ()
		return this.qValues[width][height][actionIndex];
	}

	final void updateQValue(int positionX, int positionY, int actionIndex) {
		Point action = this.actionList.getAction(actionIndex);
		int nextX = positionX + action.x;
		int nextY = positionY + action.y;
		
		boolean modelXPosIsValid = 0 <= nextX && nextX < Main.AREA_WIDTH;
		boolean modelYPosIsValid = 0 <= nextY && nextY < Main.AREA_HEIGHT;
		if (!modelYPosIsValid || !modelXPosIsValid) {
			this.qValues[positionX][positionY][actionIndex] = -1;
			return;
		}
		
		LowClassRewardFunction reward = new LowClassRewardFunction();
		double oldQValue = this.getQValue(positionX, positionY, actionIndex);
		
		
		double qValue = oldQValue + STUDY * 
				(reward.getReward(nextX, nextY) + DISCOUNT * this.searchMaxQValue(nextX, nextY)- oldQValue);
		
		this.qValues[positionX][positionY][actionIndex] = qValue;
	}
	
	private final double searchMaxQValue(int posX, int posY) {
		double maxQValue = -Double.MAX_VALUE;
		for (int index = 0; index < this.actionList.size(); index++) {
			double qValue = this.getQValue(posX, posY, index);
			maxQValue = Math.max(maxQValue, qValue);
		}
		return maxQValue;
	}
}