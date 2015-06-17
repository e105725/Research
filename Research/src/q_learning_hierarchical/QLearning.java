package q_learning_hierarchical;

final class QLearning {
	private static final int TRY_MAX = 1000000; //試行回数
	private static final int STEP_MAX = 1000;   //1試行あたりの最大行動回数

	public static final int AREA_WIDTH = 100;  //空間の横
	public static final int AREA_HEIGHT = 100; //空間の縦

	public static final Point goal = new Point(50, 50); //ゴールの位置
	private static final double T_DEFAULT = 1;

	private final QValueMap qValueMap;
	private final ActionList actionList;
	private final Model model;

	QLearning() {
		//actionとqValueのマップの初期化
		this.actionList = new ActionList();
		this.qValueMap = new QValueMap(AREA_WIDTH, AREA_HEIGHT, this.actionList);
		this.model = new Model();
	}

	void start() {
		//温度tの初期化と、減衰する数の準備
		double t = T_DEFAULT;
		double decrementValue = T_DEFAULT / (double)TRY_MAX;
		
		//ループ開始
		for (int tryCount = 0; tryCount < TRY_MAX; tryCount++) {
			//モデルの初期化
			this.model.init();
			
			for (int stepCount = 0; stepCount < STEP_MAX; stepCount++) {
				//上位階層の行動決定。上位階層では根本と選択の位置を決める
				
				
				//下位階層の行動決定。根本と先端の位置が決定しているので、それを考えてjointの行動を決定する
					//報酬Riの計算。報酬の与え方は根本・ジョイント・先端の各距離が一定に近いと高報酬、各ジョイントの部分の関節角が一定に近いと高報酬
					//Qi(si, ai)の更新
				
				//Rp+cの計算
				
				//Q(s, a)の更新
			}
			//tを減衰
			t -= decrementValue;
		}
	}
}