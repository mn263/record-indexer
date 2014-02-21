package front_end.client.gui.gui_panels.indexer_view.bottom_left.quality_checker;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: matt
 * Date: 12/10/13
 * Time: 3:22 PM
 */
public class SuggListModel extends AbstractListModel<String> {


	private List<String> values;

	public SuggListModel() {
		values = new ArrayList<>();
	}

	@Override
	public String getElementAt(int index) {
		return values.get(index);
	}

	@Override
	public int getSize() {
		return values.size();
	}

	public void add(String value) {
		values.add(value);
		this.fireContentsChanged(this, values.size() - 1, values.size() - 1);
	}

	public void remove(String value) {

		int index = values.indexOf(value);
		if (index >= 0) {
			values.remove(index);
			this.fireContentsChanged(this, index, index);
		}
	}

}
