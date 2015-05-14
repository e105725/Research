package app.ui;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import library.FXMLControl;
import app.UIManager;

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
		
		this.startButton.setOnAction(event -> UIManager.setState(true));
		this.stopButton.setOnAction(event -> UIManager.setState(false));
	}
}