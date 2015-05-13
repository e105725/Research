package app;

import javafx.beans.property.ObjectProperty;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;

public final class MoveListener extends Listener {
	private final ObjectProperty<Frame> frame;
	
	public MoveListener(ObjectProperty<Frame> _frame) {
		this.frame = _frame;
	}

	@Override
	public void onFrame(Controller controller) {
		Frame newFrame = controller.frame();
		if (newFrame.isValid()) {
			this.frame.set(newFrame);
			return;
		}
	}
}