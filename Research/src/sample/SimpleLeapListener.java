package sample;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;

final class SimpleLeapListener extends Listener {

	private final ObjectProperty<Frame> frame;
	
	SimpleLeapListener() {
		this.frame = new SimpleObjectProperty<>();
	}

	@Override
	public void onFrame(Controller controller) {
		Frame frame = controller.frame();
		if (frame.isValid()) {
			this.frame.set(frame);
		}
	}
	
	final ObjectProperty<Frame> frameProperty() {
		return this.frame;
	}
}