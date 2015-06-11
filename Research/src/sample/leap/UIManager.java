package sample.leap;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;

public final class UIManager {
	public static final int LINE_COUNT = 4;
	
	private static final UIManager self = new UIManager();
	
	private final BooleanProperty state;
	private final List<Task<Void>> taskList;
	
	private UIManager() {
		this.state = new SimpleBooleanProperty(false);
		this.taskList = new ArrayList<>();
	}
	
	public static final void addTask(Task<Void> task) {
		self.taskList.add(task);
	}
	
	public static final boolean getState() {
		return self.state.get();
	}
	
	public static final BooleanProperty stateProperty() {
		return self.state;
	}
	
	public static final void setState(boolean _state) {
		self.state.set(_state);
	}

	public static final void taskCancel() {
		self.taskList.forEach(task -> task.cancel());
		self.taskList.clear();
	}
}