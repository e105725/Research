package learn_pinch_3d;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import sample.boltzmann_selection.BoltzMannSelection;

public final class QLearning {
	//試行回数
	private static final int TRY_MAX = 1000000;
	//1試行あたりの最大行動回数
	private static final int STEP_MAX = 100;
	//割引率
	private static final double DISCOUNT = 0.8;
	//学習率
	private static final double STUDY = 0.5;

	//関節が一回でどの程度動かせるか
	private static final double MAX_VARIATION = 15;
	//関節角の刻み幅
	private static final double ANGLE_INTERVAL = 15;
	//距離の刻み幅
	private static final double DISTANCE_INTERVAL = 0.1;
	//BoltzMannSelectionで使うtの初期値。試行を繰り返すごとに減少
	private static final double T_DEFAULT = 1;

	//指の長さとか位置
	static final Point3D PALM_BASE_POS = new Point3D(250, 250, 0);
	static final Point3D INDEX_FINGER_BASE_POS = new Point3D(250, 200, 0);
	static final Point3D THUMB_FINGER_BASE_POS = new Point3D(200, 250, 0);
	private static final double INDEX_FINGER_LENGTH = 150;
	private static final double THUMB_FINGER_LENGTH = 100;
	private static final double MIN_BEND_ANGLE = 0;
	private static final double MAX_BEND_ANGLE = 90;
	private static final double MIN_ADDICTION_ANGEL = -20;
	private static final double MAX_ADDICTION_ANGLE = 20;

	private final Hand model;

	public static void main(String[] args) {
		QLearning ql = new QLearning();
		ql.start((long)0);
	}
	
	QLearning() {
		//モデルの初期化
		this.model = new Hand();
	}

