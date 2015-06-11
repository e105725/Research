package sample.leap.ui;

import java.net.URL;

import sample.leap.DataManager;
import sample.leap.UIManager;
import sample.leap.ui.content.LinePane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import library.AnchorPaneHelper;
import library.FXMLControl;
import library.GridPaneHelper;

public final class MainPane extends FXMLControl {
	private static final URL URL = MainPane.class.getResource("MainPane.fxml");

	@FXML private AnchorPane contentBase;
	@FXML private Button startButton;
	@FXML private Button stopButton;
	@FXML private Label noteCountLabel;
	@FXML private Label scoreLabel;

	public MainPane() {
		super(URL);

		this.startButton.setOnAction(
				event -> UIManager.setState(true));

		this.stopButton.setOnAction(
				event -> UIManager.setState(false));

		GridPane gridPane = new GridPane();
		gridPane.setGridLinesVisible(true);
		GridPaneHelper.initConstraints(gridPane, UIManager.LINE_COUNT, 1);
		for (int index = 0; index < UIManager.LINE_COUNT; index++) {
			gridPane.add(new LinePane(), index, 0);
		}
		AnchorPaneHelper.fitToAnchorPane(gridPane, this.contentBase);

		this.noteCountLabel.textProperty().bind(DataManager.noteCountProperty().asString());
		this.scoreLabel.textProperty().bind(DataManager.scoreProperty().asString());
	}
}