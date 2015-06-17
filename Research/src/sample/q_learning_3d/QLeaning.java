package sample.q_learning_3d;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point3D;
import sample.boltzmann_selection.BoltzMannSelection;


public final class QLeaning {
	private static final int TRY_MAX = 10000; //試行回数
	private static final int STEP_MAX = 10000; //1試行あたりの最大行動回数
	private static final double T_DEFAULT = 1;

	public static final int AREA_WIDTH = 100;  //空間の横
	public static final int AREA_HEIGHT = 100; //空間の縦
	public static final int AREA_DEPTH = 100;  //空間の奥

	public static final Point3D goal = new Point3D(50, 50, 50);

	private final QValueMap qValueMap;
	private final ActionList actionList;
	private final Model model;

	public QLeaning() {
		//actionとqValueのマップの初期化
		this.actionList = new ActionList();
		this.qValueMap = new QValueMap(AREA_WIDTH, AREA_HEIGHT, AREA_DEPTH, this.actionList);
		this.model = new Model();
	}

	public final void start() {
		//温度tの初期化と、減衰する数の準備
		double t = T_DEFAULT;
		double decrementValue = T_DEFAULT / (double)TRY_MAX;

		//ループ開始
		for (int tryCount = 0; tryCount < TRY_MAX; tryCount++) {

			//モデルの初期化
			this.model.setPosition(0, 0, 0);
			
			//ステップ開始
			for (int stepCount = 0; stepCount < STEP_MAX; stepCount++) {

				//行動選択
				List<Double> qValueList = new ArrayList<>();
				for (int actionIndex = 0; actionIndex < this.actionList.size(); actionIndex++) {
					double qValue = this.qValueMap.getQValue(this.model.getPositionX(), this.model.getPositionY(), this.model.getPositionZ(), actionIndex);
					qValueList.add(qValue);
				}
				int selectedActionIndex = BoltzMannSelection.select(qValueList, t);
				Point3D selectedAction = this.actionList.getAction(selectedActionIndex);
				//行動を実行
				this.model.move(selectedAction);

				//q値の更新
				this.qValueMap.updateQValue(this.model.getPositionX() - selectedAction.getX(), this.model.getPositionY() - selectedAction.getY(), this.model.getPositionZ() - selectedAction.getZ(), selectedActionIndex);

				//範囲外に出てたら終了
				boolean modelXPosIsValid = 0 <= this.model.getPositionX() && this.model.getPositionX() < AREA_WIDTH;
				boolean modelYPosIsValid = 0 <= this.model.getPositionY() && this.model.getPositionY() < AREA_HEIGHT;
				boolean modelZPosIsValid = 0 <= this.model.getPositionZ() && this.model.getPositionZ() < AREA_DEPTH;
				if (!modelYPosIsValid || !modelXPosIsValid || ! modelZPosIsValid) {
					break;
				}

				if (this.model.getPositionX() == goal.getX() && this.model.getPositionY() == goal.getY() && this.model.getPositionZ() == goal.getZ()) {
					break;
				}
			}
			//tを減衰
			t -= decrementValue;
		}
		System.out.println(this.qValueMap.getQValue(50, 50, 48, 2));
	}
}