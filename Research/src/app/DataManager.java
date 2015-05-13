package app;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;

public final class DataManager {
	private static final DataManager self = new DataManager();
	
	private final Controller controller;
	private final ObjectProperty<Frame> frame;
	
	private DataManager() {
		this.controller = new Controller();
		this.frame = new SimpleObjectProperty<>();
	}
	
	public static final ObjectProperty<Frame> frameProperty() {
		return self.frame;
	}
	
	public static final Frame getFrame() {
		return self.frame.get();
	}
	
	public static final Controller getController() {
		return self.controller;
	}
}