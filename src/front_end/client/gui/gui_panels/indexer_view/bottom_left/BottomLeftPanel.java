package front_end.client.gui.gui_panels.indexer_view.bottom_left;

import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BasePanel;

import javax.swing.*;
import java.awt.*;

/**
 * User: matt
 * Date: 11/22/13
 * Time: 7:53 PM
 */
public class BottomLeftPanel extends BasePanel {

	private TableEntry tableEntry;
	private FormEntry formEntry;
	private JTabbedPane tabbedPane;

	public BottomLeftPanel(ClientController clientController) {
		super(clientController);
		setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		tableEntry = new TableEntry(clientController);
		formEntry = new FormEntry(clientController);
		JScrollPane scrollTableEntry = new JScrollPane(tableEntry);
		JScrollPane scrollFormEntry = new JScrollPane(formEntry);
		tabbedPane.addTab("Table Entry", scrollTableEntry);
		scrollFormEntry.setAlignmentX(LEFT_ALIGNMENT);
		tabbedPane.addTab("Form Entry", scrollFormEntry);
		add(tabbedPane, BorderLayout.CENTER);
	}

	public void reloadTabs() {
		this.tableEntry = new TableEntry(getClientController());
		this.formEntry = new FormEntry(getClientController());
		tabbedPane.removeTabAt(0);
		tabbedPane.removeTabAt(0);
		JScrollPane scrollTableEntry = new JScrollPane(tableEntry);
		JScrollPane scrollFormEntry = new JScrollPane(formEntry);
		tabbedPane.addTab("Table Entry", scrollTableEntry);
		scrollFormEntry.setAlignmentX(LEFT_ALIGNMENT);
		tabbedPane.addTab("Form Entry", scrollFormEntry);
	}

	public void updateTabs(int row, int column) {
		tableEntry.highlightCell(row, column + 1);
		formEntry.setSelectedCell(row);
	}

	public void updateRecordValues() {
		tableEntry.updateCurrentRecord();
//		tableEntry.fillTableWithSavedRecordValues();

	}
}
