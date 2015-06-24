package learn_pinch_2d;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public final class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new Pane();
		Circle base = new Circle(3, Color.BLACK);
		Circle index1 = new Circle(3, Color.BLUE);
		Circle index2 = new Circle(3, Color.YELLOW);
		Circle index3 = new Circle(3, Color.GREEN);
		Circle indexTick = new Circle(3, Color.BLACK);

		Circle thumb1 = new Circle(3, Color.RED);
		Circle thumb2 = new Circle(3, Color.RED);
		Circle thumbTick = new Circle(3, Color.RED);

		pane.getChildren().addAll(base, indexTick, index1, index2, index3, thumbTick, thumb1, thumb2);
		
		base.setLayoutX(250);
		base.setLayoutY(250);				

		indexTick.setLayoutX(QLearning.INDEX_FINGER_BASE_POS.getX());
		indexTick.setLayoutY(QLearning.INDEX_FINGER_BASE_POS.getY());

		thumbTick.setLayoutX(QLearning.THUMB_FINGER_BASE_POS.getX());
		thumbTick.setLayoutY(QLearning.THUMB_FINGER_BASE_POS.getY());

		primaryStage.setWidth(500);
		primaryStage.setHeight(500);
		primaryStage.setScene(new Scene(pane));
		primaryStage.show();

		QLearning qLearn = new QLearning();
		Hand model = qLearn.getModel();
		model.indexFinger.firstJointPos.addListener(
				(a, b, c) -> this.changePos(index1, c));
		model.indexFinger.secondJointPos.addListener(
				(a, b, c) -> this.changePos(index2, c));
		model.indexFinger.lastJointPos.addListener(
				(a, b, c) -> this.changePos(index3, c));
		model.indexFinger.tipPos.addListener(
				(a, b, c) -> this.changePos(indexTick, c));

		model.thumbFinger.firstJointPos.addListener(
				(a, b, c) -> this.changePos(thumb1, c));
		model.thumbFinger.lastJointPos.addListener(
				(a, b, c) -> this.changePos(thumb2, c));
		model.thumbFinger.tipPos.addListener(
				(a, b, c) -> this.changePos(thumbTick, c));

		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				qLearn.start(20);
				Platform.runLater(() -> primaryStage.close());
				return null;
			}
		};
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(task);
	}
	
	private final void changePos(Circle circle, Point2D pos) {
			circle.setLayoutX(pos.getX());
			circle.setLayoutY(pos.getY());
	}

	private final void changePosition(Circle circle, double angle) {		
		double length = 20;

		double lengthX = Math.cos(Math.PI * angle / 180.0) * length;
		double lengthY = Math.sin(Math.PI * angle / 180.0) * length;
		Platform.runLater(() -> {
			circle.setLayoutX(100 + lengthX);
			circle.setLayoutY(100 - lengthY);
		});
	}
}