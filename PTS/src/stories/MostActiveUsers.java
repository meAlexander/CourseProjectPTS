package stories;

import java.io.IOException;
import java.util.List;

import application.Information;
import ca.pfv.spmf.algorithms.frequentpatterns.aprioriTID.AlgoAprioriTID;
import ca.pfv.spmf.patterns.itemset_array_integers_with_tids.Itemset;
import ca.pfv.spmf.patterns.itemset_array_integers_with_tids.Itemsets;
import javafx.collections.ObservableList;

public class MostActiveUsers {
	private ObservableList<Information> list;
	private List<String> ids;
	int size;

	public MostActiveUsers(ObservableList<Information> list, List<String> ids) {
		this.list = list;
		this.ids = ids;
	}

	protected List<Itemset> getAll() throws NumberFormatException, IOException {
		AlgoAprioriTID algo = new AlgoAprioriTID();

		Itemsets patterns = algo.runAlgorithm("User-File-ID.txt", null, 0.01);

		size = patterns.getLevels().get(1).size();
		return patterns.getLevels().get(1);
	}

	public String getIpByUserId() throws NumberFormatException, IOException {
		String text = "";
		List<Itemset> items = getAll();
		int count = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < list.size(); j++) {
				if (Integer.parseInt(ids.get(j).split(",")[0]) == items.get(i).getItems()[0] && count < 5) {
					text += (count + 1) + ".User ID=" + items.get(i).getItems()[0] + " IP:" + list.get(j).getIp()
							+ "\n";
					count++;
					break;
				}
			}
		}
		return text;
	}
}