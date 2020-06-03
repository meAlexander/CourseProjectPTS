package stories;

import java.io.IOException;
import java.util.List;

import application.Information;
import application.MainController;
import application.messages.FXOptionPane;
import ca.pfv.spmf.algorithms.frequentpatterns.aprioriTID.AlgoAprioriTID;
import ca.pfv.spmf.patterns.itemset_array_integers_with_tids.Itemsets;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class SearchTopFilesByUserWithID {
	private ObservableList<Information> list;
	private List<String> ids;

	@FXML
	CheckBox userSearch;

	public SearchTopFilesByUserWithID(ObservableList<Information> list, List<String> ids, CheckBox userSearch) {
		this.list = list;
		this.ids = ids;
		this.userSearch = userSearch;
	}

	public void SearchTopFilesByUserWithIDButton() {
		userSearch.setOnAction(e -> {
			String text = FXOptionPane.showTextInput("Search By ID", "");
			if (text != null) {
				StringBuilder top5Files = new StringBuilder();

				for (int i = 0; i < list.size(); i++) {

					int a = Integer.parseInt(ids.get(i).split(",")[0]);
					int b = Integer.parseInt(text);

					if (a == b && Integer.parseInt(ids.get(i).split(",")[1]) != 49) {
						top5Files.append(ids.get(i).split(",")[1]);
						top5Files.append("\n");
					}
				}

				MainController.printToFIle(top5Files.toString());
				top5Files = new StringBuilder();

				try {
					int size = 5;
					AlgoAprioriTID algo = new AlgoAprioriTID();
					Itemsets patterns = algo.runAlgorithm("User-MostViewedFiles-ID.txt", null, 0.01);

					if (size > patterns.getLevels().get(1).size())
						size = patterns.getLevels().get(1).size();

					for (int j = 0; j < size; j++) {

						for (int i = 0; i < list.size(); i++) {
							int a = Integer.parseInt(ids.get(i).split(",")[1]);
							int b = patterns.getLevels().get(1).get(j).getItems()[0];

							if ((list.get(i).getIp().equals(text) || a == b)) {
								top5Files.append(list.get(i).getCourse());
								top5Files.append("\n");
								break;
							}
						}
					}
					FXOptionPane.showInformation("Information", top5Files.toString());
				} catch (NumberFormatException | IOException e1) {
					e1.printStackTrace();
				} finally {
					userSearch.setSelected(false);
				}
			}
			userSearch.setSelected(false);
		});
	}
}