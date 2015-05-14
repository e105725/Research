package app.ui;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import library.FXMLControl;

public final class MainPane extends FXMLControl {
	private static final URL URL = MainPane.class.getResource("MainPane.fxml");
	private static final int LINE_COUNT = 4;

	@FXML private AnchorPane contentBase;
	@FXML private Button startButton;
	@FXML private Button stopButton;
	@FXML private Label numeratorLabel;
	@FXML private Label denominatorLabel;
	
	public MainPane() {
		super(URL);
//		GridPaneHelper.initConstraints(this, LINE_COUNT, 1);
//		this.setGridLinesVisible(true);
//		for (int index = 0; index < LINE_COUNT; index++) {
//			LinePane linePane = new LinePane();
//			this.add(linePane, index, 0);
//		}
//		
//		CountUpTask task = new CountUpTask(1000, 1, 10);
//		this.timingBar.translateYProperty().bind(task.progressProperty().multiply(this.heightProperty().multiply(0.8)));
//		Executor executor = Executors.newSingleThreadExecutor();
//		executor.execute(task);
//		DataManager.frameProperty().addListener(
//				(observable, oldValue, newValue) -> this.hoge(newValue));
	}
}