package q_learning;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import sample.boltzmann_selection.BoltzMannSelection;

public final class Main {
	private static final int TRY_MAX = 1000000; //試行回数
	private static final int STEP_MAX = 100; //1試行あたりの最大行動回数

	public static final int AREA_WIDTH = 10;  //空間の横
	public static final int AREA_HEIGHT = 10; //空間の縦
	public static final int AREA_DEPTH = 10;  //空間の奥

	public static final Point goal = new Point(9, 9); //ゴールの位置

	private static final double T_DEFAULT = 1;

	public static void main(String[] args) {
		//actionとqValueのマップの初期化
		ActionList actionList = new ActionList();
		QValueMap qValueMap = new QValueMap(AREA_WIDTH, AREA_HEIGHT, actionList);

		//温度tの初期化と、減衰する数の準備
		double t = T_DEFAULT;
		double decrementValue = T_DEFAULT / (double)TRY_MAX;

		//ループ開始
		for (int tryCount = 0; tryCount < TRY_MAX; tryCount++) {
			//モデルの初期化
			Model model = new Model(0, 0, 0);
			for (int stepCount = 0; stepCount < STEP_MAX; stepCount++) {
				//行動選択
				List<Double> qValueList = new ArrayList<>();
				for (int actionIndex = 0; actionIndex < actionList.size(); actionIndex++) {
					double qValue = qValueMap.getQValue((int)model.getPositionX(), (int)model.getPositionY(), actionIndex);
					qValueList.add(qValue);
				}
				int selectedActionIndex = BoltzMannSelection.select(qValueList, t);
				Point selectedAction = actionList.getAction(selectedActionIndex);
				//行動を実行
				model.move(selectedAction);
				
				//q値の更新
				qValueMap.updateQValue((int)model.getPositionX() - selectedAction.x, (int)model.getPositionY() - selectedAction.y, selectedActionIndex);
				
				//範囲外に出てたら終了
				boolean modelXPosIsValid = 0 <= model.getPositionX() && model.getPositionX() < AREA_WIDTH;
				boolean modelYPosIsValid = 0 <= model.getPositionY() && model.getPositionY() < AREA_HEIGHT;
				if (!modelYPosIsValid || !modelXPosIsValid) {
					break;
				}

				if ((int)model.getPositionX() == 9 && (int)model.getPositionY() == 9) {
					break;
				}
			}
			//tを減衰
			t -= decrementValue;
		}
		
		//q値の出力
		for (int x = 0; x < AREA_WIDTH; x++) {
			for (int y = 0; y < AREA_HEIGHT; y++) {
				for (int actionIndex = 0; actionIndex < actionList.size(); actionIndex++) {
					System.out.println("x = " + x + "; y = " + y + "; action = " + actionIndex + "; qValue = " + qValueMap.getQValue(x, y, actionIndex));
					//System.out.println();
				}
			}
		}
	}
}