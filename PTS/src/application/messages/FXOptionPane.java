package application.messages;

import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXOptionPane {
	public static void showInformation(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		// alert.initStyle(StageStyle.UTILITY);
		alert.initStyle(StageStyle.UNDECORATED);
		alert.setGraphic(null);
		alert.setTitle("Information");
		alert.setHeaderText(title);
		alert.setContentText(message);

		((Scene) alert.getDialogPane().getScene()).getStylesheets()
				.add(new FXOptionPane().getClass().getResource("option.css").toExternalForm());
		((Scene) alert.getDialogPane().getScene()).setFill(javafx.scene.paint.Color.BLACK);
		moovableStage(((Scene) alert.getDialogPane().getScene()));

		DialogPane pane = alert.getDialogPane();
		((GridPane) pane.getChildren().get(0)).setStyle("-fx-background-color:black");

		alert.showAndWait();
	}

	static double positionX;
	static double positionY;

	public static void moovableStage(Scene scene) {
		scene.setOnMousePressed(e -> {
			// get mouse position x and y
			positionX = e.getSceneX();
			positionY = e.getSceneY();
		});
		scene.setOnMouseDragged(e -> {
			Stage primaryStage = (Stage) scene.getWindow();
			primaryStage.setX(e.getScreenX() - positionX);
			primaryStage.setY(e.getScreenY() - positionY);
		});
	}

	public static String showTextInput(String title, String defaultValue) {
		TextInputDialog dialog = new TextInputDialog(defaultValue);
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.setGraphic(null);
		dialog.setHeaderText(title);

		((Scene) dialog.getDialogPane().getScene()).getStylesheets()
				.add(new FXOptionPane().getClass().getResource("option.css").toExternalForm());
		((Scene) dialog.getDialogPane().getScene()).setFill(javafx.scene.paint.Color.BLACK);
		moovableStage(((Scene) dialog.getDialogPane().getScene()));

		DialogPane pane = dialog.getDialogPane();
		((GridPane) pane.getChildren().get(0)).setStyle("-fx-background-color:black");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}
}