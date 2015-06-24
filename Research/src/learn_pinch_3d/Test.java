package learn_pinch_3d;

import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
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
		
		Point3D basePos = new Point3D(200, 200, 0);
		Sphere base = new Sphere();
		base.setMaterial(new PhongMaterial(Color.RED));
		base.setRadius(5);
		base.setLayoutX(basePos.getX());
		base.setLayoutY(basePos.getY());
		base.setTranslateZ(basePos.getZ());
		pane.getChildren().add(base);
		
		Point3D nextPos = computePos(basePos, 50, new Point3D(45, 0, 45));
		Sphere next = new Sphere();
		next.setMaterial(new PhongMaterial(Color.RED));
		System.out.println(nextPos);
		next.setRadius(5);
		next.setLayoutX(nextPos.getX());
		next.setLayoutY(nextPos.getY());
		next.setTranslateZ(nextPos.getZ());
		pane.getChildren().add(next);
		
		Camera camera = new PerspectiveCamera();
		scene.setCamera(camera);
		
		primaryStage.setWidth(400);
		primaryStage.setHeight(400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private final Point3D computePos(Point3D basePos, double distance, Point3D angle) {
		double phi = angle.getX() / 180.0 * Math.PI;
		double theta = angle.getZ() / 180.0 * Math.PI;
		
		double nextY = distance * Math.cos(theta) * Math.cos(phi);
		double nextZ = distance * Math.cos(theta) * Math.sin(phi);
		double nextX = distance * Math.sin(theta);
		
		Point3D nextPos = new Point3D(basePos.getX() + nextX, basePos.getY() + nextY, basePos.getZ() + nextZ);
		return nextPos;
	}
}