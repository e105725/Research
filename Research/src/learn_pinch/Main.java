package learn_pinch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
		QLearning qLearn = new QLearning();
		
		Pane pane = new Pane();
		Circle joint = new Circle(3, Color.BLACK);
		Circle index = new Circle(3, Color.BLACK);
		Circle thumb = new Circle(3, Color.BLACK);
		pane.getChildren().addAll(joint, index, thumb);
		
		joint.setLayoutX(100);
		joint.setLayoutY(100);				
		primaryStage.setWidth(200);
		primaryStage.setHeight(200);
		primaryStage.setScene(new Scene(pane));
		primaryStage.show();
		
		Model model = qLearn.getModel();
		model.indexFingerAngleProperty().addListener(
				(a, b, c) -> this.changePosition(index, model.getIndexFingerAngle()));
		model.thumbFingerAngleProperty().addListener(
				(a, b, c) -> this.changePosition(thumb, model.getThumbFingerAngle()));
		
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				qLearn.start();
				return null;
			}
		};
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.submit(task);
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