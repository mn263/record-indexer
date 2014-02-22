package front_end.client.gui.gui_panels.indexer_view.bottom_left;

import front_end.client.gui.ClientController;
import front_end.client.gui.gui_panels.indexer_view.bottom_left.Form.FormEntry;
import front_end.client.gui.gui_panels.indexer_view.bottom_left.Table.TableEntry;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * User: matt
 * Date: 11/22/13
 * Time: 7:53 PM
 */
public class BottomLeftPanel extends JTabbedPane {

	private TableEntry tableEntry;
	private FormEntry formEntry;
	private ClientController clientController;

	public BottomLeftPanel(final ClientController clientController) {
		this.clientController = clientController;

		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int row = clientController.getBatchState().getSelectedRow();
				formEntry.setSelectedCell(row);
			}
		});

		tableEntry = new TableEntry(clientController);
		formEntry = new FormEntry(clientController);
		addTab("Table Entry", tableEntry);
		addTab("Form Entry", formEntry);
	}

	public void reloadTabs() {
		this.tableEntry = new TableEntry(clientController);
		this.formEntry = new FormEntry(clientController);
		removeTabAt(0);
		removeTabAt(0);
		JScrollPane scrollTableEntry = new JScrollPane(tableEntry);
		JScrollPane scrollFormEntry = new JScrollPane(formEntry);
		addTab("Table Entry", scrollTableEntry);
		scrollFormEntry.setAlignmentX(LEFT_ALIGNMENT);
		addTab("Form Entry", scrollFormEntry);
	}


	public void updateTabs(int row, int column) {
		tableEntry.highlightCell(row, column + 1);
		formEntry.setSelectedCell(row);
	}

	public void updateRecordValues() {
		tableEntry.updateCurrentRecord();
		formEntry.setSelectedCell(clientController.getBatchState().getSelectedRow());
	}
}
