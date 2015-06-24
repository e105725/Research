package learn_pinch_3d;

final class ActionList {
	//private final Action[][][][][][][] actions;
	private final Action[] actions;
	private final int length;
	
//	ActionList(double maxVariation, double interval) {
//		//actionsの初期化
//		int variationCount = (int)((maxVariation / interval) * 2) + 1;
//		this.actions = new Action[variationCount][variationCount][variationCount][variationCount][variationCount][variationCount][variationCount];
//		int index = 0;
//		for (int a = 0; a < variationCount; a++) {
//			for (int b = 0; b < variationCount; b++) {
//				for (int c = 0; c < variationCount; c++) {
//					for (int d = 0; d < variationCount; d++) {
//						for (int e = 0; e < variationCount; e++) {
//							for (int f = 0; f < variationCount; f++) {
//								for (int g = 0; g < variationCount; g++) {
//									double index1Adduction = -maxVariation + (a * interval);
//									double index1 = -maxVariation + (b * interval);
//									double index2 = -maxVariation + (c * interval);
//									double index3 = -maxVariation + (d * interval);
//
//									double thumb1Adduction = -maxVariation + (e * interval);
//									double thumb1 = -maxVariation + (f * interval);
//									double thumb2 = -maxVariation + (g * interval);
//
//									this.actions[a][b][c][d][e][f][g] = new Action(index1Adduction, index1, index2, index3, thumb1Adduction, thumb1, thumb2);
//									index++;
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		this.length = index;
//	}
	
	ActionList(double maxVariation, double interval) {
		//actionsの初期化
		int variationCount = (int)((maxVariation / interval) * 2) + 1;
		this.actions = new Action[(int)Math.pow(variationCount, 7)];
		int index = 0;
		for (int a = 0; a < variationCount; a++) {
			for (int b = 0; b < variationCount; b++) {
				for (int c = 0; c < variationCount; c++) {
					for (int d = 0; d < variationCount; d++) {
						for (int e = 0; e < variationCount; e++) {
							for (int f = 0; f < variationCount; f++) {
								for (int g = 0; g < variationCount; g++) {
									double index1Adduction = -maxVariation + (a * interval);
									double index1 = -maxVariation + (b * interval);
									double index2 = -maxVariation + (c * interval);
									double index3 = -maxVariation + (d * interval);

									double thumb1Adduction = -maxVariation + (e * interval);
									double thumb1 = -maxVariation + (f * interval);
									double thumb2 = -maxVariation + (g * interval);

									this.actions[index] = new Action(index1Adduction, index1, index2, index3, thumb1Adduction, thumb1, thumb2);
									index++;
								}
							}
						}
					}
				}
			}
		}
		this.length = index;
	}


//	final Action get(int a, int b, int c, int d, int e, int f, int g) {
//		return this.actions[a][b][c][d][e][f][g];
//	}
	
	final Action get(int actionIndex) {
		return this.actions[actionIndex];
	}

	final int size() {
		return this.length;
	}
}