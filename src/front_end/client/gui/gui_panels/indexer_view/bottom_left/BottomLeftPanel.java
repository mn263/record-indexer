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
		tabbedPane.addTab("Table Entry", tableEntry);
		tabbedPane.addTab("Form Entry", formEntry);
		add(tabbedPane, BorderLayout.CENTER);
	}

	public void reloadTabs() {
		this.tableEntry = new TableEntry(getClientController());
		this.formEntry = new FormEntry(getClientController());
		tabbedPane.removeTabAt(0);
		tabbedPane.removeTabAt(0);
		tabbedPane.addTab("Table Entry", tableEntry);
		tabbedPane.addTab("Form Entry", formEntry);
	}

	public void updateTabs(int row, int column) {
		tableEntry.highlightCell(row, column + 1);
//		TODO: update formEntry
	}
}
