package front_end.client.gui.gui_panels.indexer_view.bottom_left.Form;

import front_end.client.gui.ClientController;
import front_end.client.gui.batch_state.BatchState;

import javax.swing.table.AbstractTableModel;

/**
 * User: matt
 * Date: 12/9/13
 * Time: 8:29 PM
 */
public class FormEntryTableModel extends AbstractTableModel {

	private BatchState batchState;


	public FormEntryTableModel(ClientController clientController) {
		this.batchState = clientController.getBatchState();
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
	public int getColumnCount() {
		return 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return Integer.toString(rowIndex);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
