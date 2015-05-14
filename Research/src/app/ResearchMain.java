package app;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import app.task.NoteMoveTask;
import app.ui.MainPane;

public final class ResearchMain extends Application {
	private static final double WIDTH = 800;
	private static final double HEIGHT = 640;

	public static void main(String[] args) {
		launch(args);
	}

	private final MoveListener listener;
	private final NoteMoveTask noteMoveTask;

	public ResearchMain() {
		this.listener = new MoveListener(DataManager.frameProperty());
		DataManager.getController().addListener(this.listener);
		this.noteMoveTask = new NoteMoveTask(new ArrayList<>());

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		MainPane mainPane = new MainPane();
		primaryStage.setScene(new Scene(mainPane));
		primaryStage.setWidth(WIDTH);
		primaryStage.setHeight(HEIGHT);
		primaryStage.show();

		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(this.noteMoveTask);
	}

	@Override
	public void stop() throws Exception {
		DataManager.getController().removeListener(this.listener);
		this.noteMoveTask.cancel();
	}
}