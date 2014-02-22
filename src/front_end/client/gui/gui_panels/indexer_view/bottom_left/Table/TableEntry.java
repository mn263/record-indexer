package front_end.client.gui.gui_panels.indexer_view.bottom_left.Table;

import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BasePanel;
import front_end.client.gui.batch_state.BatchState;
import front_end.client.gui.gui_panels.indexer_view.bottom_left.RecordValuesRow;
import front_end.client.gui.gui_panels.indexer_view.bottom_left.SpellCorrector.SpellCorrectorMain;
import front_end.client.gui.gui_panels.indexer_view.bottom_left.quality_checker.SuggestionDialog;
import shared.communication.results.DownloadBatch_Result;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 8:35 PM
 */
public class TableEntry extends BasePanel {

	private TableEntryTable tableModel;
	private JTable table;
	private JPopupMenu popupMenu;

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

			// Create some menu items for the popup
			JMenuItem menuSuggestion = new JMenuItem("See Suggestions");
			// Create a popup menu
			popupMenu = new JPopupMenu( "Menu" );
			popupMenu.add(menuSuggestion);
			table.add(popupMenu);

			ActionListener suggestionListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					BatchState bs = getClientController().getBatchState();
					final int row = table.getSelectedRow();
					final int column = table.getSelectedColumn()-1;
					if (!(column < 0)) {
						bs.changeSelectedCell(row, column);
					}

					String[][] recordValues = bs.getRecordValues();
					HashSet<String> knownDataSet = bs.getKnownDataSetList().get(column);
					String recordValue = recordValues[column][row];

					ArrayList<String> similarWords = new ArrayList<>();
					try {
						similarWords = SpellCorrectorMain.suggestWord(knownDataSet, recordValue);
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					HashSet<String> suggestedSet = new HashSet<>();
					for (String word : similarWords) {
						suggestedSet.add(word);
					}
					similarWords.clear();
					for (String word : suggestedSet) {
						similarWords.add(word);
					}
					Collections.sort(similarWords);

					SuggestionDialog suggestionDialog = new SuggestionDialog(getClientController(), similarWords,
							column, row);
					suggestionDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					suggestionDialog.setVisible(true);
					suggestionDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				}
			};
			menuSuggestion.addActionListener(suggestionListener);


			MouseAdapter mouseAdapter = new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					BatchState bs = getClientController().getBatchState();

					final int row = table.rowAtPoint(e.getPoint());
					final int column = table.columnAtPoint(e.getPoint()) - 1;
					if (!(column < 0)) {
						bs.changeSelectedCell(row, column);
					}

					if (SwingUtilities.isRightMouseButton(e)) {
						String[][] recordValues = bs.getRecordValues();
						Set<String> knownDataSet = bs.getKnownDataSetList().get(column);
						String recordValue = recordValues[column][row];
						if (recordValue != null && !recordValue.isEmpty()) {
							if (!knownDataSet.isEmpty() && !knownDataSet.contains(recordValue.toUpperCase())) {
								table.setRowSelectionInterval(row, row);
								table.setColumnSelectionInterval(column+1, column+1);
								popupMenu.show(e.getComponent(), e.getX(), e.getY());
							}
						}
					}
				}
			};

			KeyAdapter keyAdapter = new KeyAdapter() {

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
				column.setCellRenderer(new ColorCellRenderer());
				column.setPreferredWidth(100);
			}

			JScrollPane tableContainer = new JScrollPane(table);
			add(tableContainer, BorderLayout.WEST);
		}
	}

	public void fillTableWithSavedRecordValues() {
		BatchState bs = getClientController().getBatchState();
		bs.getDownloadBatchResult().getFieldInformationList().get(1).getKnownDataPath();
		String[][] recordValues = bs.getRecordValues();
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
		BatchState bs = getClientController().getBatchState();
		String[][] recordValues = bs.getRecordValues();
		int column = bs.getSelectedColumn();
		int row = bs.getSelectedRow();
		tableModel.setValueAt(recordValues[column][row], row, column + 1);
	}


	@SuppressWarnings("serial")
	class ColorCellRenderer extends JLabel implements TableCellRenderer {

		private Border selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

		public ColorCellRenderer() {

			setOpaque(true);
			setFont(getFont().deriveFont(16.0f));
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
													   int row, int column) {

			Color c = Color.white;
			if (column == 0) {
				this.setText((String) value);
			} else {

				BatchState bs = getClientController().getBatchState();
				String[][] recordValues = bs.getRecordValues();
				Set<String> knownDataSet = bs.getKnownDataSetList().get(column-1);
				String cellValue = (String) value;
				String recordValue = recordValues[column-1][row];
				String upperValue = recordValue;
				if (recordValue != null && !recordValue.isEmpty()) {
					upperValue = recordValue.toUpperCase();
				}
				if (!cellValue.isEmpty() && !knownDataSet.isEmpty() && !knownDataSet.contains(upperValue)) {
					c = new Color(229, 88, 79);
				}
				this.setText(recordValues[column-1][row]);
			}

			if (isSelected) {
				this.setBorder(selectedBorder);
				c = new Color(62, 60, 186);
			} else {
				this.setBorder(null);
			}
			this.setBackground(c);
			return this;
		}

	}
}

