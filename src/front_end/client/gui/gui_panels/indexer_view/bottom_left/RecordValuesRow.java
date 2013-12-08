package front_end.client.gui.gui_panels.indexer_view.bottom_left;


import java.util.ArrayList;
import java.util.List;

public class RecordValuesRow {

	private List<String> recordsRow;

	public RecordValuesRow(int fieldCount) {
		recordsRow = new ArrayList<>();
		for (int i = 0; i < fieldCount; i++) {
			recordsRow.add("");
		}
	}

	public String getRecordValue(int column) {
		return recordsRow.get(column);
	}

	public void setRecordValue(String value, int column) {
		if (column != 0) {
			this.recordsRow.set(column - 1, value);
		}
	}
}
