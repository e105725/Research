package learn_pinch;

import java.util.Arrays;
import java.util.List;

final class QValueMap {
	private static final double DEFAULT_QVALUE = 0.01;
	
	private final double[][][] qValueMap;
	
	QValueMap(int indexFingerAngleCount, int thumbFingerAngleCount, List<Action> actionList) {
		System.out.println(indexFingerAngleCount);
		System.out.println(thumbFingerAngleCount);
		System.out.println(actionList.size());
		this.qValueMap = new double[indexFingerAngleCount + 1][thumbFingerAngleCount + 1][actionList.size()];
		
		for (int x = 0; x < indexFingerAngleCount; x++) {
			for (int y = 0; y < thumbFingerAngleCount; y++) {
				Arrays.fill(this.qValueMap[x][y], DEFAULT_QVALUE);
			}
		}
	}

	final double getQValue(int x, int y, int actionIndex) {
		return this.qValueMap[x][y][actionIndex];
	}

	//q値を更新する
	final void updateQValue(int x, int y, int actionIndex, double qValue) {
		this.qValueMap[x][y][actionIndex] = qValue;
	}
	
	final double searchMaxQValue(int x, int y) {
		double[] qValues = this.qValueMap[x][y];
		double maxQValue = -Double.MAX_VALUE;
		for (double qValue : qValues) {
			maxQValue = Math.max(maxQValue, qValue);
		}
		return maxQValue;
	}
}