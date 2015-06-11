package sample.leap.concurrent;

import java.util.ArrayList;
import java.util.List;

import sample.leap.UIManager;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

//List内のノーツを動かすタスク
public final class NoteMoveTask extends Task<Void> {
	private final List<Pane> lineList;
	private final List<Node> noteList;

	public NoteMoveTask(List<Pane> _noteList) {
		this.lineList = _noteList;
		this.noteList = new ArrayList<>();
	}

	@Override
	protected Void call() throws Exception {
		int index = 0;
		while (true) {
			System.out.println("A");
			if (this.isCancelled()) {
				return null;
			}
			System.out.println("B");
			try {
				Thread.sleep(100);
			} catch (IllegalThreadStateException e) {
				System.err.println(e);
			}
			if (!UIManager.getState()) {
				continue;
			}
			//			Iterator<Button> iterator = this.noteList.iterator();
			//			while (iterator.hasNext()) {
			//				Node note = iterator.next();
			//				Platform.runLater(() -> note.setTranslateY(note.getTranslateY() + 1));
			//				if (100 <= note.getTranslateY()) {
			//					Pane parent = (Pane) note.getParent();
			//					Platform.runLater(() -> parent.getChildren().remove(note));
			//					iterator.remove();
			//				}
			//			}
			//			boolean isNoteAdd = index % 100== 0;
			//			if (isNoteAdd) {
			//				Button button = new Button();
			//				this.noteList.add(button);
			//				Platform.runLater(() -> {
			//					this.paneList.get(0).getChildren().add(button);
			//				}); 
			//			}
			System.out.println("hoge");
			index++;
		}
	}
}