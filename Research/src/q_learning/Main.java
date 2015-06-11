package q_learning;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class Main extends Application {

	public static void main(String[] args) {
		QLeaning qLearn = new QLeaning();
		qLearn.start();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(new Pane()));
		primaryStage.show();
	}
}