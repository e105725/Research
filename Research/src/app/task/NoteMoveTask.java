package app.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

//List内のノーツを動かすタスク
public final class NoteMoveTask extends Task<Void> {
	private final List<Pane> paneList;
	private final List<Button> noteList;
	
	public NoteMoveTask(List<Pane> _noteList) {
		this.paneList = _noteList;
		this.noteList = new ArrayList<>();
	}

	@Override
	protected Void call() throws Exception {
		int index = 0;
		while (true) {
			Iterator<Button> iterator = this.noteList.iterator();
			while (iterator.hasNext()) {
				Node note = iterator.next();
				Platform.runLater(() -> note.setTranslateY(note.getTranslateY() + 1));
				if (100 <= note.getTranslateY()) {
					Pane parent = (Pane) note.getParent();
					Platform.runLater(() -> parent.getChildren().remove(note));
					iterator.remove();
				}
			}
			boolean isNoteAdd = index % 100== 0;
			if (isNoteAdd) {
				Button button = new Button();
				this.noteList.add(button);
				Platform.runLater(() -> {
					this.paneList.get(0).getChildren().add(button);
				}); 
			}

			Thread.sleep(10);
			index++;
		}
	}
}