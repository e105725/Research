package learn_pinch;

import java.util.ArrayList;
import java.util.List;

import sample.boltzmann_selection.BoltzMannSelection;

public final class QLearning {
	//	public static void main(String[] args) {
	//		QLearning ql =new QLearning();
	//		ql.start();
	//	}

	//試行回数
	private static final int TRY_MAX = 10000;
	//1試行あたりの最大行動回数
	private static final int STEP_MAX = 1000;
	//割引率
	private static final double DISCOUNT = 0.8;
	//学習率
	private static final double STUDY = 0.5;
	//人差し指の最大関節角
	private static final double INDEX_FINGER_MIN_ANGLE = 90;
	//人差し指の最小関節角
	private static final double INDEX_FINGER_MAX_ANGLE = 180;
	//親指の最大関節角
	private static final double THUMB_FINGER_MIN_ANGLE = 90;
	//親指の最小関節角
	private static final double THUMB_FINGER_MAX_ANGLE = 180;
	//関節が1度にどの程度動かせるか
	private static final double MAX_VARIATION = 3;
	//関節角の刻み幅
	private static final double INTERVAL = 0.1;
	//BoltzMannSelectionで使うtの初期値。試行を繰り返すごとに減少
	private static final double T_DEFAULT = 1;

	private final Model model;

	QLearning() {
		this.model = new Model();
		//		ActionList actionList = new ActionList(MAX_VARIATION, INTERVAL);
		//		QValueMap qValueMap = new QValueMap(INDEX_FINGER_MAX_ANGLE, INDEX_FINGER_MIN_ANGLE, INTERVAL, actionList);
	}

	void start() {
		ActionList actionList = new ActionList(MAX_VARIATION, INTERVAL);

		//全状態の列挙
		QValueMap qValueMap = new QValueMap(INDEX_FINGER_MAX_ANGLE, INDEX_FINGER_MIN_ANGLE, INTERVAL, actionList);

		//モデルの初期化
		//Model model = new Model();

		//温度tの初期化と、減衰する数の準備
		double t = T_DEFAULT;
		double decrementValue = T_DEFAULT / (double)TRY_MAX;

		//ループ開始
		for (int tryCount = 0; tryCount < TRY_MAX; tryCount++) {
			//モデルの初期化
			model.reset();


			if (tryCount % (TRY_MAX / 100) == 0) {
				System.out.println("進捗率 = " + tryCount / (double)TRY_MAX);
			}
			for (int stepCount = 0; stepCount < STEP_MAX; stepCount++) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//行動決定
				List<Double> qValueList = new ArrayList<>();
				double nowIndexFingerAngle = model.getIndexFingerAngle();
				double nowThumbFingerAngle = model.getThumbFingerAngle();
				double nowDistance = Math.abs(nowIndexFingerAngle - nowThumbFingerAngle);
				int nowStateIndex = (int)(nowDistance / INTERVAL);
				for (int actionIndex = 0; actionIndex < actionList.size(); actionIndex++) {
					qValueList.add(qValueMap.getQValue(nowStateIndex, actionIndex));
				}
				int actionIndex = BoltzMannSelection.select(qValueList, t);
				Action action = actionList.get(actionIndex);

				//行動実行
				double nextIndexFingerAngle = model.getIndexFingerAngle() + action.getIndexFingerAngleVariation();
				double nextThumbFingerAngle = model.getThumbFingerAngle() + action.getThumbFingerAngleVariation();

				model.setIndexFingerAngle(nextIndexFingerAngle);
				model.setThumbFingerAngle(nextThumbFingerAngle);

				double nextDistance = Math.abs(nextIndexFingerAngle - nextThumbFingerAngle);

				int nextStateIndex = (int)(nextDistance / INTERVAL);
				if (!this.isValidFingerAngle(model)) {
					qValueMap.updateQValue(nowStateIndex, actionIndex, -1);
					break;
				}

				if (this.isGoal(model)) {
					System.out.println("goal");
					//System.out.println(nextDistance);
					qValueMap.updateQValue(nowStateIndex, actionIndex, 1);
					break;
				}

				//Q値の更新
				//まずは報酬の計算。
				double reward = this.computeReward(model);
				//次に新しいq値の計算
				double oldQValue = qValueMap.getQValue(nowStateIndex, actionIndex);
				double nextMaxQValue = oldQValue + STUDY * 
						(reward + DISCOUNT * qValueMap.searchMaxQValue(nextStateIndex) - oldQValue);
				//最後にq値のマップを更新
				qValueMap.updateQValue(nowStateIndex, actionIndex, nextMaxQValue);
			}
			//tを減衰
			t -= decrementValue;
		}
		System.out.println("Fin");
		for (int index = 0; index < 900; index++) {
			//for (int actionIndex = 0; actionIndex < actionList.size(); actionIndex++) {
			//double qValue = qValueMap.getQValue(index, actionIndex);
			double qValue = qValueMap.searchMaxQValue(index);
			//if (qValue != 0.01) {
			System.out.println("Distance = " + index * INTERVAL);
			System.out.println("q = " + qValue);
			//}
			//}
		}
	}

	//報酬の計算
	private final double computeReward(Model model) {
		if (!this.isValidFingerAngle(model)) {
			return -1;
		}
		if (this.isGoal(model)) {
			return 1;
		}
		return 1 / (double)(Math.abs((model.getIndexFingerAngle() - model.getThumbFingerAngle())) / INTERVAL);
	}

	//関節可動範囲かどうか
	private final boolean isValidFingerAngle(Model model) {
		double indexFingerAngle = model.getIndexFingerAngle();
		double thumbFingerAngle = model.getThumbFingerAngle();
		boolean isIndexFingerAngleValie = INDEX_FINGER_MIN_ANGLE < indexFingerAngle && indexFingerAngle < INDEX_FINGER_MAX_ANGLE;
		boolean isThumbFingerAngleValie = THUMB_FINGER_MIN_ANGLE < thumbFingerAngle && thumbFingerAngle < THUMB_FINGER_MAX_ANGLE;
		return isThumbFingerAngleValie && isIndexFingerAngleValie;
	}

	//終了条件を満たしているかどうか
	private final boolean isGoal(Model model) {
		double distance = Math.abs(model.getIndexFingerAngle() - model.getThumbFingerAngle());
		if (distance < 0.1) {
			return true;
		}
		return false;
	}

	final Model getModel() {
		return this.model;
	}
}