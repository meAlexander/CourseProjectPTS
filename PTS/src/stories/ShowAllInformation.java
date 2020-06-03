package stories;

import application.Information;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class ShowAllInformation {
	@FXML
	Button showAllInformation;
	
	@FXML
	TableView<Information> view;
	
	public ShowAllInformation(Button showAllInformation, TableView<Information> view) {
		this.showAllInformation = showAllInformation;
		this.view = view;
	}
	
	public void showAllInformationButton() {
		showAllInformation.setOnAction(e -> {
			if (!view.isVisible()) {
				view.setVisible(true);
				view.refresh();
			}
		});
	}
}