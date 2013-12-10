package front_end.client.gui.gui_panels.indexer_view.bottom_left;

import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BasePanel;
import front_end.client.gui.batch_state.BatchState;
import shared.communication.results.DownloadBatch_Result;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 8:35 PM
 */
public class TableEntry extends BasePanel {

	private TableEntryTable tableModel;
	private JTable table;

	public TableEntry(ClientController clientController) throws HeadlessException {
		super(clientController);
		setLayout(new BorderLayout());

		if (clientController.getBatchState().hasDownloadedBatch()) {
			ArrayList<RecordValuesRow> recordValues = generateRecordValues();
			tableModel = new TableEntryTable(getClientController(), recordValues);
			table = new JTable(tableModel);
			table.setRowHeight(15);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setCellSelectionEnabled(true);
			table.getTableHeader().setReorderingAllowed(false);
			fillTableWithSavedRecordValues();
			MouseAdapter mouseAdapter = new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					final int row = table.rowAtPoint(e.getPoint());
					final int column = table.columnAtPoint(e.getPoint()) - 1;
					if (!(column < 0)) {
						getClientController().getBatchState().changeSelectedCell(row, column);
					}
				}
			};

			KeyAdapter keyAdapter = new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					super.keyTyped(e);

				}

				@Override
				public void keyPressed(KeyEvent e) {
					super.keyPressed(e);
//					int selectedColumn = table.getSelectedColumn();
//					int selectedRow = table.getSelectedRow();
//					String value = (String) tableModel.getValueAt(selectedRow, selectedColumn);
//					System.out.println("keyPressed -- setRecordValue");
//					getClientController().getBatchState().setRecordValue(value, selectedColumn-1, selectedRow);
				}

				@Override
				public void keyReleased(KeyEvent e) {
					super.keyReleased(e);
					int ck = e.getKeyCode();
					if (ck == 9 || ck == 10 || ck == 37 || ck == 38 || ck == 39 || ck == 40 || ck == 98 || ck == 100
							|| ck == 102 || ck == 104) {
						int selectedColumn = table.getSelectedColumn();
						int selectedRow = table.getSelectedRow();
						if (selectedColumn < 1) {
							return;
						}
						getClientController().getBatchState().changeSelectedCell(selectedRow, selectedColumn - 1);
					}
				}
			};
			table.addMouseListener(mouseAdapter);
			table.addKeyListener(keyAdapter);

			TableColumnModel columnModel = table.getColumnModel();
			for (int i = 0; i < tableModel.getColumnCount(); ++i) {
				TableColumn column = columnModel.getColumn(i);
				column.setPreferredWidth(100);
			}

			JScrollPane tableContainer = new JScrollPane(table);
			add(tableContainer, BorderLayout.WEST);
		}
	}

	public void fillTableWithSavedRecordValues() {
		System.out.println("fillTableWithSavedRecordValues");
		String[][] recordValues = getClientController().getBatchState().getRecordValues();
		for (int column = 0; column < recordValues.length; column++) {
			for (int row = 0; row < recordValues[column].length; row++) {
				tableModel.setValueAt(recordValues[column][row], row, column + 1);
			}
		}
	}

	private ArrayList<RecordValuesRow> generateRecordValues() {
		DownloadBatch_Result downloadBatchResult = getClientController().getBatchState().getDownloadBatchResult();
		int numOfRecords = downloadBatchResult.getRecordCount();
		int numOfFields = downloadBatchResult.getFieldCount();
		ArrayList<RecordValuesRow> result = new ArrayList<>();
		for (int i = 0; i < numOfRecords; i++) {
			result.add(new RecordValuesRow(numOfFields));
		}
		return result;
	}

	public void highlightCell(int row, int column) {
		table.setColumnSelectionInterval(column, column);
		table.setRowSelectionInterval(row, row);
	}

	public void updateCurrentRecord() {
		System.out.println("single record");
		BatchState bs = getClientController().getBatchState();
		String[][] recordValues = bs.getRecordValues();
		int column = bs.getSelectedColumn();
		int row = bs.getSelectedRow();
		tableModel.setValueAt(recordValues[column][row], row, column + 1);
	}
}
