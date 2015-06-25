package learn_pinch_3d;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class Test extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new Pane();
		Scene scene = new Scene(pane);
		PerspectiveCamera camera = new PerspectiveCamera();

		Sphere sphere = new Sphere(5, 100);
		sphere.setMaterial(new PhongMaterial(Color.RED));
		sphere.setLayoutX(150);
		sphere.setLayoutY(150);

		Point3D basePosition = new Point3D(0, 0, 0);
		Point2D baseAngle = new Point2D(0, 0);
		double r = 100;

		Point3D newPosition = this.computePosition(basePosition, baseAngle, new Point2D(0, 0), r);
		System.out.println(newPosition);
		
		//		ContextMenu contextMenu = new ContextMenu();
		//		pane.setOnContextMenuRequested(event -> contextMenu.show(primaryStage));
		//		MenuItem rotateXItem = new MenuItem("rotateX");
		//		rotateXItem.setOnAction(event -> hoge.rotate);

		pane.getChildren().add(sphere);

		scene.setCamera(camera);
		primaryStage.setScene(scene);

		primaryStage.setWidth(300);
		primaryStage.setHeight(300);
		primaryStage.show();
	}

	private final Point3D computePosition(Point3D basePosition, Point2D baseAngle, Point2D angle, double r) {
		double actualXAngle = Math.PI * (angle.getX() + baseAngle.getX()) / 180.0;
		double actualYAngle = Math.PI * (angle.getY() + baseAngle.getY()) / 180.0;
		double newXPosition = basePosition.getX() + (r * Math.sin(actualYAngle) * Math.cos(actualXAngle));
		double newYPosition = basePosition.getY() + (r * Math.sin(actualYAngle) * Math.sin(actualXAngle));
		double newZPosition = basePosition.getZ() + (r * Math.cos(actualYAngle));

		return new Point3D(newXPosition, newYPosition, newZPosition);
	}
}