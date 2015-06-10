package app;

import java.util.Random;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;

public final class DataManager {
	private static final DataManager self = new DataManager();
	private static final Random RANDOM = new Random();
	
	private final Controller controller;
	private final ObjectProperty<Frame> frame;
	private final IntegerProperty score;
	private final IntegerProperty noteCount;
	
	private DataManager() {
		this.controller = new Controller();
		this.frame = new SimpleObjectProperty<>();
		this.score = new SimpleIntegerProperty();
		this.noteCount = new SimpleIntegerProperty();
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
	
	public static final Random random() {
		return RANDOM;
	}
	
	public static final int getScore() {
		return self.score.get();
	}
	
	public static final IntegerProperty scoreProperty() {
		return self.score;
	}
	
	public static final void setScore(int score) {
		self.score.set(score);
	}
	
	public static final int getNoteCount() {
		return self.noteCount.get();
	}
	
	public static final IntegerProperty noteCountProperty() {
		return self.noteCount;
	}
	
	public static final void setNoteCount(int noteCount) {
		self.noteCount.set(noteCount);
	}
}