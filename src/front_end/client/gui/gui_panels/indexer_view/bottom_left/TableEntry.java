package front_end.client.gui.gui_panels.indexer_view.bottom_left;

import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BasePanel;
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
//	If no one is logged in, or no batch is currently being indexed by the logged in user, the Table Entry Tab should be empty.
//	When a batch is being indexed, the table should contain a “Record Number”column that displays record numbers and is
//	read-only. The table should also contain editable columns for all of the project fields in the proper order.
//	When the screen space allocated to the Table Entry Tab is too small to fully display the table, scroll bars should
//	be provided so the user can scroll the view.
//	The TAB key should move the field selection in a left-to-right, top-to-bottom order.
//	Unrecognized field values should be highlighted red.
//			Right-clicking on an unrecognized field value (i.e., one that is highlighted red) should bring up a context
//	menu containing a “See Suggestions”menu item. Selecting the “See Suggestions”menu item should display the Suggestions Dialog. If the user selects a suggested value and clicks the “Use Suggestion”button, the selected value should replace the unrecognized value in the table.


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
//			TODO: make it so that a scroll bar will appear when the area is too small
//			TODO: make it so that the image changes when you hit tab or enter
			tableContainer.createVerticalScrollBar();
			add(tableContainer, BorderLayout.WEST);
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
}
