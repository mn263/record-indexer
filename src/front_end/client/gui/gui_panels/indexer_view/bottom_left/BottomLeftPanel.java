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

	public BottomLeftPanel(ClientController clientController) {
		super(clientController);

		setLayout(new BorderLayout());
//		setPreferredSize(new Dimension(400, 200));

		JTabbedPane tabbedPane = new JTabbedPane();
		TableEntry tableEntry = new TableEntry(clientController);
		FormEntry formEntry = new FormEntry(clientController);
		tabbedPane.addTab("Table Entry", tableEntry);
		tabbedPane.addTab("Form Entry", formEntry);
		add(tabbedPane, BorderLayout.CENTER);
	}
}