	void start(Long sleep) {
		//アクションは、人差し指第三関節の内外転 + 曲がり + 第二関節の曲がり + 第一関節の曲がり
		//+ 親指第二関節の内外転 + 曲がり + 第一関節の曲がりの7自由度
		ActionList actionList = new ActionList(MAX_VARIATION, ANGLE_INTERVAL);
		//必要なのはMAXの距離
		//double defaultXDistance = Math.abs(INDEX_FINGER_BASE_DEFAULT_POS.x - THUMB_FINGER_BASE_DEFAULT_POS.x);
		double defaultYDistance = Math.abs(INDEX_FINGER_BASE_POS.getY() - THUMB_FINGER_BASE_POS.getY());
		//今回はx座標は合わせてあるのでyだけ
		double maxDistance = INDEX_FINGER_LENGTH + THUMB_FINGER_LENGTH + defaultYDistance + 100;
		int stateCount = (int)(maxDistance / DISTANCE_INTERVAL) + 1;
		//int variationCount = (int)((MAX_VARIATION / ANGLE_INTERVAL) * 2) + 1;
		QValueMap qValueMap = new QValueMap(stateCount, actionList.size());

		//温度tの初期化と、減衰する数の準備
		double t = T_DEFAULT;
		double decrementValue = T_DEFAULT / (double)TRY_MAX;
		double minDistance = Double.MAX_VALUE;
		//ループ開始
		for (int tryCount = 0; tryCount < TRY_MAX; tryCount++) {
			//モデルの初期化
			this.model.reset();

			if (tryCount % (TRY_MAX / 100) == 0) {
				System.out.println("進捗率 = " + tryCount / (double)TRY_MAX);
			}
			for (int stepCount = 0; stepCount < STEP_MAX; stepCount++) {
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//行動決定
				List<Double> qValueList = new ArrayList<>();
				double nowIndexAdduction = this.model.indexFinger.firstJointAdductionAngle.get();
				double nowIndex1 = this.model.indexFinger.firstJointBendAngle.get();
				double nowIndex2 = this.model.indexFinger.secondJointBendAngle.get();
				double nowIndex3 = this.model.indexFinger.lastJointBendAngle.get();
				double nowThumbAdduction = this.model.thumbFinger.secondJointAdductionAngle.get();
				double nowThumb1 = this.model.thumbFinger.secondJointBendAngle.get();
				double nowThumb2 = this.model.thumbFinger.lastJointBendAngle.get();

				Point3D nowIndex1Pos = INDEX_FINGER_BASE_POS;
				Point3D nowIndex2Pos = PositionComputer.computeNextPosition(nowIndex1Pos, 50, new Point3D(0, 0, 0), new Point2D(nowIndexAdduction, -nowIndex1));
				Point3D nowIndex3Pos = PositionComputer.computeNextPosition(nowIndex2Pos, 50, new Point3D(nowIndexAdduction, 0, nowIndex1), new Point2D(0, -nowIndex2));
				Point3D nowIndexTickPos = PositionComputer.computeNextPosition(nowIndex3Pos, 50, new Point3D(nowIndexAdduction, 0, nowIndex1 + nowIndex2), new Point2D(0, -nowIndex3));

				Point3D nowThumb1Pos = THUMB_FINGER_BASE_POS;
				Point3D nowThumb2Pos = PositionComputer.computeNextPosition(nowThumb1Pos, 50, new Point3D(0, 0, 90), new Point2D(nowThumbAdduction, nowThumb1));
				Point3D nowThumbTickPos = PositionComputer.computeNextPosition(nowThumb2Pos, 50, new Point3D(nowThumbAdduction, 0, 90 - nowThumb1), new Point2D(0, nowThumb2));

				double nowXDistance = Math.abs(nowIndexTickPos.getX() - nowThumbTickPos.getX());
				double nowYDistance = Math.abs(nowIndexTickPos.getY() - nowThumbTickPos.getY());
				double nowZDistance = Math.abs(nowIndexTickPos.getZ() - nowThumbTickPos.getZ());
				//親指と人差指の先端位置を計算して距離を測る
				double nowDistance = Math.sqrt(Math.pow(nowXDistance, 2) + Math.pow(nowYDistance, 2) + Math.pow(nowZDistance, 2));
				int nowStateIndex = (int)(nowDistance / DISTANCE_INTERVAL);

				for (int actionIndex = 0; actionIndex < actionList.size(); actionIndex++) {
					qValueList.add(qValueMap.getQValue(nowStateIndex, actionIndex));
				}
				int actionIndex = BoltzMannSelection.select(qValueList, t);
				Action action = actionList.get(actionIndex);

				//行動実行
				double nextIndexAdduction = nowIndexAdduction + action.indexJoint1Adduction;
				double nextIndex1 = nowIndex1 + action.indexJoint1;
				double nextIndex2 = nowIndex2 + action.indexJoint2;
				double nextIndex3 = nowIndex3 + action.indexJoint3;
				double nextThumbAdduction = nowThumbAdduction + action.thumbJoint1Adduction;
				double nextThumb1 = nowThumb1 + action.thumbJoint1;
				double nextThumb2 = nowThumb2 + action.thumbJoint2;
				this.model.indexFinger.firstJointAdductionAngle.set(nextIndexAdduction);
				this.model.indexFinger.firstJointBendAngle.set(nextIndex1);
				this.model.indexFinger.secondJointBendAngle.set(nextIndex2);
				this.model.indexFinger.lastJointBendAngle.set(nextIndex3);
				this.model.thumbFinger.secondJointAdductionAngle.set(nextThumbAdduction);
				this.model.thumbFinger.secondJointBendAngle.set(nextThumb1);
				this.model.thumbFinger.lastJointBendAngle.set(nextThumb2);

				Point3D nextIndex1Pos = INDEX_FINGER_BASE_POS;
				this.model.indexFinger.firstJointPos.set(nextIndex1Pos);
				Point3D nextIndex2Pos = PositionComputer.computeNextPosition(nextIndex1Pos, 50, new Point3D(0, 0, 0), new Point2D(nextIndexAdduction, -nextIndex1));
				this.model.indexFinger.secondJointPos.set(nextIndex2Pos);
				Point3D nextIndex3Pos = PositionComputer.computeNextPosition(nextIndex2Pos, 50, new Point3D(nextIndexAdduction, 0, nextIndex1), new Point2D(0, -nextIndex2));
				this.model.indexFinger.lastJointPos.set(nextIndex3Pos);
				Point3D nextIndexTickPos = PositionComputer.computeNextPosition(nextIndex3Pos, 50, new Point3D(nextIndexAdduction, 0, nextIndex1 + nextIndex2), new Point2D(0, -nextIndex3));
				this.model.indexFinger.tipPos.set(nextIndexTickPos);

				Point3D nextThumb1Pos = THUMB_FINGER_BASE_POS;
				this.model.thumbFinger.secondJointPos.set(nextThumb1Pos);
				Point3D nextThumb2Pos = PositionComputer.computeNextPosition(nextThumb1Pos, 50, new Point3D(0, 0, 90), new Point2D(nextThumbAdduction, nextThumb1));
				//Point3D nextThumb2Pos = PositionComputer.computeNextPosition(nextThumb1Pos, 50, new Point3D(0, 0, 90), new Point2D(nextThumbAdduction, 90));
				this.model.thumbFinger.lastJointPos.set(nextThumb2Pos);
				Point3D nextThumbTickPos = PositionComputer.computeNextPosition(nextThumb2Pos, 50, new Point3D(nextThumbAdduction, 0, 90 - nextThumb1), new Point2D(0, nextThumb2));
				//Point3D nextThumbTickPos = PositionComputer.computeNextPosition(nextThumb2Pos, 50, new Point3D(nextThumbAdduction, 0, 90 - 90), new Point2D(0, 10));

				this.model.thumbFinger.tipPos.set(nextThumbTickPos);
				double nextXDistance = Math.abs(nextIndexTickPos.getX() - nextThumbTickPos.getX());
				double nextYDistance = Math.abs(nextIndexTickPos.getY() - nextThumbTickPos.getY());
				double nextZDistance = Math.abs(nextIndexTickPos.getZ() - nextThumbTickPos.getZ());
				//親指と人差指の先端位置を計算して距離を測る
				double nextDistance = Math.sqrt(Math.pow(nextXDistance, 2) + Math.pow(nextYDistance, 2) + Math.pow(nextZDistance, 2));	

				int nextStateIndex = (int)(nextDistance / DISTANCE_INTERVAL);
				if (!this.isValidFingerAngle(model)) {
					qValueMap.updateQValue(nowStateIndex, actionIndex, -1);
					break;
				}

				if (this.isGoal(nextDistance)) {
					System.out.println("goal");
					qValueMap.updateQValue(nowStateIndex, actionIndex, 1);
					break;
				}

				//Q値の更新
				//まずは報酬の計算。
				double reward = this.computeReward(nextDistance);
				if (nextDistance < minDistance) {
					minDistance = nextDistance;
				}

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
		Platform.runLater(() -> {
			String allText = "";
			for (int index = 0; index < (int)(maxDistance / DISTANCE_INTERVAL) + 1; index++) {
				String distance = "Distance = " + index * DISTANCE_INTERVAL;
				double maxQValue = -Double.MAX_VALUE;
				int maxIndex = 0;
				double[] qValues = qValueMap.getQValues(index);
				for (int actionIndex = 0; actionIndex < actionList.size(); actionIndex++) {
					double qValue = qValues[actionIndex];
					if (maxQValue < qValue) {
						maxQValue = qValue;
						maxIndex = actionIndex;
					}
				}
				String qValue = "QValue = " + maxQValue;
				String action = "action = " + actionList.get(maxIndex).toString();
				allText = allText.concat((distance + "\n" + qValue + "\n" + action + "\n"));
			}
			ClipboardContent content = new ClipboardContent();
			content.putString(allText);
			Clipboard.getSystemClipboard().setContent(content);
		});

	}

	//報酬の計算
	private final double computeReward(double distance) {
		return this.isGoal(distance) ? 1 : 1 / distance; 
	}

	//関節可動範囲かどうか.今回は全関節 0 ~ 90とする
	private final boolean isValidFingerAngle(Hand model) {
		double index1Adduction = this.model.indexFinger.firstJointAdductionAngle.get();
		boolean a = MIN_BEND_ANGLE <= index1Adduction && index1Adduction <= MAX_BEND_ANGLE;
		double index1 = this.model.indexFinger.firstJointBendAngle.get();
		boolean b = MIN_BEND_ANGLE <= index1 && index1 <= MAX_BEND_ANGLE;
		double index2 = this.model.indexFinger.secondJointBendAngle.get();
		boolean c = MIN_BEND_ANGLE <= index2 && index2 <= MAX_BEND_ANGLE;
		double index3 = this.model.indexFinger.lastJointBendAngle.get();
		boolean d = MIN_BEND_ANGLE <= index3 && index3 <= MAX_BEND_ANGLE;

		double thumb1Adduction = this.model.thumbFinger.secondJointAdductionAngle.get();
		boolean e = MIN_BEND_ANGLE <= thumb1Adduction && thumb1Adduction <= MAX_BEND_ANGLE;
		double thumb1 = this.model.thumbFinger.secondJointBendAngle.get();;
		boolean f = MIN_BEND_ANGLE <= thumb1 && thumb1 <= MAX_BEND_ANGLE;
		double thumb2 = this.model.thumbFinger.lastJointBendAngle.get();
		boolean g = MIN_BEND_ANGLE <= thumb2 && thumb2 <= MAX_BEND_ANGLE;
		return a && b && c && d && e && f && g;
	}

	//終了条件を満たしているかどうか
	private final boolean isGoal(double distance) {
		return distance < 5;
	}

	final Hand getModel() {
		return this.model;
	}
}