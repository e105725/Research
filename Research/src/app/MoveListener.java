package app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Screen;
import com.leapmotion.leap.Vector;

final class MoveListener extends Listener {
	private final List<ObjectProperty<Point2D>> pointList;
	
	public MoveListener() {
		this.pointList = new ArrayList<>();
		IntStream.range(0, 5).forEach(index -> this.pointList.add(new SimpleObjectProperty<>()));
	}
	
	public final List<ObjectProperty<Point2D>> getPointList() {
		return this.pointList;
	}

	@Override
	public void onFrame(Controller controller) {
		Frame frame = controller.frame();
		if (!frame.isValid()) {
			return;
		}
		if (frame.hands().isEmpty()) {
			return;
		}
		Screen screen = controller.locatedScreens().get(0);
		if (screen == null || !screen.isValid()) {
			return;
		}
		int index = 0;
		
		for (Finger finger : frame.fingers()) {
			if (5 < index) {
				return;
			}
			ObjectProperty<Point2D> point = this.pointList.get(index);
			Vector intersect = screen.intersect(finger.stabilizedTipPosition(), finger.direction(), true);
			double x = screen.widthPixels()*Math.min(1d,Math.max(0d,intersect.getX()));
			double y = screen.heightPixels()*Math.min(1d,Math.max(0d,(1d-intersect.getY())));
			point.set(new Point2D(x, y));
			index++;
		}
	}
}