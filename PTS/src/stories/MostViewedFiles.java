package stories;

import java.io.IOException;
import java.util.List;

import application.Information;
import ca.pfv.spmf.algorithms.frequentpatterns.aprioriTID.AlgoAprioriTID;
import ca.pfv.spmf.patterns.itemset_array_integers_with_tids.Itemset;
import ca.pfv.spmf.patterns.itemset_array_integers_with_tids.Itemsets;
import javafx.collections.ObservableList;

public class MostViewedFiles {
	private List<String> list;
	private ObservableList<Information> allInfo;
	private List<String> courseFile;
	private int size = 0;

	public MostViewedFiles(List<String> list, ObservableList<Information> allInfo, List<String> courseFile) {
		this.list = list;
		this.allInfo = allInfo;
		this.courseFile = courseFile;
	}

	protected List<Itemset> getAll() throws NumberFormatException, IOException {
		AlgoAprioriTID algo = new AlgoAprioriTID();

		Itemsets patterns = algo.runAlgorithm("Files-ID.txt", null, 0.01);

		size = patterns.getLevels().get(1).size();
		return patterns.getLevels().get(1);
	}

	public String getTopViewedFiles(String compare) throws NumberFormatException, IOException {
		String text = "";
		List<Itemset> items = getAll();
		int count = 0;
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < allInfo.size(); j++) {
				if (Integer.parseInt(list.get(j).split(",")[1]) == items.get(i).getItems()[0]
						&& courseFile.get(j).startsWith(compare) && count < 4) {
					text += (count + 1) + "." + allInfo.get(j).getCourse() + "\n";
					count++;
					break;
				}
			}
		}
		return text;
	}
}