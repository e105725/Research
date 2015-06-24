package learn_pinch_2d;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import sample.boltzmann_selection.BoltzMannSelection;

public final class QLearning {
	public static void main(String[] args) {
		QLearning ql = new QLearning();
		ql.start(0);
	}
	//試行回数
	private static final int TRY_MAX = 10000;
	//1試行あたりの最大行動回数
	private static final int STEP_MAX = 10000;
	//割引率
	private static final double DISCOUNT = 0.8;
	//学習率
	private static final double STUDY = 0.5;
	//関節が一回でどの程度動かせるか
	private static final double MAX_VARIATION = 5;
	//関節角の刻み幅
	private static final double INTERVAL = 1;
	//BoltzMannSelectionで使うtの初期値。試行を繰り返すごとに減少
	private static final double T_DEFAULT = 1;

	//指の長さの設定
	static final Point2D INDEX_FINGER_BASE_POS = new Point2D(250, 200);
	static final Point2D THUMB_FINGER_BASE_POS = new Point2D(200, 250);
	private static final double INDEX_FINGER_LENGTH = 150;
	private static final double THUMB_FINGER_LENGTH = 100;
	private static final double MIN_ANGLE = 0;
	private static final double MAX_ANGLE = 90;

	private final Hand model;
	QLearning() {
		//モデルの初期化
		this.model = new Hand();
	}

	void start(long sleep) {
		ActionList actionList = new ActionList(MAX_VARIATION, INTERVAL);

		//必要なのはMAXの距離
		//double defaultXDistance = Math.abs(INDEX_FINGER_BASE_DEFAULT_POS.x - THUMB_FINGER_BASE_DEFAULT_POS.x);
		double defaultYDistance = Math.abs(INDEX_FINGER_BASE_POS.getY() - THUMB_FINGER_BASE_POS.getY());
		//今回はx座標は合わせてあるのでyだけ
		double maxDistance = INDEX_FINGER_LENGTH + THUMB_FINGER_LENGTH + defaultYDistance;
		QValueMap qValueMap = new QValueMap(maxDistance, INTERVAL, actionList);

		//温度tの初期化と、減衰する数の準備
		double t = T_DEFAULT;
		double decrementValue = T_DEFAULT / (double)TRY_MAX;
		double minDistance = Double.MAX_VALUE;
		//ループ開始
		for (int tryCount = 0; tryCount < TRY_MAX; tryCount++) {
			//モデルの初期化
			this.model.reset();

			if (tryCount % (TRY_MAX / 100) == 0) {
				System.out.println("進捗率 = " + (int)(((tryCount / (double)TRY_MAX) * 100) + 0.5) + "%");
			}
			for (int stepCount = 0; stepCount < STEP_MAX; stepCount++) {
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//行動決定
				List<Double> qValueList = new ArrayList<>();
				double nowIndex1 = this.model.getIndexFingerJointList().get(0).getAngle();
				double nowIndex2 = this.model.getIndexFingerJointList().get(1).getAngle();
				double nowIndex3 = this.model.getIndexFingerJointList().get(2).getAngle();
				double nowThumb1 = this.model.getThumbFingerJointList().get(0).getAngle();
				double nowThumb2 = this.model.getThumbFingerJointList().get(1).getAngle();

				Point2D nowIndex1Pos = INDEX_FINGER_BASE_POS;
				Point2D nowIndex2Pos = this.computePos(nowIndex1Pos, 90, nowIndex1);
				Point2D nowIndex3Pos = this.computePos(nowIndex2Pos, nowIndex1, nowIndex2);
				Point2D nowIndexTickPos = this.computePos(nowIndex3Pos, nowIndex2, nowIndex3);
				
				Point2D nowThumb1Pos = THUMB_FINGER_BASE_POS;
				Point2D nowThumb2Pos = this.computePos(nowThumb1Pos, 180, nowThumb1);
				Point2D nowThumbTickPos = this.computePos(nowThumb2Pos, nowThumb1, nowThumb2);
				
				double nowXDistance = Math.abs(nowIndexTickPos.getX() - nowThumbTickPos.getX());
				double nowYDistance = Math.abs(nowIndexTickPos.getY() - nowThumbTickPos.getY());
				//親指と人差指の先端位置を計算して距離を測る
				double nowDistance = Math.sqrt(Math.pow(nowXDistance, 2) + Math.pow(nowYDistance, 2));
				int nowStateIndex = (int)(nowDistance / INTERVAL);
				for (int actionIndex = 0; actionIndex < actionList.size(); actionIndex++) {
					qValueList.add(qValueMap.getQValue(nowStateIndex, actionIndex));
				}
				int actionIndex = BoltzMannSelection.select(qValueList, t);
				Action action = actionList.get(actionIndex);

				//行動実行
				double nextIndex1 = nowIndex1 + action.indexJoint1;
				double nextIndex2 = nowIndex2 + action.indexJoint2;
				double nextIndex3 = nowIndex3 + action.indexJoint3;
				double nextThumb1 = nowThumb1 + action.thumbJoint1;
				double nextThumb2 = nowThumb2 + action.thumbJoint2;

				this.model.getIndexFingerJointList().get(0).setAngle(nextIndex1);
				this.model.getIndexFingerJointList().get(1).setAngle(nextIndex2);
				this.model.getIndexFingerJointList().get(2).setAngle(nextIndex3);
				this.model.getThumbFingerJointList().get(0).setAngle(nextThumb1);
				this.model.getThumbFingerJointList().get(1).setAngle(nextThumb2);
				
				Point2D nextIndex1Pos = INDEX_FINGER_BASE_POS;
				this.model.getIndexFingerJointList().get(0).position.set(nextIndex1Pos);
				Point2D nextIndex2Pos = this.computePos(nextIndex1Pos, 90, nextIndex1);
				this.model.getIndexFingerJointList().get(1).position.set(nextIndex2Pos);
				Point2D nextIndex3Pos = this.computePos(nextIndex2Pos, 90 + nextIndex1, nextIndex2);
				this.model.getIndexFingerJointList().get(2).position.set(nextIndex3Pos);
				Point2D nextIndexTickPos = this.computePos(nextIndex3Pos, 90 + nextIndex1 + nextIndex2, nextIndex3);
				this.model.indexTickPos.set(nextIndexTickPos);
				
				Point2D nextThumb1Pos = THUMB_FINGER_BASE_POS;
				this.model.getThumbFingerJointList().get(0).position.set(nextThumb1Pos);
				Point2D nextThumb2Pos = this.computePos(nextThumb1Pos, 180, -nextThumb1);
				this.model.getThumbFingerJointList().get(1).position.set(nextThumb2Pos);
				Point2D nextThumbTickPos = this.computePos(nextThumb2Pos, 180 - nextThumb1, -nextThumb2);
				this.model.thumbTickPos.set(nextThumbTickPos);
				
				double nextXDistance = Math.abs(nextIndexTickPos.getX() - nextThumbTickPos.getX());
				double nextYDistance = Math.abs(nextIndexTickPos.getY() - nextThumbTickPos.getY());
				//親指と人差指の先端位置を計算して距離を測る
				double nextDistance = Math.sqrt(Math.pow(nextXDistance, 2) + Math.pow(nextYDistance, 2));	

				int nextStateIndex = (int)(nextDistance / INTERVAL);
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
//					System.out.println("nextIndexPos =" + nextIndexTickPos);
//					System.out.println("nextThumbPos =" + nextThumbTickPos);
//					System.out.println("nextDistance = " + nextDistance);
//					System.out.println("reward = " + reward);
					
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
			for (int index = 0; index < (int)(maxDistance / INTERVAL) + 1; index++) {
				String distance = "Distance = " + index * INTERVAL;
				
				String qValue = "QValue = " + qValueMap.searchMaxQValue(index);
				allText = allText.concat((distance + "\n" + qValue + "\n"));
			}
			ClipboardContent content = new ClipboardContent();
			content.putString(allText);
			Clipboard.getSystemClipboard().setContent(content);
		});
	}
	
