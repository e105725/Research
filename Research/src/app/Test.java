package app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import app.task.NoteMoveTask;

public class Test extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane pane = new AnchorPane();

		primaryStage.setScene(new Scene(pane));
		primaryStage.show();

		List<Pane> list = new ArrayList<>();
		list.add(pane);
		NoteMoveTask task = new NoteMoveTask(list);
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(task);
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
	}
}