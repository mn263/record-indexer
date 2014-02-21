package front_end.client.gui.gui_panels.indexer_view.bottom_left.Form;

import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BasePanel;
import front_end.client.gui.batch_state.BatchState;
import front_end.client.gui.gui_panels.indexer_view.bottom_left.SpellCorrector.SpellCorrectorMain;
import front_end.client.gui.gui_panels.indexer_view.bottom_left.SuggListModel;
import front_end.client.gui.gui_panels.indexer_view.bottom_left.SuggestionDialog;
import shared.communication.results.DownloadBatch_Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 8:35 PM
 */
public class FormEntry extends BasePanel {

	//			Right-clicking on an unrecognized field value (that is highlighted red) should bring up a context menu containing a “See Suggestions”menu item. Selecting the “See Suggestions”menu item should display the Suggestions Dialog. If the user selects a suggested value and clicks the “Use Suggestion”button, the selected value should replace the unrecognized value in the form.
	private JList<String> rowTable;
	private ArrayList<JTextField> fieldAreaPanels;
	private JPopupMenu popupMenu;



	public FormEntry(ClientController clientController) {
		super(clientController);
		fieldAreaPanels = new ArrayList<>();
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(500, 10));


		if (clientController.getBatchState().hasDownloadedBatch()) {
			SuggListModel rowListModel = new SuggListModel();
			for (int i = 0; i < clientController.getBatchState().getDownloadBatchResult().getRecordCount(); i++) {
				rowListModel.add(Integer.toString(i));
			}
			// Create a new table instance
			rowTable = new JList<>(rowListModel);
			JScrollPane rowTableScrollPane = new JScrollPane(rowTable);
			rowTableScrollPane.setPreferredSize(new Dimension(100, 170));
			add(rowTableScrollPane, BorderLayout.WEST);
			createFieldArea();

			ActionListener suggestionListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					BatchState bs = getClientController().getBatchState();
					int row = rowTable.getSelectedIndex();
					int column = bs.getSelectedColumn();

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
						System.out.println(word);
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

			MouseAdapter mouseAdapter = new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					int row = rowTable.getSelectedIndex();
					int column = getClientController().getBatchState().getSelectedColumn();
					getClientController().getBatchState().changeSelectedCell(row, column);
				}
			};

			rowTable.addMouseListener(mouseAdapter);
			// Create some menu items for the popup
			JMenuItem menuSuggestion = new JMenuItem("See Suggestions");
			// Create a popup menu
			popupMenu = new JPopupMenu( "Menu" );
			popupMenu.add(menuSuggestion);
			popupMenu.setVisible(false);
			add(popupMenu, BorderLayout.SOUTH);
			menuSuggestion.addActionListener(suggestionListener);
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
			int row = rowTable.getSelectedIndex();
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
			fieldValue.addMouseListener(jTextFieldAdapter);
			rowPanel.add(fieldValue);
			fieldAreaPanels.add(fieldValue);
		}
		JScrollPane rowScrollPane = new JScrollPane(rowPanel);
		add(rowScrollPane, BorderLayout.EAST);
//		add(rowPanel);
	}

	MouseAdapter jTextFieldAdapter = new MouseAdapter() {
		@Override
		public void mouseReleased(MouseEvent e) {
			for (int i = 0; i < fieldAreaPanels.size(); i++) {
				if (e.getComponent() == fieldAreaPanels.get(i)) {
					BatchState bs = getClientController().getBatchState();
					bs.changeSelectedCell(bs.getSelectedRow(), i);
				}
			}
			if (SwingUtilities.isRightMouseButton(e)) {
				if (e.getComponent().getBackground() != Color.white) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
	};

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
					int row = rowTable.getSelectedIndex();
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
					int row = rowTable.getSelectedIndex();
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
		if (getClientController().getBatchState().hasDownloadedBatch()) {

			int column = getClientController().getBatchState().getSelectedColumn();
			fieldAreaPanels.get(column).requestFocus();

			rowTable.setSelectedIndex(row);
			String[][] recordValues = getClientController().getBatchState().getRecordValues();
			for (int i = 0; i < recordValues.length; i++) {
				String value = recordValues[i][row];
				fieldAreaPanels.get(i).setText(value);
				Set<String> knownDataSet = getClientController().getBatchState().getKnownDataSetList().get(i);
				if (value != null && !value.isEmpty()) {
					String upperValue = value.toUpperCase();
					if (!knownDataSet.isEmpty() && !knownDataSet.contains(upperValue)) {
						fieldAreaPanels.get(i).setBackground(new Color(229, 88, 79));
					} else {
						fieldAreaPanels.get(i).setBackground(Color.white);
					}
				} else {
					fieldAreaPanels.get(i).setBackground(Color.white);
				}
			}
		}
	}
}
