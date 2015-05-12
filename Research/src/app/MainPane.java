package app;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public final class MainPane extends AnchorPane {

	public MainPane(MoveListener listener) {
		this.setPrefHeight(500);
		this.setPrefWidth(500);

		for (ObjectProperty<Point2D> point : listener.getPointList()) {
			Circle circle = new Circle(15, Color.RED);
			this.getChildren().add(circle);
			point.addListener((observable, oldValue, newValue) -> {
				Platform.runLater(() -> {
					circle.setLayoutX(newValue.getX());
					circle.setLayoutY(newValue.getY());
				});
			});
		}
	}
}