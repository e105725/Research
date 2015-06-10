package app.ui.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import app.UIManager;

public final class LinePane extends AnchorPane {
	private static final double TIMING_BAR_SIZE = 50;
	private static final double FINGER_BAR_SIZE = 10;

	private static final double TIMING_BAR_POSITION_RATE = 0.8;
	private static final double FINGER_BAR_DEFAULT_POSITION_RATE = 0.1;

	public LinePane() {
		Pane timingBar = this.createBar(TIMING_BAR_SIZE);
		timingBar.setStyle("-fx-background-color: #000000aa;");
		timingBar.translateYProperty().bind(this.heightProperty().multiply(TIMING_BAR_POSITION_RATE));
		this.getChildren().add(timingBar);

		Pane fingerBar = this.createBar(FINGER_BAR_SIZE);
		fingerBar.setStyle("-fx-background-color: red;");
		//		DoubleBinding binding = Bindings.createDoubleBinding(
		//				() -> this.computeFingerPos(this.isClicked.get()), this.isClicked, this.heightProperty());
		//		fingerBar.translateYProperty().bind(binding);
		this.getChildren().add(fingerBar);

		HogeTask task = new HogeTask(this.getChildren());
		UIManager.addTask(task);
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(task);
	}

	private static final class HogeTask extends Task<Void> {
		private final ObservableList<Node> children;
		private final List<Node> noteList;

		private HogeTask(ObservableList<Node> _children) {
			this.children = _children;
			this.noteList = new ArrayList<>();
		}

		@Override
		protected Void call() throws Exception {
			int index = 0;
			while (true) {
				if (this.isCancelled()) {
					return null;
				}
				try {
					Thread.sleep(10);
				} catch (IllegalThreadStateException e) {
					System.err.println(e);
				}
				if (!UIManager.getState()) {
					continue;
				}
				Iterator<Node> iterator = this.noteList.iterator();
				while (iterator.hasNext()) {
					Node note = iterator.next();
					Platform.runLater(() -> note.setTranslateY(note.getTranslateY() + 1));
					if (600 <= note.getTranslateY()) {
						Platform.runLater(() -> this.children.remove(note));
						iterator.remove();
					}
				}
				boolean isNoteAdd = index % 100== 0;
				if (isNoteAdd) {
					Button button = new Button();
					this.noteList.add(button);
					Platform.runLater(() -> this.children.add(button)); 
				}
				index++;
			}
		}
	}

	private final double computeFingerPos(boolean isClicked) {
		if (!isClicked) {
			return FINGER_BAR_DEFAULT_POSITION_RATE * this.getHeight();
		}
		return (this.getHeight() * TIMING_BAR_POSITION_RATE) + (TIMING_BAR_SIZE / 2.0);
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