package app.line;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public final class LinePane extends AnchorPane {
	private static final double TIMING_BAR_SIZE = 50;
	private static final double FINGER_BAR_SIZE = 10;

	private static final double TIMING_BAR_POSITION_RATE = 0.8;
	private static final double FINGER_BAR_DEFAULT_POSITION_RATE = 0.1;

	private final BooleanProperty isClicked;

	public LinePane() {
		this.isClicked = new SimpleBooleanProperty(false);
		this.setOnMousePressed(event -> this.isClicked.set(true));
		this.setOnMouseReleased(event -> this.isClicked.set(false));

		Pane timingBar = this.createBar(TIMING_BAR_SIZE);
		timingBar.setStyle("-fx-background-color: #000000aa;");
		timingBar.translateYProperty().bind(this.heightProperty().multiply(TIMING_BAR_POSITION_RATE));
		this.getChildren().add(timingBar);

		Pane fingerBar = this.createBar(FINGER_BAR_SIZE);
		fingerBar.setStyle("-fx-background-color: red;");
		DoubleBinding binding = Bindings.createDoubleBinding(
				() -> this.computeFingerPos(this.isClicked.get()), this.isClicked, this.heightProperty());
		fingerBar.translateYProperty().bind(binding);
		this.getChildren().add(fingerBar);
		
		Pane hoge = this.createBar(10);
		hoge.setStyle("-fx-background-color: blue;");
		DoubleProperty property = new SimpleDoubleProperty();
		hoge.translateYProperty().bind(property);
		
		CountUpTask task = new CountUpTask(10, 10);
		task.getTargetList().add(property);
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(task);
	}

	private final double computeFingerPos(boolean isClicked) {
		if (!isClicked) {
			return FINGER_BAR_DEFAULT_POSITION_RATE * this.getHeight();
		}
		return (this.getHeight() * TIMING_BAR_POSITION_RATE) + (TIMING_BAR_SIZE / 2.0);
	}

	public final BooleanProperty clickedProperty() {
		return this.isClicked;
	}

	private final Pane createBar(double height) {
		Pane pane = new Pane();
		AnchorPane.setRightAnchor(pane, 0.0);
		AnchorPane.setLeftAnchor(pane, 0.0);
		pane.setMaxHeight(height);
		pane.setMinHeight(height);
		return pane;
	}
}