	private final Point2D computePos(Point2D basePos, double baseAngle, double angle) {
		double length = 50;
		double actualAngle = baseAngle + angle;
		
		double lengthX = Math.cos(Math.PI * actualAngle / 180.0) * length;
		double lengthY = Math.sin(Math.PI * actualAngle / 180.0) * length;
		
		Point2D pos = new Point2D(basePos.getX() + lengthX, basePos.getY() - lengthY);
		return pos;
	}

	//報酬の計算
	private final double computeReward(double distance) {
		return this.isGoal(distance) ? 1 : 1 / distance; 
	}

	//関節可動範囲かどうか.今回は全関節 0 ~ 90とする
	private final boolean isValidFingerAngle(Hand model) {
		double index1 = this.model.getIndexFingerJointList().get(0).getAngle();
		boolean a = MIN_ANGLE <= index1 && index1 <= MAX_ANGLE;
		double index2 = this.model.getIndexFingerJointList().get(1).getAngle();
		boolean b = MIN_ANGLE <= index2 && index2 <= MAX_ANGLE;
		double index3 = this.model.getIndexFingerJointList().get(2).getAngle();
		boolean c = MIN_ANGLE <= index3 && index3 <= MAX_ANGLE;
		double thumb1 = this.model.getThumbFingerJointList().get(0).getAngle();
		boolean d = MIN_ANGLE <= thumb1 && thumb1 <= MAX_ANGLE;
		double thumb2 = this.model.getThumbFingerJointList().get(1).getAngle();
		boolean e = MIN_ANGLE <= thumb2 && thumb2 <= MAX_ANGLE;
		return a && b && c && d && e;
	}

	//終了条件を満たしているかどうか
	private final boolean isGoal(double distance) {
		return distance < 5;
	}

	final Hand getModel() {
		return this.model;
	}
}