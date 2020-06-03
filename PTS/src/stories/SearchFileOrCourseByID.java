package stories;

import application.Information;
import application.MainController;
import application.messages.FXOptionPane;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class SearchFileOrCourseByID {
	private FilteredList<Information> filter;

	@FXML
	CheckBox searchID;

	public SearchFileOrCourseByID(CheckBox searchID, FilteredList<Information> filter) {
		this.searchID = searchID;
		this.filter = filter;
	}

	public void searchFileOrCourseByIdButton() {
		searchID.selectedProperty().addListener(i -> {
			if (searchID.isSelected()) {
				String id = FXOptionPane.showTextInput("Enter File/Course ID", "");
				if (id != null) {
					filter.setPredicate(e -> {
						if (id.isEmpty())
							return true;

						String input = id.toLowerCase();

						String userID = MainController.regex(e.getId(), "'([\\d]{1,5})'[.]", 1);
						if (userID.equals(input.toLowerCase()))
							return true;
						else
							return false;
					});
				}
				searchID.setSelected(false);
			}
		});
	}
}