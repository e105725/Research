package q_learning;

import javafx.geometry.Point3D;


public final class QValueMap {
	private static final double DISCOUNT = 0.8;
	private static final double STUDY = 0.5;

	private final double[][][][] qValues;
	private final ActionList actionList;

	public QValueMap(int width, int height, int depth, ActionList _actionList) {
		this.actionList = _actionList;
		this.qValues = new double[width][height][depth][this.actionList.size()];
		
		//初期値設定
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < height; z++) {
					for (int actionIndex = 0; actionIndex < this.actionList.size(); actionIndex++) {
						this.qValues[x][z][z][actionIndex] = 0.01;
					}
				}
			}
		}
	}

	public final double getQValue(double x, double y, double z, int actionIndex) {
		return this.qValues[(int)x][(int)y][(int)z][actionIndex];
	}

	public final void updateQValue(double positionX, double positionY, double positionZ, int actionIndex) {
		Point3D action = this.actionList.getAction(actionIndex);
		double nextX = positionX + action.getX();
		double nextY = positionY + action.getY();
		double nextZ = positionZ + action.getZ();

		boolean modelXPosIsValid = 0 <= nextX && nextX < QLeaning.AREA_WIDTH;
		boolean modelYPosIsValid = 0 <= nextY && nextY < QLeaning.AREA_HEIGHT;
		boolean modelZPosIsValid = 0 <= nextZ && nextZ < QLeaning.AREA_DEPTH;
		
		if (!modelYPosIsValid || !modelXPosIsValid || !modelZPosIsValid) {
			this.qValues[(int)positionX][(int)positionY][(int)positionZ][actionIndex] = -1;
			return;
		}

		RewardFunction reward = new RewardFunction();
		double oldQValue = this.getQValue(positionX, positionY, positionZ, actionIndex);

		double qValue = oldQValue + STUDY * 
				(reward.getReward(nextX, nextY, nextZ) + DISCOUNT * this.searchMaxQValue(nextX, nextY, nextZ)- oldQValue);

		this.qValues[(int)positionX][(int)positionY][(int)positionZ][actionIndex] = qValue;
	}

	private final double searchMaxQValue(double nextX, double nextY, double nextZ) {
		double maxQValue = -Double.MAX_VALUE;
		for (int index = 0; index < this.actionList.size(); index++) {
			double qValue = this.getQValue(nextX, nextY, nextZ, index);
			maxQValue = Math.max(maxQValue, qValue);
		}
		return maxQValue;
	}
}