package learn_pinch;

final class ActionList {
	private final Action[] actions;

	ActionList(double maxVariation, double interval) {
		//actionsの初期化
		int angleVariationCount = (int)(maxVariation * 2 / interval) + 1;
		this.actions = new Action[angleVariationCount * angleVariationCount];
		int index = 0;
		for (int x = 0; x < angleVariationCount; x++) {
			for (int y = 0; y < angleVariationCount; y++) {
				double indexFingerAngleVariation = -maxVariation + (x * interval);
				double thumbFingerAngleVariation = -maxVariation + (y * interval);
				this.actions[index] = new Action(indexFingerAngleVariation, thumbFingerAngleVariation);
				index++;
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