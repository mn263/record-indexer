package front_end.client.gui.gui_panels.indexer_view.bottom_left;

import front_end.client.gui.ClientController;
import front_end.client.gui.batch_state.BatchState;
import shared.communication.results.DownloadBatch_Result;

import javax.swing.table.AbstractTableModel;
import java.util.List;


public class TableEntryTable extends AbstractTableModel {

	private BatchState batchState;
	private List<RecordValuesRow> recordValuesRows;

	public TableEntryTable(ClientController clientController, List<RecordValuesRow> recordValuesRows) {
		this.recordValuesRows = recordValuesRows;
		this.batchState = clientController.getBatchState();
	}

	@Override
	public int getColumnCount() {
		int columnCount = 0;
		if (batchState.hasDownloadedBatch()) {
			columnCount = batchState.getDownloadBatchResult().getFieldCount();
			columnCount++;
		}
		return columnCount;
	}

	@Override
	public String getColumnName(int column) {
		if (batchState.hasDownloadedBatch()) {
			if (column == 0) {
				return "Record Number";
			}
			column--;
			List<DownloadBatch_Result.FieldInformation> fieldInfoList = batchState.getDownloadBatchResult().getFieldInformationList();
			return fieldInfoList.get(column).getFieldTitle();
		} else {
			return null;
		}
	}

	@Override
	public int getRowCount() {
		int rowCount = 0;
		if (batchState.hasDownloadedBatch()) {
			rowCount = batchState.getDownloadBatchResult().getRecordCount();
		}
		return rowCount;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return column != 0;
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (column == 0) {
			return Integer.toString(row);
		} else {
			column--;
			RecordValuesRow recordValuesRow = recordValuesRows.get(row);
			return recordValuesRow.getRecordValue(column);
		}
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		if (value == null) {
			return;
		}
		if (row >= 0 && row < getRowCount() && column > 0 && column < getColumnCount()) {
			RecordValuesRow recordValuesRow = recordValuesRows.get(row);
			String cellVal = (String) value;
			recordValuesRow.setRecordValue(cellVal, column);
			batchState.setRecordValueFromTableEntryTable((String) value, column - 1, row);
		}
	}
}
