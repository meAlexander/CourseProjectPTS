package application;

import insidefx.undecorator.UndecoratorScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stories.BarCodeController;

public class CommunicationController extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BarCodeController barCode = new BarCodeController();

			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("MainProgram.fxml"));
			UndecoratorScene scene = new UndecoratorScene(primaryStage, root);

			scene.getUndecorator().setButtonVisibility(true, true, true);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(barCode.getBarCodeScene(primaryStage, scene));

			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setMinWidth(993);
			primaryStage.setMinHeight(470);

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}