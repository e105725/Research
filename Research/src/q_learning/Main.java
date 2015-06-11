package q_learning;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import library.GridPaneHelper;
import sample.boltzmann_selection.BoltzMannSelection;

public final class Main extends Application {
	private static final int TRY_MAX = 1000000; //試行回数
	private static final int STEP_MAX = 1000; //1試行あたりの最大行動回数

	public static final int AREA_WIDTH = 100;  //空間の横
	public static final int AREA_HEIGHT = 100; //空間の縦
	public static final int AREA_DEPTH = 100;  //空間の奥

	public static final Point goal = new Point(50, 50); //ゴールの位置

	private static final double T_DEFAULT = 1;

	private static QValueMap hoge;

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

				if (model.getPositionX() == goal.x && model.getPositionY() == goal.y) {
					break;
				}
			}
			//tを減衰
			t -= decrementValue;
		}

		hoge = qValueMap;
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane grid = new GridPane();
		GridPaneHelper.initConstraints(grid, AREA_WIDTH, AREA_HEIGHT);

		for (int x = 0; x < AREA_WIDTH; x++) {
			for (int y = 0; y < AREA_HEIGHT; y++) {
				double max = -Double.MAX_VALUE;
				int maxIndex = -1;
				for (int actionIndex = 0; actionIndex < 4; actionIndex++) {
					double qValue = hoge.getQValue(x, y, actionIndex);
					if (max < qValue) {
						maxIndex = actionIndex;
						max = qValue;
					}
					if (max == 0.01) {
						maxIndex = -1;
					}
				}
				Label label = new Label("-");
				grid.add(label, x, y);
				label.setFont(Font.font(10));
				if (x == (int)goal.x && y == (int)goal.y) {
					label.setStyle("-fx-background-color: red;");
				}
					
				switch (maxIndex) {
				case 0:
					label.setText("↓");
					break;
				case 1:
					label.setText("↑");
					break;
				case 2:
					label.setText("→");
					break;
				case 3:
					label.setText("←");
					break;
				}
			}
		}
		primaryStage.setScene(new Scene(grid));
		primaryStage.show();
	}
}