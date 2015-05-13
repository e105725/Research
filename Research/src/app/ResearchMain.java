package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class ResearchMain extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private final MoveListener listener;
	
	public ResearchMain() {
		this.listener = new MoveListener(DataManager.frameProperty());
		DataManager.getController().addListener(this.listener);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		MainPane mainPane = new MainPane();
		primaryStage.setScene(new Scene(mainPane));
		primaryStage.setWidth(1000);
		primaryStage.setHeight(800);
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		DataManager.getController().removeListener(this.listener);
	}
}