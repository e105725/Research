package sample.leap;

import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Screen;
import com.leapmotion.leap.Vector;

public final class LeapJavaFX extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	private final SimpleLeapListener listener;
	private final Controller leapController;

	public LeapJavaFX() {
		this.listener = new SimpleLeapListener();
		this.leapController = new Controller();
		this.leapController.addListener(this.listener);
	}

	@Override
	public void start(Stage primaryStage) {
		this.leapController.addListener(this.listener);

		AnchorPane pane = new AnchorPane();
		IntStream.range(0, 5).forEach(
				index -> pane.getChildren().add(new Circle(10, Color.RED)));

		Scene scene = new Scene(pane, 1020, 800);
		primaryStage.setScene(scene);
		primaryStage.show();

		this.listener.frameProperty().addListener((observable, oldFrame, newFrame) -> {
			if (newFrame.hands().isEmpty()) {
				return;
			}

			Screen screen = this.leapController.locatedScreens().get(0);
			if (screen == null || !screen.isValid()) {
				return;
			}

			Hand hand = newFrame.hands().get(0);
			if(!hand.isValid()){
				return;
			}
			
			for (int index = 0; index < newFrame.fingers().count(); index++) {
				Finger finger = newFrame.fingers().get(index);
				if (!finger.isValid()) {
					continue;
				}

				if (!finger.isValid()) {
					continue;
				}
				Node node = pane.getChildren().get(index);
				Vector intersect = screen.intersect(finger.tipPosition(), finger.direction(), true);
				double screenXPos = screen.widthPixels() * Math.min(1, Math.max(0, intersect.getX()));
				double screenYPos = screen.heightPixels() * Math.min(1, Math.max(0,(1 - intersect.getY())));

				double sceneXPos = screenXPos - scene.getX() - scene.getWindow().getX();
				double sceneYPos = screenYPos - scene.getY() - scene.getWindow().getY();
				Point2D panePos = pane.sceneToLocal(sceneXPos, sceneYPos);
				Platform.runLater(() -> {
					node.setLayoutX(panePos.getX());
					node.setLayoutY(panePos.getY());
				});
				System.out.println("");
				System.out.println("x = " +  panePos.getX());
				System.out.println("y = " +  panePos.getY());
			}
		});
	}

	@Override
	public void stop(){
		this.leapController.removeListener(this.listener);
	}
}