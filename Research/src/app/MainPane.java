package app;

import javafx.scene.layout.GridPane;
import library.GridPaneHelper;
import app.line.LinePane;

public final class MainPane extends GridPane {
	private static final int LINE_COUNT = 4;

	public MainPane() {
		GridPaneHelper.initConstraints(this, LINE_COUNT, 1);
		this.setGridLinesVisible(true);
		for (int index = 0; index < LINE_COUNT; index++) {
			LinePane linePane = new LinePane();
			this.add(linePane, index, 0);
		}
//		
//		CountUpTask task = new CountUpTask(1000, 1, 10);
//		this.timingBar.translateYProperty().bind(task.progressProperty().multiply(this.heightProperty().multiply(0.8)));
//		Executor executor = Executors.newSingleThreadExecutor();
//		executor.execute(task);
//		DataManager.frameProperty().addListener(
//				(observable, oldValue, newValue) -> this.hoge(newValue));
	}
}