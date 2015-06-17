package q_learning_hierarchical;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		QLearning qLearn = new QLearning();
		qLearn.start();
		primaryStage.setScene(new Scene(new Pane()));
		primaryStage.show();
	}
}