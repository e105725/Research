package app;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public final class UIManager {
	private static final UIManager self = new UIManager();
	
	private final BooleanProperty state;
	
	private UIManager() {
		this.state = new SimpleBooleanProperty(false);
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
}