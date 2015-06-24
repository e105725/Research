package learn_pinch_3d;

final class ActionList {
	private final Action[] actions;

	ActionList(double maxVariation, double interval) {
		//actionsの初期化
		int angleVariationCount = (int)((maxVariation / interval) * 2) + 1;
		int arrayLength = (int)Math.pow(angleVariationCount, 5);
		this.actions = new Action[arrayLength];
		int index = 0;
		for (int a = 0; a < angleVariationCount; a++) {
			for (int b = 0; b < angleVariationCount; b++) {
				for (int c = 0; c < angleVariationCount; c++) {
					for (int d = 0; d < angleVariationCount; d++) {
						for (int e = 0; e < angleVariationCount; e++) {
							double index1 = -maxVariation + (a * interval);
							double index2 = -maxVariation + (b * interval);
							double index3 = -maxVariation + (c * interval);
							double thumb1 = -maxVariation + (d * interval);
							double thumb2 = -maxVariation + (e * interval);
							this.actions[index] = new Action(index1, index2, index3, thumb1, thumb2);
							index++;
						}
					}
				}
			}
		}
	}

	final Action get(int index) {
		return this.actions[index];
	}

	final int size() {
		return this.actions.length;
	}
}