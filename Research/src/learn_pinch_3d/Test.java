package learn_pinch_3d;

import javafx.application.Application;
import javafx.geometry.Point2D;
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
		base.setMaterial(new PhongMaterial(Color.BLUE));
		base.setRadius(5);
		base.setLayoutX(basePos.getX());
		base.setLayoutY(basePos.getY());
		base.setTranslateZ(basePos.getZ());
		pane.getChildren().add(base);
		
		Point3D nextPos = computeNextPosition(50, new Point3D(0, 0, 0), new Point2D(0,0));
		System.out.println(nextPos);
		
//		Point3D rotateX = this.rotateX(new Point3D(0, 50, 0), 45);
//		Point3D rotateZ = this.rotateZ(rotateX, 45);
		//Point3D nextPos = this.rotateY(rotateZ, 45);
		Point3D nexthoge = new Point3D(basePos.getX() + nextPos.getX(), basePos.getY() + nextPos.getY(), basePos.getZ() + nextPos.getZ());
		Sphere next = new Sphere();
		next.setMaterial(new PhongMaterial(Color.RED));
		System.out.println(nexthoge);
		next.setRadius(5);
		next.setLayoutX(nexthoge.getX());
		next.setLayoutY(nexthoge.getY());
		next.setTranslateZ(nexthoge.getZ());
		pane.getChildren().add(next);
		pane.setRotate(180);
		Camera camera = new PerspectiveCamera();
		scene.setCamera(camera);
		
		primaryStage.setWidth(400);
		primaryStage.setHeight(400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private final Point3D computeNextPosition(double r, Point3D baseAngle, Point2D angle) {
		double phi = baseAngle.getX() / 180.0 * Math.PI;
		double theta = baseAngle.getZ() / 180.0 * Math.PI;
		double nextY = r * Math.cos(theta) * Math.cos(phi);
		double nextZ = r * Math.cos(theta) * Math.sin(phi);
		double nextX = r * Math.sin(theta);
		
		Point3D tempPos = new Point3D(nextX, nextY, nextZ);
		Point3D rotateX = this.rotateX(tempPos, angle.getX());
		Point3D rotateZ = this.rotateZ(rotateX, angle.getY());
		return rotateZ;
	}
	
	private final Point3D rotateX(Point3D point, double angle) {
		double theta = angle / 180.0 * Math.PI;
		double x = point.getX();
		double y = point.getY() * Math.cos(theta) + point.getZ() * -Math.sin(theta);
		double z = point.getY() * Math.sin(theta) + point.getZ() * Math.cos(theta);
		return new Point3D(x, y, z);
	}
	
	private final Point3D rotateY(Point3D point, double angle) {
		double theta = angle / 180.0 * Math.PI;
		double x = point.getX() * Math.cos(theta) + point.getZ() * Math.sin(theta);
		double y = point.getY();
		double z = point.getX() * -Math.sin(theta) + point.getZ() * Math.cos(theta);
		return new Point3D(x, y, z);
	}
	
	private final Point3D rotateZ(Point3D point, double angle) {
		double theta = angle / 180.0 * Math.PI;
		double x = point.getX() * Math.cos(theta) - point.getY() * Math.sin(theta);
		double y = point.getX() * Math.sin(theta) + point.getY() * Math.cos(theta);
		double z = point.getZ();
		return new Point3D(x, y, z);
	}	
}	