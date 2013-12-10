package front_end.client.gui.gui_panels.indexer_view.bottom_left;

import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BasePanel;
import shared.communication.results.DownloadBatch_Result;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 8:35 PM
 */
public class FormEntry extends BasePanel {

	//			Right-clicking on an unrecognized field value (that is highlighted red) should bring up a context menu containing a “See Suggestions”menu item. Selecting the “See Suggestions”menu item should display the Suggestions Dialog. If the user selects a suggested value and clicks the “Use Suggestion”button, the selected value should replace the unrecognized value in the form.
	private JTable rowTable;
	private ArrayList<JTextField> fieldAreaPanels;

	public FormEntry(ClientController clientController) {
		super(clientController);
		fieldAreaPanels = new ArrayList<>();
		setLayout(new FlowLayout());

		if (clientController.getBatchState().hasDownloadedBatch()) {
			String columnNames[] = {""};
			String[][] dataValues = new String[clientController.getBatchState().getDownloadBatchResult().getRecordCount()][1];
			for (int i = 0; i < clientController.getBatchState().getDownloadBatchResult().getRecordCount(); i++) {
				dataValues[i][0] = Integer.toString(i);
			}
			// Create a new table instance
			rowTable = new JTable(dataValues, columnNames);
			rowTable.setSize(10, 50);
			rowTable.setTableHeader(null);
			rowTable.setBorder(null);
			rowTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			rowTable.getColumnModel().getColumn(0).setPreferredWidth(45);
			rowTable.getColumnModel().getColumn(0).setWidth(45);
			rowTable.getColumnModel().getColumn(0).setMaxWidth(45);

			AbstractTableModel rowTableModel = new FormEntryTableModel(clientController);
			rowTable.setModel(rowTableModel);
			add(rowTable);

			createFieldArea();

			MouseAdapter mouseAdapter = new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					int row = rowTable.rowAtPoint(e.getPoint());
					int column = getClientController().getBatchState().getSelectedColumn();
					getClientController().getBatchState().changeSelectedCell(row, column);
				}
			};

			rowTable.addMouseListener(mouseAdapter);
		}
	}

	private void createFieldArea() {
		DownloadBatch_Result result = getClientController().getBatchState().getDownloadBatchResult();
		int fieldCount = result.getFieldCount();
		JPanel rowPanel = new JPanel();
		rowPanel.setLayout(new GridLayout(fieldCount, 1));
		for (int i = 0; i < fieldCount; i++) {
			String fieldName = result.getFieldInformationList().get(i).getFieldTitle();
			JLabel fieldLabel = new JLabel(fieldName);
			int row = rowTable.getSelectedRow();
			String recordValue = "";
			if (row != -1) {
				recordValue = getClientController().getBatchState().getRecordValue(row, i);
			}
			rowPanel.add(fieldLabel);
			JTextField fieldValue;
			if (recordValue.isEmpty()) {
				fieldValue = new JTextField(15);
			} else {
				fieldValue = new JTextField(recordValue, 15);
			}
			fieldValue.addFocusListener(fieldFocusAdapter);
			fieldValue.addKeyListener(fieldKeyAdapter);
			rowPanel.add(fieldValue);
			fieldAreaPanels.add(fieldValue);
		}
		add(rowPanel);
	}

	KeyAdapter fieldKeyAdapter = new KeyAdapter() {
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
			for (int i = 0; i < fieldAreaPanels.size(); i++) {
				if (fieldAreaPanels.get(i) == e.getComponent()) {
					String value = fieldAreaPanels.get(i).getText();
					int row = rowTable.getSelectedRow();
					if (row < 0) {
						row = 0;
					}
					getClientController().getBatchState().setRecordValue(value, i, row);
				}
			}
		}
	};

	FocusAdapter fieldFocusAdapter = new FocusAdapter() {
		@Override
		public void focusGained(FocusEvent e) {
			super.focusGained(e);
			for (int i = 0; i < fieldAreaPanels.size(); i++) {
				if (fieldAreaPanels.get(i) == e.getComponent()) {
					int row = rowTable.getSelectedRow();
					if (row < 0) {
						row = 0;
					}
					getClientController().getBatchState().changeSelectedCell(row, i);
				}
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			super.focusLost(e);
		}
	};


	public void setSelectedCell(int row) {
		rowTable.setRowSelectionInterval(row, row);
		String[][] recordValues = getClientController().getBatchState().getRecordValues();
		for (int i = 0; i < recordValues.length; i++) {
			fieldAreaPanels.get(i).setText(recordValues[i][row]);
		}
	}
}
