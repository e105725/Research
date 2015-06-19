package sample.learn_pinch_2line;

import java.util.Arrays;

final class QValueMap {
	private static final double DEFAULT_QVALUE = 0.01;
	
	//2本の指の間の角度差とaction
	private final double[][] qValueMap;
	
	QValueMap(double maxAngle, double minAngle, double interval, ActionList actionList) {
		int stateCount = (int)((maxAngle - minAngle) / interval) + 1;
		this.qValueMap = new double[stateCount][actionList.size()];
		for (int index = 0; index < stateCount; index++) {
			Arrays.fill(this.qValueMap[index], DEFAULT_QVALUE);
		}
	}

	final double getQValue(int stateIndex, int actionIndex) {
		return this.qValueMap[stateIndex][actionIndex];
	}

	//q値を更新する
	final void updateQValue(int stateIndex, int actionIndex, double qValue) {
		this.qValueMap[stateIndex][actionIndex] = qValue;
	}
	
	final double searchMaxQValue(int stateIndex) {
		double[] qValues = this.qValueMap[stateIndex];
		double maxQValue = -Double.MAX_VALUE;
		for (double qValue : qValues) {
			maxQValue = Math.max(maxQValue, qValue);
		}
		return maxQValue;
	}
}