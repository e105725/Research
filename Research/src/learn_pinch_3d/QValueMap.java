package learn_pinch_3d;


final class QValueMap {
	private static final double DEFAULT_QVALUE = 0.01;

	//2本の指の間の角度差とaction
	//最初はstateあとはaction
	private final double[][]qValueMap;
private final int actionListSize;
	QValueMap(int stateCount, int actionListSize) {
		this.actionListSize = actionListSize;
		this.qValueMap = new double[stateCount][actionListSize];
		for (int stateIndex = 0; stateIndex < stateCount; stateIndex++) {
			for (int actionIndex = 0; actionIndex < actionListSize; actionIndex++) {
				this.qValueMap[stateIndex][actionIndex] = DEFAULT_QVALUE;
			}
		}
	}

	//状態sにおける各行動のQ値の配列を返す
	final double[] getQValues(int stateIndex) {
		return this.qValueMap[stateIndex];
	}

	//状態sにおける行動aのQ値を返す
	final double getQValue(int stateIndex, int actionIndex) {
		return this.qValueMap[stateIndex][actionIndex];
	}

	//q値を更新する
	final void updateQValue(int stateIndex, int actionIndex, double qValue) {
		this.qValueMap[stateIndex][actionIndex] = qValue;
	}

	final double searchMaxQValue(int stateIndex) {
		double maxQValue = -Double.MAX_VALUE;
		for (int a = 0; a < this.actionListSize; a++) {
			double qValue = this.qValueMap[stateIndex][a];
			maxQValue = Math.max(maxQValue, qValue);									
		}
		return maxQValue;
	}
}