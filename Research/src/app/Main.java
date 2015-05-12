package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.leapmotion.leap.Controller;

public final class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	private final Controller controller = new Controller();
	private final MoveListener listener = new MoveListener();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.controller.addListener(this.listener);
		primaryStage.setScene(new Scene(new MainPane(this.listener)));
		primaryStage.show();
	}
	
	@Override
	public void stop() throws Exception {
		this.controller.removeListener(this.listener);
	}
}