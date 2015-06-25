package learn_pinch_3d;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;


public final class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private static final Node createSphere(Color color) {
		Sphere sphere = new Sphere();
		sphere.setMaterial(new PhongMaterial(color));
		sphere.setRadius(5);
		return sphere;
		//return new Circle(3, color);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new Pane();
		Node base = createSphere(Color.BLACK);
		Node index1 = createSphere(Color.RED);
		Node index2 = createSphere(Color.RED);
		Node index3 = createSphere(Color.RED);
		Node indexTick = createSphere(Color.RED);

		Node thumb1 = createSphere(Color.BLUE);
		Node thumb2 = createSphere(Color.GREEN);
		Node thumbTick = createSphere(Color.YELLOW);

		pane.getChildren().addAll(base, indexTick, index1, index2, index3, thumbTick, thumb1, thumb2);
		
		base.setLayoutX(250);
		base.setLayoutY(250);

		indexTick.setLayoutX(QLearning.INDEX_FINGER_BASE_POS.getX());
		indexTick.setLayoutY(QLearning.INDEX_FINGER_BASE_POS.getY());

		thumbTick.setLayoutX(QLearning.THUMB_FINGER_BASE_POS.getX());
		thumbTick.setLayoutY(QLearning.THUMB_FINGER_BASE_POS.getY());

		primaryStage.setWidth(500);
		primaryStage.setHeight(500);
		Scene scene = new Scene(pane);
		Camera camera = new PerspectiveCamera();
		camera.setTranslateX(30);
		camera.setRotationAxis(new Point3D(0, 1, 0));
		camera.setRotate(45);
		camera.setTranslateZ(100);
		scene.setCamera(camera);
		primaryStage.setScene(scene);
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

		model.thumbFinger.secondJointPos.addListener(
				(a, b, c) -> this.changePos(thumb1, c));
		model.thumbFinger.lastJointPos.addListener(
				(a, b, c) -> this.changePos(thumb2, c));
		model.thumbFinger.tipPos.addListener(
				(a, b, c) -> this.changePos(thumbTick, c));

		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				qLearn.start((long)20);
				Platform.runLater(() -> primaryStage.close());
				return null;
			}
		};
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(task);
	}
	
	private final void changePos(Node Sphere, Point3D pos) {
			Sphere.setLayoutX(pos.getX());
			Sphere.setLayoutY(pos.getY());
			Sphere.setTranslateZ(pos.getZ());
	}
}